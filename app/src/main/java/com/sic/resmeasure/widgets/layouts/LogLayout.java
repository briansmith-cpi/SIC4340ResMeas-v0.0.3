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
package com.sic.resmeasure.widgets.layouts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sic.resmeasure.R;
import com.sic.resmeasure.binders.models.LogLayoutViewModel;
import com.sic.resmeasure.databinding.LayoutLogBinding;
import com.sic.resmeasure.widgets.states.BundleSavedState;


/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 06/Oct/2016
 */
public class LogLayout extends LinearLayout {

    private LogLayoutViewModel model;

    public LogLayout(Context context) {
        super(context);
        initInflateWithAttrs(null, 0, 0);
    }

    public LogLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflateWithAttrs(attrs, 0, 0);
    }

    public LogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflateWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LogLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        LayoutLogBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_log, this, true);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.LogLayoutStyleable, defStyleAttr, defStyleRes);

        try {
            model = new LogLayoutViewModel();

            String title = a.getString(R.styleable.LogLayoutStyleable_title);
            String text = a.getString(R.styleable.LogLayoutStyleable_text);
            String unit = a.getString(R.styleable.LogLayoutStyleable_unit);

            if (title != null && !title.isEmpty()) {
                model.title.set(title);
            }
            if (text != null && !text.isEmpty()) {
                model.text.set(text);
            }
            if (unit != null && !unit.isEmpty()) {
                model.unit.set(unit);
            }

            binding.setModel(model);
        } finally {
            a.recycle();
        }
    }

    public String getTitle() {
        return model.title.get();
    }

    public void setTitle(String title) {
        model.title.set(title);
    }

    public String getText() {
        return model.text.get();
    }

    public void setText(String text) {
        this.model.text.set(text);
    }

    public String getUnit() {
        return model.unit.get();
    }

    public void setUnit(String unit) {
        model.unit.set(unit);
    }
}

