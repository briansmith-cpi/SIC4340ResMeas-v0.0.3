/*
 * Copyright (c) 2016 Silicon Craft Technology Co.,Ltd.
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

import android.util.Log;

import com.sic.module.nfc.global.GpioProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.2
 * @since 25/Nov/2015
 */
public class Gpio extends GpioProvider {

    private static final String TAG = Gpio.class.getSimpleName();

    private static Gpio instance;

    private Gpio(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Gpio getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Gpio(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
        ADDRESS_GPIO_MODE_0 = Register.GPIO_MODE;
        ADDRESS_GPIO_MODE_1 = Register.GPIO_MODE;
        ADDRESS_GPIO_MODE_2 = Register.GPIO_MODE;
        ADDRESS_GPIO_OUT = Register.GPIO_INOUT;
        ADDRESS_GPIO_IN = Register.GPIO_INOUT;
    }

    @Override
    protected void setDefaultBit() {

    }

    @Deprecated
    public Byte getMode(int number) {
        return super.getMode(Address_Mode_0);
    }


    @Deprecated
    public void setMode(int number, int GPIO_Mode) {
        super.setMode(Address_Mode_0, GPIO_Mode);
    }

    @Deprecated
    public void setModeBit(int number, int GPIO_Pin, int GPIO_Mode) {
        super.setModeBit(Address_Mode_0, GPIO_Pin, GPIO_Mode);
    }

    @Override
    public byte getOutput() {
        return (byte) ((mRegister.readBuffer(ADDRESS_GPIO_OUT) >> 4) & 0x0F);
    }


    @Override
    public void setOutput(int GPIO_PortVal) {
        mRegister.write(ADDRESS_GPIO_OUT, (GPIO_PortVal << 4) & 0xF0);
    }


    @Override
    public void setOutputBit(int GPIO_Pin, int GPIO_BitVal) {
        byte pin = (byte) ((GPIO_Pin << 4) & 0xF0);
        byte output = (byte) (mRegister.readBuffer(ADDRESS_GPIO_OUT) & 0xF0);
        if (GPIO_BitVal == Low_Level) {
            output &= ~pin;
        } else {
            output |= pin;
        }
        mRegister.write(ADDRESS_GPIO_OUT, output & 0xF0);
    }

    @Override
    public Byte getInput() {
        Byte recv = mRegister.read(ADDRESS_GPIO_IN);

        if (recv == null) {
            Log.e(TAG, "Can not get Gpio input.");
            return null;
        }

        return (byte) (recv & 0x0F);
    }

    @Override
    public Byte getInputBit(int GPIO_Pin) {
        Byte recv = mRegister.read(ADDRESS_GPIO_IN);
        if (recv == null) {
            Log.e(TAG, "Can not get Gpio input.");
            return null;
        }
        byte input = (byte) (recv & 0x0F);
        byte pin = (byte) (GPIO_Pin & 0x0F);

        if ((input & pin) == pin) {
            return High_Level;
        } else {
            return Low_Level;
        }
    }

    @Override
    public void setHighOutput(int GPIO_Pin) {
        setOutputBit(GPIO_Pin & 0x0F, High_Level);
    }

    @Override
    public void setLowOutput(int GPIO_Pin) {
        setOutputBit(GPIO_Pin & 0x0F, Low_Level);
    }

    @Override
    public void setToggleOutput(int GPIO_Pin) {
        GPIO_Pin &= 0x0F;
        byte output = getOutput();
        byte sel = (byte) (GPIO_Pin & ~output);
        byte uns = (byte) (~GPIO_Pin & output);
        setOutput((byte) (sel | uns) << 4);
    }

    /**
     * get the Gpio mode register directly.
     *
     * @return byte Gpio mode data port value.
     */
    public Byte getMode() {
        return super.getMode(Address_Mode_0);
    }

    /**
     * Sets the Gpio mode register directly.
     *
     * @param GPIO_Mode: Specifies the value to be set to the gpio mode register.
     *                   This parameter can be any combination of the following values:
     *                      At GPIO_MODE_1 Bit (7:4)
     *                   0: Gpio [3:0] pin is digital
     *                   1: Gpio [3:0] pin is analog
     *                      At GPIO_MODE_0 Bit (3:0) when GPIO_MODE_1 is Mode_DigitalFunction
     *                   0: Gpio [3:0] pin is digital input
     *                   1: Gpio [3:0] pin is digital output
     */
    public void setMode(int GPIO_Mode) {
        super.setMode(Address_Mode_0, GPIO_Mode);
    }

    /**
     * Sets the Gpio mode according to the specified parameters.
     *
     * @param GPIO_Pin:  Specifies the port bit to be set.
     *                   This parameter can be any combination of the following values:
     *                   {@link #Pin_0}: Pin 0
     *                   {@link #Pin_1}: Pin 1
     *                   {@link #Pin_2}: Pin 2
     *                   {@link #Pin_3}: Pin 3
     *                   {@link #Pin_All}: All pins
     * @param GPIO_Mode: Specifies the value to be set to the gpio mode register.
     *                   Each bit can be one of the following values:
     *                      At GPIO_MODE_1 Bit (7:4)
     *                   0: Gpio [3:0] pin is digital
     *                   1: Gpio [3:0] pin is analog
     *                      At GPIO_MODE_0 Bit (3:0) when GPIO_MODE_1 is Mode_DigitalFunction
     *                   0: Gpio [3:0] pin is digital input
     *                   1: Gpio [3:0] pin is digital output
     */
    public void setModeBit(int GPIO_Pin, int GPIO_Mode) {
        super.setModeBit(Address_Mode_0, (GPIO_Pin << 4) | GPIO_Pin, GPIO_Mode);
    }

    /**
     * Sets the Gpio according to the Gpio digital input mode.
     *
     * @param GPIO_Pin: Specifies the port bit to be set.
     *                  This parameter can be any combination of the following values:
     *                  {@link #Pin_0}: Pin 0
     *                  {@link #Pin_1}: Pin 1
     *                  {@link #Pin_2}: Pin 2
     *                  {@link #Pin_3}: Pin 3
     *                  {@link #Pin_All}: All pins
     */
    public void setGPIODigitalInput(int GPIO_Pin) {
        GPIO_Pin &= 0x0F;
        byte modeX = (byte) ((GPIO_Pin << 4) | GPIO_Pin);
        byte mode = (byte) (mRegister.readBuffer(ADDRESS_GPIO_MODE_0) & ~modeX);
        mRegister.write(ADDRESS_GPIO_MODE_0, mode);
    }

    /**
     * Sets the Gpio according to the Gpio digital output mode.
     *
     * @param GPIO_Pin: Specifies the port bit to be set.
     *                  This parameter can be any combination of the following values:
     *                  {@link #Pin_0}: Pin 0
     *                  {@link #Pin_1}: Pin 1
     *                  {@link #Pin_2}: Pin 2
     *                  {@link #Pin_3}: Pin 3
     *                  {@link #Pin_All}: All pins
     */
    public void setGPIODigitalOutput(int GPIO_Pin) {
        GPIO_Pin &= 0x0F;
        byte mode0 = (byte) (GPIO_Pin & 0x0F);
        byte mode1 = (byte) ((GPIO_Pin << 4) & 0xF0);
        byte mode = (byte) (mRegister.readBuffer(ADDRESS_GPIO_MODE_0) | mode0 & ~mode1);
        mRegister.write(ADDRESS_GPIO_MODE_0, mode);
    }

    /**
     * Sets the Gpio according to the Analog function output mode.
     *
     * @param GPIO_Pin: Specifies the port bit to be set.
     *                  This parameter can be any combination of the following values:
     *                  {@link #Pin_0}: Pin 0
     *                  {@link #Pin_1}: Pin 1
     *                  {@link #Pin_2}: Pin 2
     *                  {@link #Pin_3}: Pin 3
     *                  {@link #Pin_All}: All pins
     */
    public void setAnalogFunctionMode(int GPIO_Pin) {
        GPIO_Pin &= 0x0F;
        byte mode1 = (byte) ((GPIO_Pin << 4) & 0xF0);
        byte mode = (byte) (mRegister.readBuffer(ADDRESS_GPIO_MODE_0) | mode1);
        mRegister.write(ADDRESS_GPIO_MODE_0, mode);
    }
}
