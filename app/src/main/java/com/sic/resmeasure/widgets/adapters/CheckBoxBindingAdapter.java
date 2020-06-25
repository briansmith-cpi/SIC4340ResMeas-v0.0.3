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
package com.sic.resmeasure.widgets.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.widget.CheckBox;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 30/Sep/2016
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = CheckBox.class, attribute = "android:checked"),
})
public class CheckBoxBindingAdapter {

    @BindingAdapter("android:background")
    public static void setBackground(CheckBox checkBox, boolean bool) {
        if (!bool) {
            checkBox.setBackgroundResource(android.R.color.transparent);
            checkBox.setCompoundDrawables(null, null, null, null);
        }
    }
}
