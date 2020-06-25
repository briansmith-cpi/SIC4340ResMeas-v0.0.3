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

import com.sic.module.nfc.global.AdcProvider;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/16/2014
 */
public class Adc extends AdcProvider {

    private static final byte ADDRESS_ADC_DIVIDER = Register.ADC_DIVIDER;
    private static final byte ADDRESS_ADC_PRESCALER = Register.ADC_PRESCALER;
    private static final byte ADDRESS_ADC_SAMP_DELAY = Register.ADC_SAMP_DLY;
    private static final byte ADDRESS_ADC_NWAIT = Register.ADC_NWAIT;
    private static final byte ADDRESS_ADC_NBIT = Register.ADC_NBIT;
    private static final byte ADDRESS_ADC_CONV_CFG = Register.ADC_CONV_CFG;
    private static final byte ADDRESS_ADC_BUFF_CFG = Register.ADC_BUF_CFG;
    private static final byte ADDRESS_ADC_CH_CFG = Register.ADC_CH_CFG;
    private static final String TAG = Adc.class.getSimpleName();

    // address 0x04
    public static final int ADC_DIVIDER = 0xFF;

    // address 0x05
    public static final int ADC_PRESCALER = 0x07;

    // address 0x06
    public static final int ADC_SAMP_DELAY_TIME = 0xFF;

    // address 0x07
    public static final int NWAIT_MUL = 0xF0;
    public static final int NWAIT_PRESCALER = 0x0F;

    // address 0x08
    public static final int ADC_AVG = 0x70;
    public static final int AvgTimes_1 = 0x00;
    public static final int AvgTimes_2 = 0x10;
    public static final int AvgTimes_4 = 0x20;
    public static final int AvgTimes_8 = 0x30;
    public static final int AvgTimes_16 = 0x40;
    public static final int AvgTimes_32 = 0x50;
    public static final int AvgTimes_64 = 0x60;
    public static final int AvgTimes_128 = 0x70;

    public static final int ADC_SIGNED = 0x08;
    public static final int Result_UnsignedBit = 0x00;
    public static final int Result_SignedBit = 0x08;

    public static final int ADC_OSR = 0x07;
    public static final int Osr_32_6bit = 0x00;
    public static final int Osr_64_8bit = 0x01;
    public static final int Osr_128_10bit = 0x02;
    public static final int Osr_256_12bit = 0x03;
    public static final int Osr_512_14bit = 0x04;
    public static final int Osr_1024_14bit = 0x05;
    public static final int Osr_2048_14bit = 0x06;
    public static final int Osr_4096_14bit = 0x07;

    // address 0x09
    public static final int ADC_TICK_PERIOD = 0x70;
    public static final int TickPeriod_50_ms = 0x00;
    public static final int TickPeriod_100_ms = 0x10;
    public static final int TickPeriod_200_ms = 0x20;
    public static final int TickPeriod_500_ms = 0x30;
    public static final int TickPeriod_1000_ms = 0x40;
    public static final int TickPeriod_2000_ms = 0x50;
    public static final int TickPeriod_5000_ms = 0x60;
    public static final int TickPeriod_10000_ms = 0x70;

    public static final int ADC_TICK_RSP = 0x04;
    public static final int TickRsp_Immediately = 0x00;
    public static final int TickRsp_AtTickPeriod = 0x04;

    public static final int ADC_CONV_MODE = 0x03;
    public static final int ConvMode_Normal = 0x00;
    public static final int ConvMode_Continuous = 0x02;
    public static final int ConvMode_Tick = 0x03;

    // address 0x0A
    public static final int ADC_BUFN_EN = 0x20;
    public static final int BufN_Disable = 0x00;
    public static final int BufN_Enable = 0x20;

    public static final int ADC_BUFP_EN = 0x10;
    public static final int BufP_Disable = 0x00;
    public static final int BufP_Enable = 0x10;

