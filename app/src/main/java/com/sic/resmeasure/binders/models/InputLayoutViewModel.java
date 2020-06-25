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
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 29/Sep/2016
 */
public class InputLayoutViewModel {

    public final ObservableBoolean box = new ObservableBoolean();
    public final ObservableBoolean editor = new ObservableBoolean();
    public final ObservableBoolean spinner = new ObservableBoolean();

    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<String> unit = new ObservableField<>();

    // box
    public final ObservableBoolean check = new ObservableBoolean();

    // editor
    public final ObservableFloat value = new ObservableFloat();
    public final ObservableFloat adjust = new ObservableFloat();
    public final ObservableFloat max = new ObservableFloat();
    public final ObservableFloat min = new ObservableFloat();
    public final ObservableInt length = new ObservableInt();
    public final ObservableBoolean integer = new ObservableBoolean();

    // spinner
    public final ObservableField<ArrayAdapter<Integer>> adapter = new ObservableField<>();
    public final ObservableInt select = new ObservableInt();

    public InputLayoutViewModel() {
        box.set(false);
        editor.set(false);
        spinner.set(false);

        check.set(true);

        value.set(0);
        adjust.set(1);
        max.set(100);
        min.set(1);
        length.set(5);
        integer.set(false);

        adapter.set(null);
        select.set(-1);
    }

    public int getEditorVisibility() {
        return editor.get() ? View.VISIBLE : spinner.get() ? View.GONE : View.INVISIBLE;
    }
}
