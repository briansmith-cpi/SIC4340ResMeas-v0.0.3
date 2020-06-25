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

import com.sic.resmeasure.widgets.layouts.LogLayout;


/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 11/Oct/2016
 */
public class LogLayoutBindingAdapter {
    @BindingAdapter("title")
    public static void setTitle(LogLayout layout, String title) {
        if (!title.equals(layout.getTitle())) {
            layout.setTitle(title);
        }
    }

    @BindingAdapter("text")
    public static void setText(LogLayout layout, String text) {
        if (!text.equals(layout.getText())) {
            layout.setText(text);
        }
    }

    @BindingAdapter("unit")
    public static void setUnit(LogLayout layout, String unit) {
        if (!unit.equals(layout.getUnit())) {
            layout.setUnit(unit);
        }
    }

}
