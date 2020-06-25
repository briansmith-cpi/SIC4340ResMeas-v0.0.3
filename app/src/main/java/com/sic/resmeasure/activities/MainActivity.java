package com.sic.resmeasure.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;
import com.sic.module.nfc.sic4340.Sic4340;
import com.sic.resmeasure.R;
import com.sic.resmeasure.adapters.ViewPagerAdapter;
import com.sic.resmeasure.binders.contacts.MainActivityContact;
import com.sic.resmeasure.binders.models.MainActivityViewModel;
import com.sic.resmeasure.binders.presenters.MainActivityPresenter;
import com.sic.resmeasure.daos.LogItemDao;
import com.sic.resmeasure.daos.RegisterItemDao;
import com.sic.resmeasure.databinding.ActivityMainBinding;
import com.sic.resmeasure.fragments.ChipSetupFragment;
import com.sic.resmeasure.fragments.StatusFragment;
import com.sic.resmeasure.managers.tasks.IdrvRxTask;
import com.sic.resmeasure.utils.Pager;
import com.sic.resmeasure.utils.Toaster;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainActivityContact.View {

    public static final String ACTION_CALL_ON_CLICK = "action_call_on_click";
    private static final IntentFilter ACTION_FILTER = new IntentFilter();

    private ActivityMainBinding binding;
    private Sic4340 nfc;
    private boolean loading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        ACTION_FILTER.addAction(ACTION_CALL_ON_CLICK);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initInstances();

        attachFragments(savedInstanceState);

        nfc = Sic4340.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        nfc.onResume(this);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            onNewIntent(getIntent());
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, ACTION_FILTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        nfc.onPause(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        loading = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        nfc.onNewIntent(this, intent);
        if (nfc.isFoundTag()) {
            nfc.getRegister().clearFlags();
            IdrvRxTask.getInstance().initialTask(getApplicationContext()).subscribe(getStatusFragment().getSubscribeInitial());
        } else {
            Toaster.showToast(getApplicationContext(), "This tag is not support");
        }
        loading = false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you want to exit?");
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {
            super.onBackPressed();
            dialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void initInstances() {
        MainActivityViewModel model = new MainActivityViewModel();
        MainActivityPresenter presenter = new MainActivityPresenter(this);

        model.adapter.set(ViewPagerAdapter.newInstance(getSupportFragmentManager()));
        binding.layoutTab.setupWithViewPager(binding.viewPager);

        binding.setModel(model);
        binding.setPresenter(presenter);
    }

    private void attachFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            StatusFragment fragment = StatusFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_container,
                            fragment,
                            fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private StatusFragment getStatusFragment() {
        return (StatusFragment) getSupportFragmentManager()
                .findFragmentByTag(StatusFragment.class.getSimpleName());
    }

    private ChipSetupFragment getChipSetupFragment() {
        return (ChipSetupFragment) binding.getModel().adapter.get().getItem(Pager.CHIP_SETUP);
    }

    @Override
    public void pageChanged(MainActivityViewModel model) {
        RegisterItemDao register = new RegisterItemDao();
        View view = getStatusFragment().getView();

        if (view != null) {
            getStatusFragment().setPosition(model.position.get().ordinal());
            view.requestFocus();

            switch (model.position.get()) {
                case CHIP_SETUP:
                    break;
            }
        }
    }

    @Override
    public void pageDragging(MainActivityViewModel model) {
        View view = getStatusFragment().getView();

        if (view != null) {
            view.requestFocus();
            hideKeyboard();

            switch (model.position.get()) {
                case CHIP_SETUP:
                    break;
            }
        }
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            RegisterItemDao register = new RegisterItemDao();
            LogItemDao log = getChipSetupFragment().getLogData();

            getChipSetupFragment().putRegisterData(register);

            loading = true;
            IdrvRxTask.getInstance().getAdcTask(getApplicationContext(), register)
                    .share()
                    .publish(resultRxObservable -> {
                resultRxObservable.subscribe(getStatusFragment().getSubscribeGetAdc());
                resultRxObservable.subscribe(getChipSetupFragment().getSubscribeGetAdc(log));
                return resultRxObservable;
            }).subscribe(resultRx -> loading = false);

        }
    };
}
