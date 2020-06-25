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

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import com.sic.resmeasure.adapters.LogListAdapter;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 29/Sep/2016
 */
public class ChipSetupFragmentViewModel {
    public final ObservableInt biasCurrent1 = new ObservableInt();
    public final ObservableInt channelPosition = new ObservableInt();

    public final ObservableField<LogListAdapter> adapter = new ObservableField<>();
    public final ObservableBoolean buffer = new ObservableBoolean();
    public final ObservableInt range = new ObservableInt();
    public final ObservableInt bias = new ObservableInt();
    public final ObservableFloat value = new ObservableFloat();

    public ChipSetupFragmentViewModel() {
        biasCurrent1.set(10);
        channelPosition.set(0);

        adapter.set(new LogListAdapter());
        bias.set(200);
        range.set(1200);
        value.set(-100);
        buffer.set(false);
    }

    public float getAdjustBias1() {
        return biasCurrent1.get() < 64 ? 1 : 8;
    }
}
