package com.sic.resmeasure.binders.contacts;


import com.sic.resmeasure.binders.models.InputLayoutViewModel;

public interface InputLayoutContact {
    interface Presenter {
        void onValueChanged(InputLayoutViewModel model);
    }

    interface View {
        void valueChanged(InputLayoutViewModel model);

    }

    interface DynamicAdjust {
        float getAdjust();
    }

    interface DynamicMax {
        float getMax();
    }

    interface CheckChanged {
        void onCheckChange();
    }

    interface ItemSelected {
        void onItemSelect();
    }

    interface ValueChanged {
        void onValueChange();
    }
}