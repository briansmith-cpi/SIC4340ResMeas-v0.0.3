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

package com.sic.resmeasure.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.sic.resmeasure.fragments.ChipSetupFragment;
import com.sic.resmeasure.utils.Pager;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 7/27/2015
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    private ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        registeredFragments.put(Pager.CHIP_SETUP.ordinal(), ChipSetupFragment.newInstance());
    }

    public static ViewPagerAdapter newInstance(FragmentManager fragmentManager) {
        return new ViewPagerAdapter(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Pager.valueOf(position).toString();
    }

    @Override
    public int getCount() {
        return Pager.size();
    }

    @Override
    public Fragment getItem(int position) {
        return registeredFragments.get(position);
    }

    public Fragment getItem(Pager pager) {
        return registeredFragments.get(pager.ordinal());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}