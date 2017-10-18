package com.prongbang.startroom.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.prongbang.startroom.data.db.AppDatabase;

/**
 * Created by mdev on 10/16/2017 AD.
 */

public class BaseViewModel extends AndroidViewModel {

    protected AppDatabase mDb;

    public BaseViewModel(Application application) {
        super(application);
        mDb = AppDatabase.getInDatabase(this.getApplication());
    }

}
