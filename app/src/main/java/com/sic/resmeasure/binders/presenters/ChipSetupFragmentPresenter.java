package com.sic.resmeasure.binders.presenters;

import com.sic.resmeasure.binders.contacts.ChipSetupFragmentContact;
import com.sic.resmeasure.binders.models.ChipSetupFragmentViewModel;

public class ChipSetupFragmentPresenter implements ChipSetupFragmentContact.Presenter {
    private ChipSetupFragmentContact.View mView;

    public ChipSetupFragmentPresenter(ChipSetupFragmentContact.View view) {
        this.mView = view;
    }

    @Override
    public void onSamplingFrequencyChange(ChipSetupFragmentViewModel model) {
        mView.samplingFrequencyChange(model);
    }

    @Override
    public void onClearLogClicked(ChipSetupFragmentViewModel model) {
        mView.clearLogClicked(model);
    }
}