    public static final int ADC_LPF = 0x03;
    public static final int Lpf_None = 0x00;
    public static final int Lpf_S1260_D1060 = 0x01;
    public static final int Lpf_S623_D530 = 0x02;
    public static final int Lpf_S325_D280 = 0x03;

    // address 0x0B
    public static final int ADC_CHN_GND = 0x10;
    public static final int ChNGnd_DifferentialMode = 0x00;
    public static final int ChNGnd_SingleEndedMode = 0x10;

    public static final int ADC_CHN = 0x0C;
    public static final int ChN_Channel0 = 0x00;
    public static final int ChN_Channel1 = 0x04;
    public static final int ChN_Channel2 = 0x08;
    public static final int ChN_Channel3 = 0x0C;

    public static final int ADC_CHP = 0x03;
    public static final int ChP_Channel0 = 0x00;
    public static final int ChP_Channel1 = 0x01;
    public static final int ChP_Channel2 = 0x02;
    public static final int ChP_Channel3 = 0x03;

    private static Adc instance;

    private Adc(Sic4340 sic4340) {
        super(sic4340);
    }

    public static Adc getInstance(Sic4340 sic4340) {
        if (instance == null)
            instance = new Adc(sic4340);
        return instance;
    }

    @Override
    protected void setRegisterAddress() {
        ADDRESS_ADC_RESULT_0 = Register.ADC_RESULT_0;
        ADDRESS_ADC_RESULT_1 = Register.ADC_RESULT_1;
        ADDRESS_ADC_RESULT_2 = Register.ADC_RESULT_2;
    }

    @Override
    protected void setDefaultBit() {

    }

    /**
     * Gets the Adc sampling frequency.
     *
     * @return Adc sampling frequency.
     */
    public int getSamplingFrequency() {
        int d = mRegister.readBuffer(ADDRESS_ADC_DIVIDER);
        int p = mRegister.readBuffer(ADDRESS_ADC_PRESCALER) & 0x07;
        return (int) Math.round(13560000 / (Math.pow(2, p) * (128 + d)));
    }

    /**
     * Sets the Adc sampling frequency.
     *
     * @param frequency: Specifies the Adc sampling frequency to be set the ADC_Divider (M) and
     *                   ADC_Prescaler (N) register. (Hz)
     */
    public void setSamplingFrequency(int frequency) {
        int adcDivider = 0;
        int adcPrescaler = 0;
        double value = 1000;
        double equationConstant = 13560000 / frequency;

        for (int p = 0; p < 7; ++p) {
            int start = -1;
            int end = 256;
            int mid = (start + end) >> 1;
            double equationVariable = Math.pow(2, p) * (128 + end);
            double equation;
            if (equationVariable < equationConstant) {
                continue;
            }
            while (mid != start && mid != end && value > 0.0000001) {
                equationVariable = Math.pow(2, p) * (128 + end);
                equation = Math.abs(equationVariable - equationConstant);

                if (equationVariable < equationConstant) {
                    start = mid;
                } else if (equationVariable > equationConstant) {
                    end = mid;
                }

                if (equation < value) {
                    adcDivider = mid;
                    adcPrescaler = p;
                    value = equation;
                }
                mid = (start + end) >> 1;
            }
        }

        mRegister.write(ADDRESS_ADC_DIVIDER, adcDivider);
        mRegister.write(ADDRESS_ADC_PRESCALER, adcPrescaler);
    }

    /**
     * Gets the number of sampling clock before Adc process the input data.
     *
     * @return the number of sampling clock
     */
    public int getNwaitClk() {
        byte nwait = mRegister.readBuffer(ADDRESS_ADC_NWAIT);
        int m = (nwait & 0xF0) >> 4;
        int n = nwait & 0x0F;
        return (int) (8 + (m * Math.pow(2, n)));
    }

