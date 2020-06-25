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
package com.sic.resmeasure.utils;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 06/Oct/2016
 */
public class ResultRx {
    private int state;
    private int adcResult;
    private float ppmRegression;
    private byte[] eepromResult;
    private int index;

    public static ResultRx newInstance(int state) {
        return new ResultRx(state);
    }

    private ResultRx(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public int getAdcResult() {
        return adcResult;
    }

    public ResultRx setAdcResult(int adcResult) {
        this.adcResult = adcResult;
        return this;
    }

    public float getPpmRegression() {
        return ppmRegression;
    }

    public ResultRx setPpmRegression(float ppmRegression) {
        this.ppmRegression = ppmRegression;
        return this;
    }

    public byte[] getEepromResult() {
        return eepromResult;
    }

    public ResultRx setEepromResult(byte[] eepromResult) {
        this.eepromResult = eepromResult;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ResultRx setIndex(int index) {
        this.index = index;
        return this;
    }
}
