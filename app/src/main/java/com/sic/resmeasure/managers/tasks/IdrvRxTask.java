/*
 * Copyright (c) 2016 Silicon Craft Technology Co.,Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sic.resmeasure.managers.tasks;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Xml;

import com.bumptech.glide.load.Encoder;
import com.google.common.io.BaseEncoding;
import com.sic.module.nfc.cmds.Eeprom;
import com.sic.module.nfc.sic4340.Adc;
import com.sic.module.nfc.sic4340.Idrv;
import com.sic.module.nfc.sic4340.Register;
import com.sic.module.nfc.sic4340.Sic4340;
import com.sic.resmeasure.daos.RegisterItemDao;
import com.sic.resmeasure.managers.IdrvManager;
import com.sic.resmeasure.utils.ResultRx;
import com.sic.resmeasure.utils.RxUtils;
import com.sic.resmeasure.utils.Toaster;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 06/Oct/2016
 */
public class IdrvRxTask {

    private static IdrvRxTask instance;
    private final Sic4340 mSic4340;

    public static IdrvRxTask getInstance() {
        if (instance == null) {
            instance = new IdrvRxTask();
        }
        return instance;
    }

    private IdrvRxTask() {
        mSic4340 = Sic4340.getInstance();
    }

    public Observable<ResultRx> initialTask(Context context) {
        return RxUtils.getInstance().applySchedulers(
                Observable.concat(
                        getPreExecuteObservable(context),
                        getInitialTaskObservable(context)));
    }

    public Observable<ResultRx> getAdcTask(Context context, RegisterItemDao dao) {
        return RxUtils.getInstance().applySchedulers(
                Observable.concat(
                        getPreExecuteObservable(context),
                        getAdcTaskObservable(context, false, dao),
                        getAdcTaskObservable(context, true, dao)));
    }

    @NonNull
    private ObservableOnSubscribe<Integer> preExecute() {
        return subscriber -> {
//            Timber.d("preExecute CALL");
            if (!mSic4340.checkedUid()) {
                subscriber.onNext(-1);
                return;
            }

            byte sample1 = mSic4340.getUid()[2];
            byte sample2 = mSic4340.getUid()[3];
            byte rev = mSic4340.getUid()[4];
            if (sample1 != (byte) 0xFF || sample2 != (byte) 0xFF) {
                rev = sample1;
            }

            if (rev != 0x00 && rev != 0x01 && rev != 0x03) { //TODO
                subscriber.onNext(-1);
                return;
            }

            mSic4340.getRegister().clearFlags();
            if (!mSic4340.isTagExists()) {
                subscriber.onNext(-2);
                return;
            }
            subscriber.onNext(0);
            subscriber.onComplete();
        };
    }

    @NonNull
    private ObservableOnSubscribe<Integer> initial() {
        return subscriber -> {
//            Timber.d("initial CALL");
            if (IdrvManager.getInstance().isSameTagId(mSic4340.getUid())) {
                subscriber.onComplete();
            }
            if (!mSic4340.isSendCompleted()) {
                subscriber.onNext(-3);
                return;
            }
            IdrvManager.getInstance().setUid(mSic4340.getUid());
            byte[] recv = mSic4340.autoTransceive(Eeprom.getPackage_Read(0x2C));
            if (recv == null || recv.length < 16) {
                subscriber.onNext(-4);
                return;
            }
            IdrvManager.getInstance().setChipInfoFromPage2C(recv);
            recv = mSic4340.autoTransceive(Eeprom.getPackage_Read(0x30));
            if (recv == null || recv.length < 16) {
                subscriber.onNext(-4);
                return;
            }
            IdrvManager.getInstance().setChipInfoFromPage30(recv);
            subscriber.onNext(0);
            subscriber.onComplete();
        };
    }

