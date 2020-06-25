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

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 12/Feb/2015
 */
public class Flag {
    // Response Flag (RF - UART, RF - RegPage)
    public static final byte B_ACK = 0x1A;
    public static final byte B_NAK = (byte) 0x80;
    // Data Bit Clear Flag
    public static final byte C_FLUSH_VDD_RDY_L = 0x01;
    public static final byte C_FLUSH_VDD_RDY_H = 0x02;
    // Response NACK
    public static final byte B_NAK_VDD_RDY_L = 0x01;
    public static final byte B_NAK_VDD_RDY_H = 0x02;

    public static String getName(byte flag) {
        switch (flag) {
            case B_ACK:
                return "B_ACK";

            default:
                String rtFlag = "";
                byte cFlag = B_NAK;

                if ((flag & cFlag) == cFlag) {
                    cFlag = B_NAK_VDD_RDY_L;
                    if ((flag & cFlag) == cFlag) {
                        if (!rtFlag.isEmpty()) {
                            rtFlag += "/";
                        }
                        rtFlag += "VDD_RDY_L";
                    }

                    cFlag = B_NAK_VDD_RDY_H;
                    if ((flag & cFlag) == cFlag) {
                        if (!rtFlag.isEmpty()) {
                            rtFlag += "/";
                        }
                        rtFlag += "VDD_RDY_H";
                    }

                    return rtFlag;
                } else {
                    return "NULL";
                }
        }
    }
}
