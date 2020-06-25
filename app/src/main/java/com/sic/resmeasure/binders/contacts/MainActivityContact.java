package com.sic.resmeasure.binders.contacts;


import com.sic.resmeasure.binders.models.MainActivityViewModel;

public interface MainActivityContact {
    interface Presenter {
         void onPageChanged(MainActivityViewModel model);
         void onPageDragging(MainActivityViewModel model);
    }

    interface View {
        void pageChanged(MainActivityViewModel model);
        void pageDragging(MainActivityViewModel model);
    }
}