    @NonNull
    private ObservableOnSubscribe<ResultRx> adc(final boolean switchBias, final RegisterItemDao dao) {
        return subscriber -> {
//            Timber.d("adc CALL");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mSic4340.setTimeout(8000);
            } else {
                mSic4340.setTimeout(4000);
            }
            boolean success = configRegisters(switchBias, dao);
            if (!success) {
                subscriber.onNext(ResultRx.newInstance(-6));
                return;
            }
//            reportRegisterData();
            byte[] clear = mSic4340.autoTransceive(new byte[]{(byte) 0xB4, (byte) 0xFF});
            Log.wtf("B4FF ", BaseEncoding.base16().encode(clear));
            byte[] recv = mSic4340.autoTransceive(Adc.getPackage_ConvAdc());
            if (recv != null && mSic4340.isSendCompleted() && recv.length > 2) {
                Log.wtf("recv ----- ", BaseEncoding.base16().encode(recv));
                if (mSic4340.isVddHError() || mSic4340.isVddLError()) {
                    subscriber.onNext(ResultRx.newInstance(-7));
                    return;
                }
                int conc = 0;
                int result = ((recv[1] << 8) | (recv[2] & 0xFF)) >> 2;
                float vout = IdrvManager.getInstance().getAdcResult(result);
//                Timber.wtf("vout ----  %f", vout);
                float ppm;

                if (switchBias) {
                    //conc = (vout > dao.getLimitVolt23()) ? 1 : 2;
                    conc = 1;
                } else if (vout < dao.getLimitVolt12() && dao.isBiasSwitch()) {
                    subscriber.onNext(ResultRx.newInstance(-5));
                    subscriber.onComplete();
                    return;
                }

                float regA, regB;
                switch (conc) {
                    case 0:
                        regA = dao.getRegressionParamA1();
                        regB = dao.getRegressionParamB1();
                        ppm = IdrvManager.getInstance().getPpmRegressionEquationA(vout, regA, regB);
                        break;

                    case 1:
                        regA = dao.getRegressionParamA2();
                        regB = dao.getRegressionParamB2();
                        ppm = IdrvManager.getInstance().getPpmRegressionEquationB(vout, regA, regB);
                        break;

                    case 2:
                    default:
                        regA = dao.getRegressionParamA3();
                        regB = dao.getRegressionParamB3();
                        subscriber.onNext(ResultRx.newInstance(-6));
                        return;
                }

                subscriber.onNext(ResultRx.newInstance(conc + 1).setAdcResult(result));
            } else {
                subscriber.onNext(ResultRx.newInstance(-2));
            }
        };
    }

    private Observable<ResultRx> getPreExecuteObservable(Context context) {
        return RxUtils.getInstance().applySchedulers(Observable.create(preExecute())
                .share().publish(observable -> {
                    RxUtils.getInstance().applySchedulers(observable.filter(err -> err < 0))
                            .subscribe(errorStateSubscribe(context));
                    return observable.map(ResultRx::newInstance);
                }));
    }

    private Observable<ResultRx> getInitialTaskObservable(Context context) {
        return RxUtils.getInstance().applySchedulers(Observable.create(initial())
                .share().publish(observable -> {
                    RxUtils.getInstance().applySchedulers(observable.filter(err -> err < 0))
                            .subscribe(errorStateSubscribe(context));
                    return observable.map(ResultRx::newInstance);
                }));
    }

    private Observable<ResultRx> getAdcTaskObservable(Context context, boolean switchBias, RegisterItemDao dao) {
        return RxUtils.getInstance().applySchedulers(Observable.create(adc(switchBias, dao))
                .share().publish(observable -> {
                    RxUtils.getInstance().applySchedulers(observable.map(ResultRx::getState)
                            .filter(err -> err < 0))
                            .subscribe(errorStateSubscribe(context));
                    return observable;
                }));
    }

    @NonNull
    private Consumer<Integer> errorStateSubscribe(Context context) {
        return err -> {
//            Timber.d("errorStateSubscribe publish %d", err);
            switch (err) {
                case -1:
                    break;
                case -2:
                    Toaster.showToast(context, "Tag was lost");
                    break;
                case -3:
                    Toaster.showToast(context, "Transmission Failed");
                    break;
                case -4:
                    Toaster.showToast(context, "Read EEPROM Failed");
                    break;
                case -5:
                    Toaster.showToast(context, "Switch to Bias2");
                    break;
                case -6:
                    Toaster.showToast(context, "Configuration Failed");
                    break;
                case -7:
                    Toaster.showToast(context, "Power is not enough");
                    break;
            }
        };
    }

    private void reportRegisterData() {
        Byte adcDivider = mSic4340.getRegister().read(Register.ADC_DIVIDER);
        Byte adcPrescaler = mSic4340.getRegister().read(Register.ADC_PRESCALER);
        Byte adcSampDly = mSic4340.getRegister().read(Register.ADC_SAMP_DLY);
        Byte adcNWait = mSic4340.getRegister().read(Register.ADC_NWAIT);
        Byte adcNbit = mSic4340.getRegister().read(Register.ADC_NBIT);
        Byte adcBufCFG = mSic4340.getRegister().read(Register.ADC_BUF_CFG);
        Byte adcIdrvCFG = mSic4340.getRegister().read(Register.IDRV_CFG);
        Byte adcIdrvValue = mSic4340.getRegister().read(Register.IDRV_VALUE);
        Byte adcDac1CValue = mSic4340.getRegister().read(Register.DAC1_VALUE);
        Byte adcChCFG = mSic4340.getRegister().read(Register.ADC_CH_CFG);
        Byte adcConvCfg = mSic4340.getRegister().read(Register.ADC_CONV_CFG);
        if (adcDivider == null || adcPrescaler == null || adcSampDly == null ||
                adcNWait == null || adcNbit == null || adcBufCFG == null ||
                adcIdrvCFG == null || adcIdrvValue == null || adcDac1CValue == null ||
                adcChCFG == null) {
            return;
        }

        Timber.wtf("adcDivider %02X", adcDivider);
        Timber.wtf("adcPrescaler %02X", adcPrescaler);
        Timber.wtf("adcSampDly %02X", adcSampDly);
        Timber.wtf("adcNWait %02X", adcNWait);
        Timber.wtf("adcNbit %02X", adcNbit);
        Timber.wtf("adcBufCFG %02X", adcBufCFG);
        Timber.wtf("adcIdrvCFG %02X", adcIdrvCFG);
        Timber.wtf("adcIdrvValue %02X", adcIdrvValue);
        Timber.wtf("adcDac1CValue %02X", adcDac1CValue);
        Timber.wtf("adcDac1CValue %02X", adcDac1CValue);
        Timber.wtf("adcChCFG %02X", adcChCFG);
        Timber.wtf("adcConvCfg %02X", adcConvCfg);
    }

    private boolean configRegisters(boolean switchBias, RegisterItemDao dao) {
        mSic4340.getRegister().writeParams(Register.ADC_DIVIDER, Adc.ADC_DIVIDER, 0xD3);
        mSic4340.getRegister().writeParams(Register.ADC_PRESCALER, Adc.ADC_PRESCALER, 0x00);
        mSic4340.getRegister().writeParams(Register.ADC_SAMP_DLY, Adc.ADC_SAMP_DELAY_TIME, 0xA2);
        mSic4340.getRegister().writeParams(Register.ADC_NWAIT, Adc.NWAIT_MUL | Adc.NWAIT_PRESCALER, 0x80);
        mSic4340.getRegister().writeParams(Register.ADC_NBIT, Adc.ADC_AVG | Adc.ADC_SIGNED | Adc.ADC_OSR, 0x0D); // Adc Signed bit OSR 1024
        mSic4340.getRegister().writeParams(Register.ADC_BUF_CFG, Adc.ADC_BUFP_EN | Adc.ADC_LPF, 0x11);
        mSic4340.getRegister().writeParams(Register.ADC_CH_CFG, Adc.ADC_CHN_GND | Adc.ADC_CHP, (0x01 << 4) | (byte)(dao.getChannel() + 12));
        mSic4340.getRegister().writeParams(Register.IDRV_CFG, Idrv.IDRV_CH | Idrv.IDRV_DC | Idrv.IDRV_VLM_EN | Idrv.IDRV_RNG, (dao.getChannel() << 4) | 0x04 | dao.getIdrvRange1());
        mSic4340.getRegister().writeParams(Register.IDRV_VALUE, Idrv.IDRV_VALUE, dao.getIdrvValue1());

//        reportRegisterData();
        return mSic4340.isSendCompleted();
    }
}
