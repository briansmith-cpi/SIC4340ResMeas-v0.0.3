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
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;

import com.sic.resmeasure.binders.contacts.InputLayoutContact;
import com.sic.resmeasure.widgets.layouts.InputLayout;


/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 10/Oct/2016
 */
public class InputLayoutBindingAdapter {
    @InverseBindingAdapter(attribute = "value")
    public static int getValueInt(InputLayout layout) {
        return Math.round((layout.getValue()));
    }

    @InverseBindingAdapter(attribute = "value")
    public static float getValueFloat(InputLayout layout) {
        return layout.getValue();
    }

    @BindingAdapter("value")
    public static void setValue(InputLayout layout, float value) {
        if (value != layout.getValue()) {
            layout.setValue(value);
        }
    }

    @BindingAdapter("value")
    public static void setValue(InputLayout layout, int value) {
        if (value != layout.getValue()) {
            layout.setValue(value);
        }
    }

    @InverseBindingAdapter(attribute = "check")
    public static boolean isCheck(InputLayout layout) {
        return layout.isCheck();
    }

    @BindingAdapter("check")
    public static void setCheck(InputLayout layout, boolean check) {
        if (check != layout.isCheck()) {
            layout.setCheck(check);
        }
    }

    @InverseBindingAdapter(attribute = "select")
    public static int getSelect(InputLayout layout) {
        return layout.getSelect();
    }

    @BindingAdapter("select")
    public static void setSelect(InputLayout layout, int select) {
        if (select != layout.getSelect()) {
            layout.setSelect(select);
        }
    }

    @BindingAdapter("text")
    public static void setText(InputLayout layout, String text) {
        layout.setText(text);

    }

    @BindingAdapter("edt_adjust")
    public static void setAdjust(InputLayout layout, float adjust) {
        if (adjust != layout.getAdjust()) {
            layout.setAdjust(adjust);
        }
    }

    @BindingAdapter(value = {"edt_max", "edt_adjust", "onValueChange", "valueAttrChanged"}, requireAll = false)
    public static void setOnValueChangeListener(InputLayout layout,
                                                final InputLayoutContact.DynamicMax dynamicMax,
                                                final InputLayoutContact.DynamicAdjust dynamicAdjust,
                                                final InputLayoutContact.ValueChanged valueChanged,
                                                final InverseBindingListener attrChange) {
        if (dynamicAdjust == null) {
            layout.setDynamicAdjust(null);
        } else {
            layout.setDynamicAdjust(() -> {
                if (attrChange != null) {
                    attrChange.onChange();
                }

                return dynamicAdjust.getAdjust();
            });
        }

        if (dynamicMax == null) {
            layout.setDynamicMax(null);
        } else {
            layout.setDynamicMax(() -> {
                if (attrChange != null) {
                    attrChange.onChange();
                }

                return dynamicMax.getMax();
            });
        }
        if (attrChange == null) {
            layout.setValueChanged(null);
        } else {
            if (valueChanged == null) {
                layout.setValueChanged(attrChange::onChange);
            } else {
                layout.setValueChanged(() -> {
                    attrChange.onChange();
                    valueChanged.onValueChange();
                });
            }
        }
    }

    @BindingAdapter("selectAttrChanged")
    public static void setItemSelectedListener(InputLayout layout, final InverseBindingListener attrChange) {
        if (attrChange == null) {
            layout.setItemSelected(null);
        } else {
            layout.setItemSelected(attrChange::onChange);
        }
    }

    @BindingAdapter("checkAttrChanged")
    public static void setCheckChangedListener(InputLayout layout, final InverseBindingListener attrChange) {
        if (attrChange == null) {
            layout.setCheckChanged(null);
        } else {
            layout.setCheckChanged(attrChange::onChange);
        }
    }
}
