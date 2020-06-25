/*
 * Copyright (c) 2014 Silicon Craft Technology Co.,Ltd.
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

package com.sic.module.nfc.sic4340;

import com.sic.module.nfc.SicChip;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.1
 * @since 11/16/2014
 */
public class Sic4340 extends SicChip {

    private static final String TAG = Sic4340.class.getName();
    private static final byte SIC4340_UID_1 = (byte) 0x48; // 0x3948 for LG

    private static Sic4340 instance;

    private Adc mAdc;
    private Config mConfig;
    private Dac mDac;
    private Gpio mGpio;
    private Idrv mIdrv;
    private Status mStatus;

    @Override
    protected void setPeripheral() {
        mRegister = Register.getInstance(this);
        mAdc = Adc.getInstance(this);
        mConfig = Config.getInstance(this);
        mDac = Dac.getInstance(this);
        mGpio = Gpio.getInstance(this);
        mIdrv = Idrv.getInstance(this);
        mStatus = Status.getInstance(this);
    }

    private Sic4340() {
        super();
    }

    public static Sic4340 getInstance() {
        if (instance == null) {
            instance = new Sic4340();
        }
        return instance;

    }

    @Override
    public boolean checkedUid() {
        return !(mNfcA == null || mNfcA.getTag() == null || mNfcA.getTag().getId() == null) &&
                mNfcA.getTag().getId().length == 7 &&
                mNfcA.getTag().getId()[1] == SIC4340_UID_1 &&
                super.checkedUid();
    }

    public Register register() {
        return (Register) mRegister;
    }

    public Adc adc() {
        return mAdc;
    }

    public Config config() {
        return mConfig;
    }

    public Dac dac() {
        return mDac;
    }

    public Gpio gpio() {
        return mGpio;
    }

    public Idrv idrv() {
        return mIdrv;
    }

    public Status status() {
        return mStatus;
    }


    /**
     * Checks the latest response flag for VDD_RDY_L.
     *
     * @return true, if VDD L Ready.
     */
    public boolean isVddLError() {
        byte flag = (Flag.B_NAK | Flag.B_NAK_VDD_RDY_L);
        return (response & flag) == flag;
    }

    /**
     * Checks the latest response flag for VDD_RDY_H.
     *
     * @return true, if VDD H Ready.
     */
    public boolean isVddHError() {
        byte flag = (Flag.B_NAK | Flag.B_NAK_VDD_RDY_H);
        return (response & flag) == flag;
    }

}
