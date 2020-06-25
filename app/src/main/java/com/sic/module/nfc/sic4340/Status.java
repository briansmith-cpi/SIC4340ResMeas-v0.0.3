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

import com.sic.module.nfc.global.StatusProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Status extends StatusProvider {

    public static final byte ADC_TCK_FLG = BIT3;
    public static final byte ADC_CONT_FLG = BIT2;
    public static final byte VDD_EDY_H = BIT1;
    public static final byte VDD_EDY_L = BIT0;
    public static final byte ADDRESS_STATUS = 0x00;
    private static final String TAG = Status.class.getSimpleName();

    private static Status instance;

    private Status(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Status getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Status(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
    }

    @Override
    protected void setDefaultBit() {

    }

    /**
     * Gets the status on register.
     *
     * @return byte status data value.
     */
    public Byte getStatus() {
        return getStatus(ADDRESS_STATUS);
    }

    /**
     * Bit indicates that Adc is operated in Tick Mode
     *
     * @return true, if this Adc is operated in Tick Mode
     */
    public boolean isAdcTickMode() {
        Byte flag = getStatus();
        return flag != null && ((flag & ADC_TCK_FLG) == ADC_TCK_FLG);
    }

    /**
     * Bit indicates that Adc is operated in Continuous Mode
     *
     * @return true, if this Adc is operated in Continuous Mode
     */
    public boolean isAdcContinuousMode() {
        Byte flag = getStatus();
        return flag != null && ((flag & ADC_CONT_FLG) == ADC_CONT_FLG);
    }

    /**
     * Bit indicates that VDD output is more than 1.65 V
     *
     * @return true, if VDD output is more than 1.65 V
     */
    public boolean isVddHReady() {
        Byte flag = getStatus();
        return flag != null && ((flag & VDD_EDY_H) == VDD_EDY_H);
    }

    /**
     * Bit indicates that VDD output is more than 1.2 V / 1.45 V ( configure in EEPROM )
     *
     * @return true, if VDD output is more than 1.2 V / 1.45 V
     */
    public boolean isVddLReady() {
        Byte flag = getStatus();
        return flag != null && ((flag & VDD_EDY_L) == VDD_EDY_L);
    }
}