    /**
     * Sets the number of sampling clock before Adc process the input data.
     *
     * @param clock: Specifies the sampling clock to be set the ADC_NWait register. (clock)
     */
    public void setNwaitClk(int clock) {
        int nWaitMul = 0;
        int nWaitPrescaler = 0;
        double value = 1000;
        double equationConstant = clock - 8;

        for (int n = 0; n < 15; ++n) {
            int start = -1;
            int end = 16;
            int mid = (start + end) >> 1;
            double equationVariable = end * Math.pow(2, n);
            double equation;
            if (equationVariable < equationConstant) {
                continue;
            }
            while (mid != start && mid != end && value > 0.0000001) {
                equationVariable = end * Math.pow(2, n);
                equation = Math.abs(equationVariable - equationConstant);

                if (equationVariable < equationConstant) {
                    start = mid;
                } else if (equationVariable > equationConstant) {
                    end = mid;
                }

                if (equation < value) {
                    nWaitMul = mid;
                    nWaitPrescaler = n;
                    value = equation;
                }
                mid = (start + end) >> 1;
            }
        }

        mRegister.write(ADDRESS_ADC_NWAIT, (nWaitMul << 4) | nWaitPrescaler);
    }

    /**
     * Gets the number of sampling clock before Adc process the input data.
     *
     * @return the number of sampling clock
     */
    public int getSampDelayTime() {
        int p = mRegister.readBuffer(ADDRESS_ADC_PRESCALER) & 0x07;
        int ts = mRegister.readBuffer(ADDRESS_ADC_SAMP_DELAY);
        return (int) (Math.pow(2, p) * (ts + 1) / 13560000);
    }

    /**
     * Sets the Adc sampling time for reach sampling period.
     *
     * @param tsamp: Adc sampling time (second)
     */
    public void setTSamp(int tsamp) {
        int p = mRegister.readBuffer(ADDRESS_ADC_PRESCALER) & 0x07;
        mRegister.write(ADDRESS_ADC_SAMP_DELAY, (byte) ((tsamp * 13560000 / Math.pow(2, p)) - 1));
    }

