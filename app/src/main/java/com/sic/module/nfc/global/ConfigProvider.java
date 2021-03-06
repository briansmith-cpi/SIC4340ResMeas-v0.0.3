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
 * @version 1.0.2
 * @since 25/Nov/2015
 */
public abstract class ConfigProvider extends Provider {

    private static final String TAG = ConfigProvider.class.getName();

    private final SicChip mChip;

    protected ConfigProvider(SicChip chip) {
        super(chip.getRegister());
        mChip = chip;
    }

}
