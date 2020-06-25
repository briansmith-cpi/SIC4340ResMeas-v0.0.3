package com.sic.resmeasure;

import android.app.Application;

import com.sic.module.nfc.sic4340.Sic4340;

import timber.log.Timber;

/**
 * @author ztrix
 * @version 1.0.0
 * @since 31-Jan-16
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Sic4340.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Sic4340.getInstance().terminate();
    }
}
