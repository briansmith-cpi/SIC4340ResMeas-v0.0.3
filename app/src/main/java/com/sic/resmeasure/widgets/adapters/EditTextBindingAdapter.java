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

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Build;
import android.text.InputType;
import android.widget.EditText;

import java.util.Locale;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 30/Sep/2016
 */
public class EditTextBindingAdapter {

    @BindingAdapter("android:textColor")
    public static void setValueColor(EditText editText, int resId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editText.setTextColor(editText.getContext().getColor(resId));
            } else {
                editText.setTextColor(editText.getContext().getResources().getColor(resId));
            }
        } catch (Resources.NotFoundException ignored) {
        }
    }

    @BindingAdapter("android:inputType")
    public static void setInputType(EditText editText, boolean bool) {
        if (bool) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }
    }

    @BindingAdapter("android:text")
    public static void setValueFloat(EditText editText, float val) {
        String str = editText.getText().toString();
        try {
            if (Float.valueOf(str) == val) {
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        if (Math.abs(val) < 0.000001) {
            editText.setText("");
        } else if ((editText.getInputType() & InputType.TYPE_NUMBER_FLAG_DECIMAL) == 0x00) {
            editText.setText(String.valueOf((int) val));
        } else {
            editText.setText(String.format(Locale.US, "%.4f", val));
        }
    }

    @BindingAdapter("android:text")
    public static void setValueByte(EditText editText, byte val) {
        String str = editText.getText().toString();
        try {
            if (Integer.valueOf(str, 16) == (((int) val) & 0xFF)) {
                return;
            }
        } catch (NumberFormatException ignored) {
        }
        editText.setText(String.format("%02X", ((int) val) & 0xFF));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getValueFloat(EditText editText) {
        String str = editText.getText().toString();
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static byte getValueByte(EditText editText) {
        String str = editText.getText().toString();
        try {
            int value = Integer.valueOf(str, 16);
            return (byte) (value & 0xFF);
        } catch (NumberFormatException ignored) {
            return 0x00;
        }
    }

    @BindingAdapter(value = {"onTextChanged"})
    public static void setOnFocusChangedListener(EditText editText, final InverseBindingListener attrChange) {
        if (attrChange == null) {
            editText.setOnFocusChangeListener(null);
        } else {
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    attrChange.onChange();
                }
            });
        }
    }
}
