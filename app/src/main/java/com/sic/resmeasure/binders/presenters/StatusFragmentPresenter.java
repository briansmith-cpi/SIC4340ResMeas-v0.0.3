package com.sic.resmeasure.binders.presenters;


import com.sic.resmeasure.binders.contacts.StatusFragmentContact;
import com.sic.resmeasure.binders.models.StatusFragmentViewModel;

public class StatusFragmentPresenter implements StatusFragmentContact.Presenter {
    private StatusFragmentContact.View mView;

    public StatusFragmentPresenter(StatusFragmentContact.View view) {
        this.mView = view;
    }

    @Override
    public void onSendClicked(StatusFragmentViewModel model) {
        mView.sendClicked(model);
    }
}