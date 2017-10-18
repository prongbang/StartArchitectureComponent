package com.prongbang.startroom.views;

import android.arch.lifecycle.LifecycleActivity;

import com.prongbang.startroom.data.db.AppDatabase;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class BaseActivity extends LifecycleActivity {

    @Override
    public void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
