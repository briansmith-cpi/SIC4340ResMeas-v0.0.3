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
package com.sic.resmeasure.binders.models;

import android.databinding.ObservableField;

import com.sic.resmeasure.adapters.ViewPagerAdapter;
import com.sic.resmeasure.utils.Pager;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 29/Sep/2016
 */
public class MainActivityViewModel {
    public final ObservableField<ViewPagerAdapter> adapter = new ObservableField<>();
    public final ObservableField<Pager> position = new ObservableField<>();

    public MainActivityViewModel() {
        position.set(Pager.CHIP_SETUP);
    }
}