    /**
     * Gets the number of average time for Adc Result.
     *
     * @return the number of sampling clock
     */
    public byte getAverageTime() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_NBIT) & ADC_AVG);
    }

    /**
     * Sets the number of average time for Adc Result.
     *
     * @param adcAvg: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #AvgTimes_1}: 1 time
     *                {@link #AvgTimes_2}: 2 times
     *                {@link #AvgTimes_4}: 4 times
     *                {@link #AvgTimes_8}: 8 times
     *                {@link #AvgTimes_16}: 16 times
     *                {@link #AvgTimes_32}: 32 times
     *                {@link #AvgTimes_64}: 64 times
     *                {@link #AvgTimes_128}: 128 times
     */
    public void setAverageTime(int adcAvg) {
        mRegister.writeParams(ADDRESS_ADC_NBIT, ADC_AVG, adcAvg);
    }

    /**
     * Gets the pattern of ADC_Result
     *
     * @return the number of sampling clock
     */
    public byte getAdcResultPattern() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_NBIT) & ADC_SIGNED);
    }

    /**
     * Sets the pattern of ADC_Result
     *
     * @param adcSigned: Specifies the port bit to be set.
     *                   This parameter can be one of the following values:
     *                   {@link #Result_SignedBit}: signed bit
     *                   {@link #Result_UnsignedBit}: unsigned bit
     */
    public void setAdcResultPattern(int adcSigned) {
        mRegister.writeParams(ADDRESS_ADC_NBIT, ADC_SIGNED, adcSigned);
    }

    /**
     * Gets the number of Oversampling Ratio (OSR) of Adc, Number of valid bit for ADC_Result
     *
     * @return the number of sampling clock
     */
    public byte getOsr() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_NBIT) & ADC_OSR);
    }

    /**
     * Sets the number of Oversampling Ratio (OSR) of Adc, Number of valid bit for ADC_Result
     *
     * @param adcOsr: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #Osr_32_6bit}: 1 time
     *                {@link #Osr_64_8bit}: 2 times
     *                {@link #Osr_128_10bit}: 4 times
     *                {@link #Osr_256_12bit}: 8 times
     *                {@link #Osr_512_14bit}: 16 times
     *                {@link #Osr_1024_14bit}: 32 times
     *                {@link #Osr_2048_14bit}: 64 times
     *                {@link #Osr_4096_14bit}: 128 times
     */
    public void setOsr(int adcOsr) {
        mRegister.writeParams(ADDRESS_ADC_NBIT, ADC_OSR, adcOsr);
    }

    /**
     * Gets the pattern of ADC_Result
     *
     * @return the number of sampling clock
     */
    public byte getTickPeriod() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CONV_CFG) & ADC_TICK_PERIOD);
    }

    /**
     * Sets the period of Adc sampling when ADC_CONV_Mode is configured to TICK
     *
     * @param adcTickPeriod: Specifies the port bit to be set.
     *                       This parameter can be one of the following values:
     *                       {@link #TickPeriod_50_ms}: 50 ms
     *                       {@link #TickPeriod_100_ms}: 100 ms
     *                       {@link #TickPeriod_200_ms}: 200 ms
     *                       {@link #TickPeriod_500_ms}: 500 ms
     *                       {@link #TickPeriod_1000_ms}: 1 s
     *                       {@link #TickPeriod_2000_ms}: 2 s
     *                       {@link #TickPeriod_5000_ms}: 5 s
     *                       {@link #TickPeriod_10000_ms}: 10 s
     */
    public void setTickPeriod(int adcTickPeriod) {
        mRegister.writeParams(ADDRESS_ADC_CONV_CFG, ADC_TICK_PERIOD, adcTickPeriod);
    }

    /**
     * Gets the response pattern for Get_ADC command when configured to TICK Mode
     *
     * @return the response pattern
     */
    public byte getTickResponse() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CONV_CFG) & ADC_TICK_RSP);
    }

    /**
     * Sets the response pattern for Get_ADC command when configured to TICK Mode
     *
     * @param adcTickRsp: Specifies the port bit to be set.
     *                    This parameter can be one of the following values:
     *                    {@link #TickRsp_Immediately}: Response immediately after Get_ADC command
     *                    {@link #TickRsp_AtTickPeriod}: Response at tick period
     */
    public void setTickResponse(int adcTickRsp) {
        mRegister.writeParams(ADDRESS_ADC_CONV_CFG, ADC_TICK_RSP, adcTickRsp);
    }

    /**
     * Gets the Adc conversion method
     *
     * @return the method
     */
    public byte getConvertMode() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CONV_CFG) & ADC_CONV_MODE);
    }

    /**
     * Sets the Adc conversion method
     *
     * @param adcConvMode: Specifies the port bit to be set.
     *                     This parameter can be one of the following values:
     *                     {@link #ConvMode_Normal}: Adc is converted when Get_ADC is executed
     *                     {@link #ConvMode_Continuous}: Adc is continuously converted
     *                     {@link #ConvMode_Tick}: Adc is automatically converted every tick period
     */
    public void setConvertMode(int adcConvMode) {
        mRegister.writeParams(ADDRESS_ADC_CONV_CFG, ADC_CONV_MODE, adcConvMode);
    }

    /**
     * Checks the Bit enabling Adc input BUFFER – N side
     *
     * @return false: Disable the Adc input Buffer.
     * true: Enable the Adc input Buffer.
     */
    public boolean isBufferNEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_BUFF_CFG) & ADC_BUFN_EN) == BufN_Enable;
    }

    /**
     * Sets the Bit enabling Adc input BUFFER – N side
     *
     * @param enabled: This parameter contains the Adc input Buffer
     *                 This parameter can be one of the following values:
     *                 false: Disable the Adc input Buffer.
     *                 true: Enable the Adc input Buffer.
     */
    public void setBufferNEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_ADC_BUFF_CFG, ADC_BUFN_EN, BufN_Enable);
        } else {
            mRegister.writeParams(ADDRESS_ADC_BUFF_CFG, ADC_BUFN_EN, BufN_Disable);
        }
    }

    /**
     * Checks the Bit enabling Adc input BUFFER – P side
     *
     * @return false: Disable the Adc input Buffer.
     * true: Enable the Adc input Buffer.
     */
    public boolean isBufferPEnabled() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_BUFF_CFG) & ADC_BUFP_EN) == BufP_Enable;
    }

    /**
     * Sets the Bit enabling Adc input BUFFER – P side
     *
     * @param enabled: This parameter contains the Adc input Buffer
     *                 This parameter can be one of the following values:
     *                 false: Disable the Adc input Buffer.
     *                 true: Enable the Adc input Buffer.
     */
    public void setBufferPEnabled(boolean enabled) {
        if (enabled) {
            mRegister.writeParams(ADDRESS_ADC_BUFF_CFG, ADC_BUFP_EN, BufP_Enable);
        } else {
            mRegister.writeParams(ADDRESS_ADC_BUFF_CFG, ADC_BUFP_EN, BufP_Disable);
        }
    }

    /**
     * Gets the input low pass filter for Adc
     *
     * @return low pass filter bit
     */
    public byte getLowPassFilter() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_BUFF_CFG) & ADC_LPF);
    }

    /**
     * Sets the input low pass filter for Adc
     *
     * @param adcLpf: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #Lpf_None}: No LPF.
     *                {@link #Lpf_S1260_D1060}: Low pass filter 1260 kHz or 1060 kHz
     *                {@link #Lpf_S623_D530}: Low pass filter 623 kHz or 530 kHz
     *                {@link #Lpf_S325_D280}: Low pass filter 325 kHz or 280 kHz
     */
    public void setLowPassFilter(int adcLpf) {
        mRegister.writeParams(ADDRESS_ADC_BUFF_CFG, ADC_LPF, adcLpf);
    }

    /**
     * Gets the bit controlling the Adc input
     *
     * @return bit controlling mode
     */
    public byte getChannelN_Ground() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CH_CFG) & ADC_CHN_GND);
    }

    /**
     * Sets the bit controlling the Adc input
     *
     * @param adcChnGnd: Specifies the port bit to be set.
     *                   This parameter can be one of the following values:
     *                   {@link #ChNGnd_DifferentialMode}: differential mode
     *                   {@link #ChNGnd_SingleEndedMode}: single ended mode (Channel N is internal
     *                   connected to GND)
     */
    public void setChannelN_Ground(int adcChnGnd) {
        mRegister.writeParams(ADDRESS_ADC_CH_CFG, ADC_CHN_GND, adcChnGnd);
    }

    /**
     * Gets the Adc input -N channel
     *
     * @return input channel
     */
    public byte getChannelN() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CH_CFG) & ADC_CHN);
    }

    /**
     * Sets the Adc input -N channel
     *
     * @param adcChn: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #ChN_Channel0}: Channel N at Gpio 0
     *                {@link #ChN_Channel1}: Channel N at Gpio 1
     *                {@link #ChN_Channel2}: Channel N at Gpio 2
     *                {@link #ChN_Channel3}: Channel N at Gpio 3
     */
    public void setChannelN(int adcChn) {
        mRegister.writeParams(ADDRESS_ADC_CH_CFG, ADC_CHN, adcChn);
    }

    /**
     * Gets the Adc input -P channel
     *
     * @return input channel
     */
    public byte getChannelP() {
        return (byte) (mRegister.readBuffer(ADDRESS_ADC_CH_CFG) & ADC_CHP);
    }

    /**
     * Sets the Adc input -P channel
     *
     * @param adcChp: Specifies the port bit to be set.
     *                This parameter can be one of the following values:
     *                {@link #ChP_Channel0}: Channel P at Gpio 0
     *                {@link #ChP_Channel1}: Channel P at Gpio 1
     *                {@link #ChP_Channel2}: Channel P at Gpio 2
     *                {@link #ChP_Channel3}: Channel P at Gpio 3
     */
    public void setChannelP(int adcChp) {
        mRegister.writeParams(ADDRESS_ADC_CH_CFG, ADC_CHP, adcChp);
    }
}
