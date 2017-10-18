package com.prongbang.startroom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.views.BaseActivity;
import com.prongbang.startroom.views.main.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtil.addFragmentToActivity(getSupportFragmentManager(), MainFragment.newInstance(), R.id.container);
    }

    /**
     * Pop Back Stack
     */
    @Override public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

}
