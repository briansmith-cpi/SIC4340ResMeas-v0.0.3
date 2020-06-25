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

import com.sic.module.nfc.global.ConfigProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Config extends ConfigProvider {
    private static final byte ADDRESS_CHEM_CFG = Register.CHEM_CFG;
    private static final byte ADDRESS_VDD_CFG = Register.VDD_CFG;
    private static final byte ADDRESS_TEMPSEN_CFG = Register.TEMPSEN_CFG;
    private static final byte ADDRESS_SENSOR_CFG = Register.SENSOR_CONFIG;
    private static final byte ADDRESS_GAP_CMPEN_CFG = Register.GAP_WID_CFG;
    private static final String TAG = Config.class.getSimpleName();

    public static final int CHEM_WE_GND = 0x04;
    public static final int ChemWeGnd_NoConnect = 0x00;
    public static final int ChemWeGnd_Connected = 0x04;
    public static final int CHEM_RE_CE_SHT = 0x02;
    public static final int ChemReCe_NoConnect = 0x00;
    public static final int ChemReCe_Connected = 0x02;
    public static final int CHEM_RNG = 0x01;
    public static final int ChemRng_2_5uA = 0x00;
    public static final int ChemRng_20uA = 0x01;
    public static final int VDD_EN = 0x01;
    public static final int Vdd_Disable = 0x00;
    public static final int Vdd_Enable = 0x01;
    public static final int ADC_I_MODE = 0x02;
    public static final int AdcIMode_Voltage = 0x00;
    public static final int AdcIMode_Current = 0x02;
    public static final int TEMPSEN_MODE = 0x01;
    public static final int TempSenMode_Disable = 0x00;
    public static final int TempSenMode_Enable = 0x01;

    public static final int CHEM_SENS_EN = 0x40;
    public static final int ChemSens_Disable = 0x00;
    public static final int ChemSens_Enable = 0x01;
    public static final int TEMP_EN = 0x20;
    public static final int Temp_Disable = 0x00;
    public static final int Temp_Enable = 0x20;
    public static final int ISFET_EN = 0x10;
    public static final int Isfet_Disable = 0x00;
    public static final int Isfet_Enable = 0x10;
    public static final int CHEM_EN = 0x08;
    public static final int Chem_Disable = 0x00;
    public static final int Chem_Enable = 0x08;
    public static final int IDRV_EN = 0x04;
    public static final int Idrv_Disable = 0x00;
    public static final int Idrv_Enable = 0x04;
    public static final int DAC_EN = 0x02;
    public static final int Dac_Disable = 0x00;
    public static final int Dac_Enable = 0x02;
    public static final int ADC_ANA_EN = 0x01;
    public static final int AdcAna_Disable = 0x00;
    public static final int AdcAna_Enable = 0x01;
    public static final int GAP_CMPEN_EN = 0x10;
    public static final int GapCmpen_Disable = 0x00;
    public static final int GapCmpen_Enable = 0x10;
    public static final int GAP_WID_CMPEN = 0x07;
    public static final int GapWidCmpen_32_Clk = 0x00;
    public static final int GapWidCmpen_36_Clk = 0x01;
    public static final int GapWidCmpen_40_Clk = 0x02;
    public static final int GapWidCmpen_44_Clk = 0x03;
    public static final int GapWidCmpen_16_Clk = 0x04;
    public static final int GapWidCmpen_20_Clk = 0x05;
    public static final int GapWidCmpen_24_Clk = 0x06;
    public static final int GapWidCmpen_28_Clk = 0x07;

    private static Config instance;

    private Config(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Config getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Config(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
    }

    @Override
    protected void setDefaultBit() {


    }

    /**
     * Checks the 1 pins connection, WE pins are connected to GND or not?
     *
     * @return false: not connection.
     * true: WE pins are connected to GND.
     */
    public boolean isWeGndConnected() {
        return (byte) (mRegister.readBuffer(ADDRESS_CHEM_CFG) & CHEM_WE_GND) == ChemWeGnd_Connected;
    }

    /**
     * Sets the bit controlling sensor connection
     *
     * @param connected: This parameter contains WE and GND pin
     *                   This parameter can be one of the following values:
     *                   false: not connection.
     *                   true: WE pins are connected to GND.
     */
    public void setWeGndConnected(boolean connected) {
        if (connected) {
            mRegister.writeParams(ADDRESS_CHEM_CFG, CHEM_WE_GND, ChemWeGnd_Connected);
        } else {
            mRegister.writeParams(ADDRESS_CHEM_CFG, CHEM_WE_GND, ChemWeGnd_NoConnect);
        }
    }

    /**
     * Checks the 2 pins connection, CE and RE are internally connected or not?
     *
     * @return false: not connection.
     * true: CE and RE are internally connected.
     */
    public boolean isReCeShortCircuit() {
        return (byte) (mRegister.readBuffer(ADDRESS_CHEM_CFG) & CHEM_RE_CE_SHT) == ChemReCe_Connected;
    }

    /**
     * Sets the bit controlling sensor connection
     *
     * @param connected: This parameter contains CE and RE pin
     *                   This parameter can be one of the following values:
     *                   false: not connection.
     *                   true: CE and RE are internally connected.
     */
    public void setReCeShortCircuit(boolean connected) {
        if (connected) {
            mRegister.writeParams(ADDRESS_CHEM_CFG, CHEM_RE_CE_SHT, ChemReCe_Connected);
        } else {
            mRegister.writeParams(ADDRESS_CHEM_CFG, CHEM_RE_CE_SHT, ChemReCe_NoConnect);
        }
    }

    /**
     * Gets the input current range
     *
     * @return input current range [+/- 2.5 uA or +/- 20 uA]
     */
    public byte getCurrentRange() {
        return (byte) (mRegister.readBuffer(ADDRESS_CHEM_CFG) & CHEM_RNG);
    }

    /**
     * Sets the input current range
     *
     * @param range: Specifies the port bit to be set.
     *               This parameter can be one of the following values:
     *               {@link #ChemRng_2_5uA}: +/- 2.5 uA
     *               {@link #ChemRng_20uA}: +/- 20 uA
     */
    public void setCurrentRange(int range) {
        mRegister.writeParams(ADDRESS_CHEM_CFG, CHEM_RNG, range);
    }

    /**
     * Checks the LDO 1.8 V for sensor biasing circuit
     *
     * @return false: not connection.
     * true: CE and RE are internally connected.
     */
    public boolean isVddEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_VDD_CFG) & VDD_EN) == Vdd_Enable;
    }

    /**
     * Sets the bit enabling LDO 1.8 V for sensor biasing circuit
     *
     * @param enabled: This parameter contains VDD
     *                 This parameter can be one of the following values:
     *                 false: Disable the LDO 1.8V.
     *                 true: Enable the LDO 1.8V.
     */
    public void setVddEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_VDD_CFG, VDD_EN, Vdd_Enable);
        } else {
            mRegister.writeParams(ADDRESS_VDD_CFG, VDD_EN, Vdd_Disable);
        }
    }

    /**
     * Gets the bit controlling the Adc input mode
     *
     * @return Adc input mode [voltage mode or current mode]
     */
    public int getAdcIMode() {
        return (byte) (mRegister.readBuffer(ADDRESS_TEMPSEN_CFG) & ADC_I_MODE);
    }

    /**
     * Sets the bit controlling the Adc input mode
     *
     * @param mode: Specifies the port bit to be set.
     *              This parameter can be one of the following values:
     *              {@link #AdcIMode_Voltage}: voltage mode
     *              {@link #AdcIMode_Current}: current mode (transfer to voltage
     *              by transimpedance circuit )
     */
    public void setAdcIMode(int mode) {
        mRegister.writeParams(ADDRESS_TEMPSEN_CFG, ADC_I_MODE, mode);
    }

    /**
     * Checks the bit enabling temperature sensor
     *
     * @return false: default mode.
     * true: temperature sensor mode.
     */
    public boolean isTempSenModeEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_TEMPSEN_CFG) & TEMPSEN_MODE) == TempSenMode_Enable;
    }

    /**
     * Sets the bit enabling temperature sensor. All device can switch from its default mode to
     * temperature sensor mode by change this.
     *
     * @param enabled: This parameter contains the temperature senosr mode
     *                 This parameter can be one of the following values:
     *                 false: default mode.
     *                 true: temperature sensor mode.
     */
    public void setTempSenModeEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_TEMPSEN_CFG, TEMPSEN_MODE, TempSenMode_Enable);
        } else {
            mRegister.writeParams(ADDRESS_TEMPSEN_CFG, TEMPSEN_MODE, TempSenMode_Disable);
        }
    }

    /**
     * Checks the bit enabling chemical current sensing circuit
     *
     * @return false: disable the chemical current sensor.
     * true: enable the chemical current sensor.
     */
    public boolean isChemicalSensorEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & CHEM_SENS_EN) == ChemSens_Enable;
    }

    /**
     * Sets the bit enabling chemical current sensing circuit
     *
     * @param enabled: This parameter contains the chemical current sensing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the chemical current sensor.
     *                 true: enable the chemical current sensor.
     */
    public void setChemicalSensorEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, CHEM_SENS_EN, ChemSens_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, CHEM_SENS_EN, ChemSens_Disable);
        }
    }

    /**
     * Checks the bit enabling temperature sensor
     *
     * @return false: disable the temperature sensor.
     * true: enable the temperature sensor.
     */
    public boolean isTempertureEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & TEMP_EN) == Temp_Enable;
    }

    /**
     * Sets the bit enabling temperature sensor
     *
     * @param enabled: This parameter contains the chemical current sensing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the temperature sensor.
     *                 true: enable the temperature sensor.
     */
    public void setTempertureEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, TEMP_EN, Temp_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, TEMP_EN, Temp_Disable);
        }
    }

    /**
     * Checks the bit enabling isfet biasing circuit
     *
     * @return false: disable the isfet biasing circuit
     * true: enable the isfet biasing circuit
     */
    public boolean isIsfetEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & ISFET_EN) == Isfet_Enable;
    }

    /**
     * Sets the bit enabling isfet biasing circuit
     *
     * @param enabled: This parameter contains the isfet biasing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the isfet biasing circuit.
     *                 true: enable the isfet biasing circuit.
     */
    public void setIsfetEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, ISFET_EN, Isfet_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, ISFET_EN, Isfet_Disable);
        }
    }

    /**
     * Checks the bit enabling chemical biasing circuit
     *
     * @return false: disable the chemical biasing circuit
     * true: enable the chemical biasing circuit
     */
    public boolean isChemicalEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & CHEM_EN) == Chem_Enable;
    }

    /**
     * Sets the bit enabling chemical biasing circuit
     *
     * @param enabled: This parameter contains the chemical biasing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the chemical biasing circuit.
     *                 true: enable the chemical biasing circuit.
     */
    public void setChemicalEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, CHEM_EN, Chem_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, CHEM_EN, Chem_Disable);
        }
    }

    /**
     * Checks the bit enabling current driving circuit
     *
     * @return false: disable the current driving circuit
     * true: enable the current driving circuit
     */
    public boolean isIDRVEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & IDRV_EN) == Idrv_Enable;
    }

    /**
     * Sets the bit enabling current driving circuit
     *
     * @param enabled: This parameter contains the chemical biasing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the current driving circuit
     *                 true: enable the current driving circuit
     */
    public void setIDRVEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, IDRV_EN, Idrv_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, IDRV_EN, Idrv_Disable);
        }
    }

    /**
     * Checks the bit enabling Dac
     *
     * @return false: disable the Dac
     * true: enable the Dac
     */
    public boolean isDacEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & DAC_EN) == Dac_Enable;
    }

    /**
     * Sets the bit enabling Dac
     *
     * @param enabled: This parameter contains the chemical biasing circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the Dac
     *                 true: enable the Dac
     */
    public void setDacEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, DAC_EN, Dac_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, DAC_EN, Dac_Disable);
        }
    }

    /**
     * Checks the bit enabling Adc – Analog circuit
     *
     * @return false: disable the Adc – Analog circuit
     * true: enable the Adc – Analog circuit
     */
    public boolean isAdcAnalogEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_SENSOR_CFG) & ADC_ANA_EN) == AdcAna_Enable;
    }

    /**
     * Sets the bit enabling Adc – Analog circuit
     *
     * @param enabled: This parameter contains the Adc – Analog circuit
     *                 This parameter can be one of the following values:
     *                 false: disable the Adc – Analog circuit
     *                 true: enable the Adc – Analog circuit
     */
    public void setAdcAnalogEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, ADC_ANA_EN, AdcAna_Enable);
        } else {
            mRegister.writeParams(ADDRESS_SENSOR_CFG, ADC_ANA_EN, AdcAna_Disable);
        }
    }

    /**
     * Checks the bit enabling timer compensation when gap appear in ADC_TICK Mode
     *
     * @return false: disable the timer compensation
     * true: enable the timer compensation
     */
    public boolean isGapCompensationEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_GAP_CMPEN_CFG) & GAP_CMPEN_EN) == GapCmpen_Enable;
    }

    /**
     * Sets the bit enabling timer compensation when gap appear in ADC_TICK Mode
     *
     * @param enabled: This parameter contains the timer compensation
     *                 This parameter can be one of the following values:
     *                 false: disable the timer compensation
     *                 true: enable the timer compensation
     */
    public void setGapCompensationEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_GAP_CMPEN_CFG, GAP_CMPEN_EN, GapCmpen_Enable);
        } else {
            mRegister.writeParams(ADDRESS_GAP_CMPEN_CFG, GAP_CMPEN_EN, GapCmpen_Disable);
        }
    }

    /**
     * Gets the value setting value of compensation
     * Default gap width from ISO14443A is 32 CLK.
     *
     * @return value of compensation
     */
    public int getGapWidthCompensation() {
        return (byte) (mRegister.readBuffer(ADDRESS_GAP_CMPEN_CFG) & GAP_WID_CMPEN);
    }

    /**
     * Sets the value setting value of compensation
     * Default gap width from ISO14443A is 32 CLK.
     *
     * @param width: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #GapWidCmpen_16_Clk}: Gap width is 16 CLK.
     *                {@link #GapWidCmpen_20_Clk}: Gap width is 20 CLK.
     *                {@link #GapWidCmpen_24_Clk}: Gap width is 24 CLK.
     *                {@link #GapWidCmpen_28_Clk}: Gap width is 28 CLK.
     *                {@link #GapWidCmpen_32_Clk}: Gap width is 32 CLK.
     *                {@link #GapWidCmpen_36_Clk}: Gap width is 36 CLK.
     *                {@link #GapWidCmpen_40_Clk}: Gap width is 40 CLK.
     *                {@link #GapWidCmpen_44_Clk}: Gap width is 44 CLK.
     */
    public void setGapWidthCompensation(int width) {
            mRegister.writeParams(ADDRESS_GAP_CMPEN_CFG, GAP_WID_CMPEN, width);
    }
}
