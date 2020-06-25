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

import com.sic.module.nfc.global.RegisterProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Register extends RegisterProvider {

    public static final int REGISTER_PAGE = 0x1B;

    public static final byte STATUS = 0x00;
    public static final byte ADC_RESULT_0 = 0x01;
    public static final byte ADC_RESULT_1 = 0x02;
    public static final byte ADC_RESULT_2 = 0x03;

    public static final byte ADC_DIVIDER = 0x04;
    public static final byte ADC_PRESCALER = 0x05;
    public static final byte ADC_SAMP_DLY = 0x06;
    public static final byte ADC_NWAIT = 0x07;

    public static final byte ADC_NBIT = 0x08;
    public static final byte ADC_CONV_CFG = 0x09;
    public static final byte ADC_BUF_CFG = 0x0A;
    public static final byte ADC_CH_CFG = 0x0B;

    public static final byte IDRV_CFG = 0x0C;
    public static final byte IDRV_VALUE = 0x0D;
    public static final byte DAC1_VALUE = 0x0E;
    public static final byte DAC2_VALUE = 0x0F;

    public static final byte DAC_CFG = 0x10;
    public static final byte CHEM_CFG = 0x11;

    public static final byte VDD_CFG = 0x13;

    public static final byte TEMPSEN_CFG = 0x14;

    public static final byte GPIO_MODE = 0x16;
    public static final byte GPIO_INOUT = 0x17;

    public static final byte SENSOR_CONFIG = 0x18;
    public static final byte GAP_WID_CFG = 0x19;

    public static final byte TEST = 0x1B;

    private static final String TAG = Register.class.getSimpleName();
    private static Register instance;

    protected Register(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Register getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Register(sic4340);
        return instance;
    }

    public static String getName(int address) {
        switch (address) {
            case STATUS:
                return "Status";

            case ADC_RESULT_2:
                return "Adc Result 2[23:16]";

            case ADC_RESULT_1:
                return "Adc Result 1[15:8]";

            case ADC_RESULT_0:
                return "Adc Result 0[7:0]";

            case ADC_DIVIDER:
                return "Adc Divider(M)";

            case ADC_PRESCALER:
                return "Adc Prescaler(N)";

            case ADC_SAMP_DLY:
                return "Adc Sampling Delay Time";

            case ADC_NWAIT:
                return "Adc Nwait Time";

            case ADC_NBIT:
                return "Adc NBit";

            case ADC_CONV_CFG:
                return "Adc Convert Configuration";

            case ADC_BUF_CFG:
                return "Adc Buffer Configuration";

            case ADC_CH_CFG:
                return "Adc Channel Configuration";

            case IDRV_CFG:
                return "Idrv Configuration";

            case IDRV_VALUE:
                return "Idrv Value";

            case DAC1_VALUE:
                return "Dac Value 1[7:0]";

            case DAC2_VALUE:
                return "Dac Value 2[7:0]";

            case DAC_CFG:
                return "Dac Configuration";

            case CHEM_CFG:
                return "Chemical Configuration";

            case VDD_CFG:
                return "Voltage Configuration";

            case TEMPSEN_CFG:
                return "Temperature Sensor Configuration";

            case GPIO_MODE:
                return "Gpio Mode";

            case GPIO_INOUT:
                return "Gpio Input/Output";

            case SENSOR_CONFIG:
                return "Sensor Configuration";

            case GAP_WID_CFG:
                return "Gap Width Configuration";

            case TEST:
                return "Test";
            default:
                return "RFU";
        }
    }

    public static RegisterType getRegisterType(int address) {
        switch (address) {
            case STATUS:
            case ADC_RESULT_0:
            case ADC_RESULT_1:
            case ADC_RESULT_2:
                return RegisterType.READ_ONLY_STATUS;

            case ADC_DIVIDER:
            case ADC_PRESCALER:
            case ADC_SAMP_DLY:
            case ADC_NWAIT:
            case ADC_NBIT:
            case ADC_CONV_CFG:
            case ADC_BUF_CFG:
            case ADC_CH_CFG:
            case IDRV_CFG:
            case IDRV_VALUE:
            case DAC1_VALUE:
            case DAC2_VALUE:
            case DAC_CFG:
            case CHEM_CFG:
            case VDD_CFG:
            case TEMPSEN_CFG:
            case GPIO_MODE:
            case GPIO_INOUT:
            case SENSOR_CONFIG:
            case GAP_WID_CFG:
            case TEST:
                return RegisterType.READ_WRITE_CONFIG;

            default:
                return RegisterType.RFU;
        }
    }

    @Override
    public int getRegisterPage() {
        return REGISTER_PAGE;
    }
}
