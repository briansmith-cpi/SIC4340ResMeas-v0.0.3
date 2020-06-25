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

import com.sic.module.nfc.global.IdrvProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Idrv extends IdrvProvider {

    private static final String TAG = Idrv.class.getSimpleName();

    // register address 0x0C
    public static final int IDRV_CH = 0x30;
    public static final int Channel_0 = 0x00;
    public static final int Channel_1 = 0x10;
    public static final int Channel_2 = 0x20;
    public static final int Channel_3 = 0x30;

    public static final int IDRV_DC = 0x04;
    public static final int DrivingWaveform_Pulse = 0x00;
    public static final int DrivingWaveform_Dc = 0x04;

    public static final int IDRV_VLM_EN = 0x02;
    public static final int Vlm_Disable = 0x00;
    public static final int Vlm_Enable = 0x02;

    public static final int IDRV_RNG = 0x01;
    public static final int Rng_1uA = 0x00;
    public static final int Rng_8uA = 0x01;

    // register address 0x0D
    public static int IDRV_VALUE = 0x3F;

    private static Idrv instance;

    private Idrv(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Idrv getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Idrv(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
        ADDRESS_IDRV_CONFIG = Register.IDRV_CFG;
        ADDRESS_IDRV_VALUE = Register.IDRV_VALUE;
    }

    @Override
    protected void setDefaultBit() {

    }

    /**
     * Gets the current output channel
     *
     * @return output channel
     */
    public int getOutputChannel() {
        return (byte) (mRegister.readBuffer(ADDRESS_IDRV_CONFIG) & IDRV_CH);
    }

    /**
     * Sets the current output channel
     *
     * @param channel: Specifies the port bit to be set.
     *                 This parameter can be one of the following values:
     *                 {@link #Channel_0}: Channel 0
     *                 {@link #Channel_1}: Channel 1
     *                 {@link #Channel_2}: Channel 2
     *                 {@link #Channel_3}: Channel 3
     */
    public void setOutputChannel(int channel) {
        mRegister.writeParams(ADDRESS_IDRV_CONFIG, IDRV_CH, channel);
    }

    /**
     * Gets the current driving wave form
     *
     * @return driving wave form
     */
    public int getDrivingWaveform() {
        return (byte) (mRegister.readBuffer(ADDRESS_IDRV_CONFIG) & IDRV_DC);
    }

    /**
     * Sets the current driving wave form
     *
     * @param driver: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #DrivingWaveform_Pulse}: Pulse driver
     *                {@link #DrivingWaveform_Dc}: DC driver
     */
    public void setDrivingWaveform(int driver) {
        mRegister.writeParams(ADDRESS_IDRV_CONFIG, IDRV_DC, driver);
    }

    /**
     * Checks the voltage limiter for current output to prevent sensor breakdown
     *
     * @return false: disable, output voltage can increase upto VDD
     * true: enable, output voltage is limited to VDAC1 voltage
     */
    public boolean isVoltageLimiterEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_IDRV_CONFIG) & IDRV_VLM_EN) == Vlm_Enable;
    }

    /**
     * Sets the voltage limiter for current output to prevent sensor breakdown
     *
     * @param enabled: This parameter contains the voltage limiter
     *                 This parameter can be one of the following values:
     *                 false: Disable, output voltage can increase upto VDD
     *                 true: Enable, output voltage is limited to VDAC1 voltage
     */
    public void setVoltageLimiterEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_IDRV_CONFIG, IDRV_VLM_EN, Vlm_Enable);
        } else {
            mRegister.writeParams(ADDRESS_IDRV_CONFIG, IDRV_VLM_EN, Vlm_Disable);
        }
    }

    /**
     * Gets the current output resolution per step
     *
     * @return range output
     */
    public int getRangeOutput() {
        return (byte) (mRegister.readBuffer(ADDRESS_IDRV_CONFIG) & IDRV_RNG);
    }

    /**
     * Sets the current output resolution per step
     *
     * @param range: Specifies the port bit to be set.
     *               This parameter can be one of the following values:
     *               {@link #Rng_1uA}: 1 uA per step
     *               {@link #Rng_8uA}: 8 uA per step
     */
    public void setRangeOutput(int range) {
        mRegister.writeParams(ADDRESS_IDRV_CONFIG, IDRV_RNG, range);
    }

    /**
     * Gets the current output value (in multiple of current step controlled by IDRV_RNG)
     *
     * @return raw output value
     */
    public int getValue() {
        return (byte) (mRegister.readBuffer(ADDRESS_IDRV_CONFIG) & IDRV_VALUE);
    }

    /**
     * Sets the current output value (in multiple of current step controlled by IDRV_RNG)
     *
     * @param value: raw output value
     */
    public void setValue(int value) {
        mRegister.writeParams(ADDRESS_IDRV_VALUE, IDRV_VALUE, value);
    }
}
