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
package com.sic.resmeasure.managers;

import java.util.Arrays;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 29/Feb/2016
 */
public class IdrvManager {

    private static final int IDRV_VERSION_1 = 0x01;
    private static final int IDRV_VERSION_2 = 0x02;
    private static final int IDRV_VERSION_3 = 0x03;

    private static final float[][] BUF_OFF_LINE_VAL = new float[][]{
            {}, {},
            {0, 0.15f, 0.25f, 0.35f, 0.45f, 0.55f, 0.65f, 1.00f, 1.20f, 1.28f},
            {0, 0.20f, 0.80f, 1.20f, 1.28f}
    };

    private static IdrvManager instance;

    public static IdrvManager getInstance() {
        if (instance == null) {
            instance = new IdrvManager();
        }
        return instance;
    }

    private byte[] uid;
    private float gainError;
    private int testSt;
    private int fwSwVersion;
    private int productSt;
    private int tableVersion;
    private int offsetError;
    private int bufferOffsetLineSize;
    private float bufferOffset;
    private int bufferOffsetLine[];

    public void setUid(byte[] uid) {
        this.uid = uid.clone();
    }

    public void setChipInfoFromPage2C(byte[] recv) {
        testSt = recv[12] & 0xFF;
        fwSwVersion = recv[13] & 0xFF;
        productSt = recv[14] & 0xFF;
        tableVersion = recv[15] & 0xFF;
        switch (tableVersion) {
            default:
            case IDRV_VERSION_1:
                break;

            case IDRV_VERSION_2:
                configAdcError(recv);
                configOffsetLine(8);
                break;

            case IDRV_VERSION_3:
                configAdcError(recv);
                configOffsetLine(3);
                break;

        }
    }

    private void configAdcError(byte[] recv) {
        int gain;
        gain = (recv[8] << 8) | (recv[9] & 0xFF);
        offsetError = (recv[10] << 8) | (recv[11] & 0xFF);
        gainError = (float) (gain / 100000.0);
    }

    private void configOffsetLine(int size) {
        bufferOffsetLineSize = size;
        bufferOffsetLine = new int[bufferOffsetLineSize];
    }

    public void setChipInfoFromPage30(byte[] recv) {
        switch (tableVersion) {
            default:
            case IDRV_VERSION_1:
                int gain = (recv[4] << 8) | (recv[5] & 0xFF);
                int buffer = (recv[12] << 8) | (recv[13] & 0xFF);
                offsetError = (recv[6] << 8) | (recv[7] & 0xFF);
                gainError = gain / 100000.0f;
                bufferOffset = buffer / 100000.0f;
                break;

            case IDRV_VERSION_2:
            case IDRV_VERSION_3:
                for (int i = 0; i < bufferOffsetLineSize; ++i) {
                    int index = i << 1;
                    bufferOffsetLine[i] = (recv[index] << 8) | (recv[index + 1] & 0xFF);
                }
                break;
        }
    }

    public float getAdcResultWithBuffer(int rawVolt) {
        switch (tableVersion) {
            case IDRV_VERSION_1:
                return getAdcResult(rawVolt) - bufferOffset;

            case IDRV_VERSION_2:
            case IDRV_VERSION_3:
                int index = bufferOffsetLineSize;
                int low = 0;
                int high = 0x1FFF;
                for (int i = 0; i < bufferOffsetLineSize; ++i) {
                    if (rawVolt < bufferOffsetLine[i]) {
                        index = i;
                        break;
                    }
                }
                if (index > 0) {
                    low = bufferOffsetLine[index - 1];
                }
                if (index < bufferOffsetLineSize) {
                    high = bufferOffsetLine[index];
                }
                return ((rawVolt - low)
                        * (BUF_OFF_LINE_VAL[tableVersion][index + 1] - BUF_OFF_LINE_VAL[tableVersion][index])
                        / (high - low))
                        + BUF_OFF_LINE_VAL[tableVersion][index];
        }
        return 0;
    }

    public float getAdcResult(int rawVolt) {
//        Timber.wtf("%d %f %d", rawVolt, gainError, offsetError);
        return (float) ((1.280f / Math.pow(2f, 13f)) * (1f + gainError) * (rawVolt + offsetError));
    }

    public float getPpmRegressionEquationA(float x, float a, float b) {
        double val = (a / x);
        double ln = Math.log(val) / Math.log(Math.exp(1));
        return (float) Math.exp(ln / b); // ln(a / x) / b
    }

    public float getPpmRegressionEquationB(float x, float a, float b) {
        return (float) Math.exp((a - x) / b); // (a - x) / b
    }

    public boolean isSameTagId(byte[] uid) {
        return Arrays.equals(this.uid, uid);
    }


}
