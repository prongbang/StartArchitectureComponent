package com.prongbang.startroom.views;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class BaseFragment extends LifecycleFragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void alert(Context context, String title, String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).create().show();
    }

}
