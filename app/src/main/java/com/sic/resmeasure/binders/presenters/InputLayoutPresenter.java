package com.sic.resmeasure.binders.presenters;

import com.sic.resmeasure.binders.contacts.InputLayoutContact;
import com.sic.resmeasure.binders.models.InputLayoutViewModel;

public class InputLayoutPresenter implements InputLayoutContact.Presenter {
    private InputLayoutContact.View mView;

    public InputLayoutPresenter(InputLayoutContact.View view) {
        this.mView = view;
    }

    @Override
    public void onValueChanged(InputLayoutViewModel model) {
        mView.valueChanged(model);
    }
}