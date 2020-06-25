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

import com.sic.module.nfc.global.DacProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Dac extends DacProvider {

    private static final String TAG = Dac.class.getSimpleName();

    public static final int DAC1_VALUE = 0xFF;
    public static final int DAC2_VALUE = 0xFF;
    public static final int DAC2_BUF_EN = 0x80;
    public static final int Dac2Buf_Disable = 0x00;
    public static final int Dac2Buf_Enable = 0x80;
    public static final int DAC1_BUF_EN = 0x40;
    public static final int Dac1Buf_Disable = 0x00;
    public static final int Dac1Buf_Enable = 0x40;
    public static final int RE_VG_CH = 0x30;
    public static final int ReVg_Ch0 = 0x00;
    public static final int ReVg_Ch1 = 0x10;
    public static final int ReVg_Ch2 = 0x20;
    public static final int ReVg_Ch3 = 0x30;
    public static final int DAC2_WE_VD_CH = 0x0C;
    public static final int Dac2WeVd_Ch0 = 0x00;
    public static final int Dac2WeVd_Ch1 = 0x04;
    public static final int Dac2WeVd_Ch2 = 0x08;
    public static final int Dac2WeVd_Ch3 = 0x0C;
    public static final int DAC1_CE_CH = 0x03;
    public static final int Dac1Ce_Ch0 = 0x00;
    public static final int Dac1Ce_Ch1 = 0x01;
    public static final int Dac1Ce_Ch2 = 0x02;
    public static final int Dac1Ce_Ch3 = 0x03;

    private static Dac instance;

    private Dac(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Dac getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Dac(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
        ADDRESS_DAC_CONFIG = Register.DAC_CFG;
        ADDRESS_DAC1_VALUE = Register.DAC1_VALUE;
        ADDRESS_DAC2_VALUE = Register.DAC_CFG;
    }

    @Override
    protected void setDefaultBit() {

    }

    /**
     * Gets the output voltage of DAC_1
     *
     * @return the output voltage of DAC_1
     */
    public int getDac1Output() {
        return mRegister.readBuffer(ADDRESS_DAC1_VALUE) * 5;
    }

    /**
     * Sets the output voltage of DAC_1
     *
     * @param mV:  Specifies the output voltage of DAC_1 to be set the DAC1_Value register. (mV)
     */
    public void setDac1Output(int mV) {
        mRegister.write(ADDRESS_DAC1_VALUE, mV / 5);
    }

    /**
     * Gets the output voltage of DAC_2
     *
     * @return the output voltage of DAC_2
     */
    public int getDac2Output() {
        return mRegister.readBuffer(ADDRESS_DAC2_VALUE) * 5;
    }

    /**
     * Sets the output voltage of DAC_2
     *
     * @param mV:  Specifies the output voltage of DAC_2 to be set the DAC2_Value register. (mV)
     */
    public void setDac2Output(int mV) {
        mRegister.write(ADDRESS_DAC2_VALUE, mV / 5);
    }

    /**
     * Checks the Bit enabling DAC2 BUFFER output
     *
     * @return false: Disable the DAC2 BUFFER output.
     * true: Enable the DAC2 BUFFER output.
     */
    public boolean isDac2BufferEnabled() {
        return (mRegister.readBuffer(ADDRESS_DAC_CONFIG) & DAC2_BUF_EN) == Dac2Buf_Enable;
    }

    /**
     * Sets the Bit enabling DAC2 BUFFER output
     *
     * @param enabled: This parameter contains DAC2 BUFFER output
     *                 This parameter can be one of the following values:
     *                 false: Disable the DAC2 BUFFER output.
     *                 true: Enable the DAC2 BUFFER output.
     */
    public void setDac2BufferEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC2_BUF_EN, Dac2Buf_Enable);
        } else {
            mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC2_BUF_EN, Dac2Buf_Disable);
        }
    }

    /**
     * Checks the Bit enabling DAC1 BUFFER output
     *
     * @return false: Disable the DAC1 BUFFER output.
     * true: Enable the DAC1 BUFFER output.
     */
    public boolean isDac1BufferEnabled() {
        return (mRegister.readBuffer(ADDRESS_DAC_CONFIG) & DAC1_BUF_EN) == Dac1Buf_Enable;
    }

    /**
     * Sets the Bit enabling DAC1 BUFFER output
     *
     * @param enabled: This parameter contains DAC1 BUFFER output
     *                 This parameter can be one of the following values:
     *                 false: Disable the DAC1 BUFFER output.
     *                 true: Enable the DAC1 BUFFER output.
     */
    public void setDac1BufferEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC1_BUF_EN, Dac1Buf_Enable);
        } else {
            mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC1_BUF_EN, Dac1Buf_Disable);
        }
    }

    /**
     * Gets the RE (in ChemSen mode) pin and VG (in ISFET mode) pin channel selection
     *
     * @return bit controlling pin channel selection
     */
    public int getReVgChannel() {
        return mRegister.readBuffer(ADDRESS_DAC_CONFIG) & RE_VG_CH;
    }

    /**
     * Sets the RE (in ChemSen mode) pin and VG (in ISFET mode) pin channel selection
     *
     * @param channel: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #ReVg_Ch0}: Pin channel 0 is selected.
     *                {@link #ReVg_Ch1}: Pin channel 1 is selected.
     *                {@link #ReVg_Ch2}: Pin channel 2 is selected.
     *                {@link #ReVg_Ch3}: Pin channel 3 is selected.
     */
    public void setReVgChannel(int channel) {
        mRegister.writeParams(ADDRESS_DAC_CONFIG, RE_VG_CH, channel);
    }

    /**
     * Gets the WE (in ChemSen mode) pin, VD (in ISFET mode) pin and DAC2 pin channel selection
     *
     * @return bit controlling pin channel selection
     */
    public int getDac2WeVdChannel() {
        return mRegister.readBuffer(ADDRESS_DAC_CONFIG) & DAC2_WE_VD_CH;
    }

    /**
     * Sets the WE (in ChemSen mode) pin, VD (in ISFET mode) pin and DAC2 pin channel selection
     *
     * @param channel: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #Dac2WeVd_Ch0}: Pin channel 0 is selected.
     *                {@link #Dac2WeVd_Ch1}: Pin channel 1 is selected.
     *                {@link #Dac2WeVd_Ch2}: Pin channel 2 is selected.
     *                {@link #Dac2WeVd_Ch3}: Pin channel 3 is selected.
     */
    public void setDac2WeVdChannel(int channel) {
        mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC2_WE_VD_CH, channel);
    }

    /**
     * Gets the CE (in ChemSen mode) pin and DAC1 pin channel selection
     *
     * @return bit controlling pin channel selection
     */
    public int getDac1CeChannel() {
        return mRegister.readBuffer(ADDRESS_DAC_CONFIG) & DAC1_CE_CH;
    }

    /**
     * Sets the CE (in ChemSen mode) pin and DAC1 pin channel selection
     *
     * @param channel: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #Dac1Ce_Ch0}: Pin channel 0 is selected.
     *                {@link #Dac1Ce_Ch1}: Pin channel 1 is selected.
     *                {@link #Dac1Ce_Ch2}: Pin channel 2 is selected.
     *                {@link #Dac1Ce_Ch3}: Pin channel 3 is selected.
     */
    public void setDac1CeChannel(int channel) {
        mRegister.writeParams(ADDRESS_DAC_CONFIG, DAC1_CE_CH, channel);
    }
}
