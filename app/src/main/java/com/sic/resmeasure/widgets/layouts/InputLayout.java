/*
 * Copyright (c) 2015 Silicon Craft Technology Co.,Ltd.
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

package com.sic.resmeasure.widgets.layouts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.google.common.primitives.Ints;
import com.sic.resmeasure.R;
import com.sic.resmeasure.binders.contacts.InputLayoutContact;
import com.sic.resmeasure.binders.models.InputLayoutViewModel;
import com.sic.resmeasure.binders.presenters.InputLayoutPresenter;
import com.sic.resmeasure.databinding.LayoutInputBinding;
import com.sic.resmeasure.widgets.states.BundleSavedState;


/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 7/23/2015
 */
public class InputLayout extends LinearLayout implements InputLayoutContact.View {

    private InputLayoutViewModel model;
    private InputLayoutContact.DynamicAdjust dynamicAdjust;
    private InputLayoutContact.DynamicMax dynamicMax;
    private InputLayoutContact.ItemSelected itemSelected;
    private InputLayoutContact.CheckChanged checkChanged;
    private InputLayoutContact.ValueChanged valueChanged;

    public InputLayout(Context context) {
        super(context);
        initInflateWithAttrs(null, 0, 0);
    }

    public InputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflateWithAttrs(attrs, 0, 0);
    }

    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflateWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflateWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle childrenStates = new Bundle();

        for (int i = 0; i < getChildCount(); ++i) {
            int id = getChildAt(i).getId();
            if (id != 0) {
                SparseArray<Parcelable> childrenState = new SparseArray<>();
                getChildAt(i).saveHierarchyState(childrenState);
                childrenStates.putSparseParcelableArray(String.valueOf(id), childrenState);
            }
        }

        Bundle bundle = new Bundle();
        bundle.putBundle(InputLayout.class.getSimpleName(), childrenStates);
        BundleSavedState savedState = new BundleSavedState(superState);
        savedState.setBundle(bundle);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        Bundle childrenStates = savedState.getBundle().getBundle(InputLayout.class.getSimpleName());
        for (int i = 0; i < getChildCount(); ++i) {
            int id = getChildAt(i).getId();
            if (id != 0) {
                if (childrenStates != null && childrenStates.containsKey(String.valueOf(id))) {
                    SparseArray<Parcelable> childrenState =
                            childrenStates.getSparseParcelableArray(String.valueOf(id));
                    getChildAt(i).restoreHierarchyState(childrenState);
                }
            }
        }
    }

    private void initInflateWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInputBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_input, this, true);

        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.InputLayoutStyleable, defStyleAttr, defStyleRes);

        try {
            model = new InputLayoutViewModel();
            InputLayoutPresenter presenter = new InputLayoutPresenter(this);

            String text = array.getString(R.styleable.InputLayoutStyleable_text);
            String unit = array.getString(R.styleable.InputLayoutStyleable_unit);

            boolean editor = array.getBoolean(R.styleable.InputLayoutStyleable_editor, model.editor.get());
            boolean spinner = array.getBoolean(R.styleable.InputLayoutStyleable_spinner, model.spinner.get());
            boolean box = array.getBoolean(R.styleable.InputLayoutStyleable_box, model.box.get());

            boolean check = array.getBoolean(R.styleable.InputLayoutStyleable_check, model.check.get());

            boolean integer = array.getBoolean(R.styleable.InputLayoutStyleable_integer, model.integer.get());
            float max = array.getFloat(R.styleable.InputLayoutStyleable_edt_max, model.max.get());
            float min = array.getFloat(R.styleable.InputLayoutStyleable_edt_min, model.min.get());
            float adjust = array.getFloat(R.styleable.InputLayoutStyleable_edt_adjust, model.adjust.get());
            int length = array.getInteger(R.styleable.InputLayoutStyleable_edt_length, model.length.get());

            int spnResId = array.getResourceId(R.styleable.InputLayoutStyleable_spn_list, -1);
            int select = array.getInteger(R.styleable.InputLayoutStyleable_select, model.select.get());

            float value = array.getFloat(R.styleable.InputLayoutStyleable_value, model.value.get());

            model.text.set(text);
            model.unit.set(unit);
            model.editor.set(editor);
            model.spinner.set(spinner);
            model.box.set(box);
            model.check.set(check);
            model.integer.set(integer);
            model.max.set(max);
            model.min.set(min);
            model.adjust.set(adjust);

            model.value.set(adjust(value));

            model.length.set(length);


            if (spinner && spnResId != -1) {
                model.adapter.set(new ArrayAdapter<>(getContext(), R.layout.item_spinner, Ints.asList(getResources().getIntArray(spnResId))));
            }

            model.select.set(select);

            model.value.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (sender == model.value && valueChanged != null) {
                        valueChanged.onValueChange();
                    }
                }
            });

            model.check.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (sender == model.check && checkChanged != null) {
                        checkChanged.onCheckChange();
                    }
                }
            });

            model.select.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (sender == model.select && itemSelected != null) {
                        itemSelected.onItemSelect();
                        valueChanged(model);
                    }
                }
            });

            binding.setModel(model);
            binding.setPresenter(presenter);
        } finally {
            array.recycle();
        }

    }

    private float adjust(float value) {
        float adjust = model.adjust.get();
        float min = model.min.get();
        float max = model.max.get();

        value = Math.round(value / adjust) * adjust;
        value = Math.max(min, value);
        value = Math.min(max, value);

        return value;
    }

    public float getValue() {
        return model.value.get();
    }

    public void setValue(float value) {
        model.value.set(value);
    }

    public int getSelect() {
        return model.select.get();
    }

    public void setSelect(int selected) {
        model.select.set(selected);
    }

    public boolean isCheck() {
        return model.check.get();
    }

    public void setCheck(boolean checked) {
        this.model.check.set(checked);
    }

    public float getAdjust() {
        return model.adjust.get();
    }

    public void setAdjust(float adjust) {
        model.adjust.set(adjust);
    }

    @Override
    public void valueChanged(InputLayoutViewModel model) {
        if (model.editor.get()) {
            if (dynamicAdjust != null) {
                model.adjust.set(dynamicAdjust.getAdjust());
            }

            if (dynamicMax != null) {
                model.max.set(dynamicMax.getMax());
            }

            model.value.set(adjust(model.value.get()));
            model.value.notifyChange();
        } else if (model.spinner.get() && model.select.get() >= 0) {
            Integer val = model.adapter.get().getItem(model.select.get());
            if (val != null) {
                model.value.set(val);
                model.value.notifyChange();
            }
        }
    }

    public void setDynamicAdjust(InputLayoutContact.DynamicAdjust dynamicAdjust) {
        this.dynamicAdjust = dynamicAdjust;
    }

    public void setDynamicMax(InputLayoutContact.DynamicMax dynamicMax) {
        this.dynamicMax = dynamicMax;
    }

    public void setCheckChanged(InputLayoutContact.CheckChanged checkChanged) {
        this.checkChanged = checkChanged;
    }

    public void setItemSelected(InputLayoutContact.ItemSelected itemSelected) {
        this.itemSelected = itemSelected;
    }

    public void setValueChanged(InputLayoutContact.ValueChanged valueChanged) {
        this.valueChanged = valueChanged;
    }

    public void setText(String text) {
        this.model.text.set(text);
    }
}
