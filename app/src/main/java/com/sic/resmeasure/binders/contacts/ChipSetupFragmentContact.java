package com.sic.resmeasure.binders.contacts;


import com.sic.resmeasure.binders.models.ChipSetupFragmentViewModel;

public interface ChipSetupFragmentContact {
    interface Presenter {
        void onSamplingFrequencyChange(ChipSetupFragmentViewModel model);
        void onClearLogClicked(ChipSetupFragmentViewModel model);
    }

    interface View {
        void samplingFrequencyChange(ChipSetupFragmentViewModel model);
        void clearLogClicked(ChipSetupFragmentViewModel model);
    }
}