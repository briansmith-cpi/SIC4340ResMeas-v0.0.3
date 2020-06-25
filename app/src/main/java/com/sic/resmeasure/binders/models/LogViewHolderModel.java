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

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 29/Sep/2016
 */
public class LogViewHolderModel {
    public final ObservableField<String> concentration = new ObservableField<>();
    public final ObservableField<String> vout = new ObservableField<>();
    public final ObservableField<String> adc = new ObservableField<>();
    public final ObservableField<String> bias = new ObservableField<>();
    public final ObservableField<String> ohm = new ObservableField<>();
    public final ObservableField<String> lpf = new ObservableField<>();
    public final ObservableField<String> buffer = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();

    public LogViewHolderModel() {
        concentration.set("0");
    }
}
