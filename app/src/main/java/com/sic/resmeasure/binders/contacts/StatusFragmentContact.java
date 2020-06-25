package com.sic.resmeasure.binders.contacts;

import com.sic.resmeasure.binders.models.StatusFragmentViewModel;

public interface StatusFragmentContact {
    interface Presenter {
        void onSendClicked(StatusFragmentViewModel model);
    }

    interface View {
        void sendClicked(StatusFragmentViewModel model);
    }
}