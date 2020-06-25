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

package com.sic.module.nfc.global;

import com.sic.module.nfc.SicChip;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.1.0
 * @since 25/Nov/2015
 */
public abstract class AdcProvider extends Provider {

    private static final String TAG = AdcProvider.class.getName();
    protected static byte ADDRESS_ADC_RESULT_2;
    protected static byte ADDRESS_ADC_RESULT_1;
    protected static byte ADDRESS_ADC_RESULT_0;

    private final SicChip mChip;

    protected AdcProvider(SicChip chip) {
        super(chip.getRegister());
        mChip = chip;
    }

    /**
     * Gets the Convert AdcProvider protocol.
     *
     * @return byte array, ConvADC command
     */
    public static byte[] getPackage_ConvAdc() {
        return new byte[]{
                (byte) 0xB8,
                (byte) 0x00
        };
    }

    /**
     * Returns the data conversion of SIC's chip.
     *
     * @return byte array, response flag and AdcProvider's data from NFC tag.
     */
    public byte[] convAdc() {
        return mChip.autoTransceive(getPackage_ConvAdc());
    }

}
