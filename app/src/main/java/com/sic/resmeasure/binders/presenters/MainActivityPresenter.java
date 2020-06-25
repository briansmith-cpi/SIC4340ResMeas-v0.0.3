package com.sic.resmeasure.binders.presenters;


import com.sic.resmeasure.binders.contacts.MainActivityContact;
import com.sic.resmeasure.binders.models.MainActivityViewModel;

public class MainActivityPresenter implements MainActivityContact.Presenter {
    private MainActivityContact.View mView;

    public MainActivityPresenter(MainActivityContact.View view) {
        this.mView = view;
    }

    @Override
    public void onPageChanged(MainActivityViewModel model) {
        mView.pageChanged(model);
    }

    @Override
    public void onPageDragging(MainActivityViewModel model) {
        mView.pageDragging(model);
    }
}