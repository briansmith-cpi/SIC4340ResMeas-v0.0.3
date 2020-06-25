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
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.sic.resmeasure.utils.Pager;


/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 30/Sep/2016
 */
public class ViewPagerBindingAdapter {

    @BindingAdapter("adapter")
    public static void setAdapter(ViewPager viewPager, FragmentPagerAdapter adapter) {
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, viewPager.getContext().getResources().getDisplayMetrics());

        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(pageMargin);
    }

    @BindingAdapter("position")
    public static void setPositionPager(ViewPager viewPager, Pager pager) {
        if (pager.ordinal() != viewPager.getCurrentItem()) {
            viewPager.setCurrentItem(pager.ordinal(), true);
        }
    }

    @InverseBindingAdapter(attribute = "position")
    public static Pager getPosition(ViewPager viewPager) {
        return Pager.valueOf(viewPager.getCurrentItem());
    }

    @BindingAdapter(value = {"onPageSelected", "onPageDragging", "positionAttrChanged"}, requireAll = false)
    public static void setOnPageChangeListener(ViewPager viewPager, OnPageSelected selected, OnPageDragging dragging, final InverseBindingListener attrChange) {
        if (attrChange == null && selected == null) {
            viewPager.addOnPageChangeListener(null);
        } else {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (attrChange != null) {
                        attrChange.onChange();
                    }
                    if (selected != null) {
                        selected.onPageSelected(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        dragging.onPageDragging();
                    }
                }
            });
        }
    }

    public interface OnPageSelected {
        void onPageSelected(int position);
    }

    public interface OnPageDragging {
        void onPageDragging();
    }
}
