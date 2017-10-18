package com.prongbang.startroom.views.main;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.Tabs;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.views.BaseFragment;
import com.prongbang.startroom.views.book.AddOrEditBookFragment;
import com.prongbang.startroom.views.loan.AddOrEditLoanFragment;
import com.prongbang.startroom.views.user.AddOrEditUserFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    private String TAG = MainFragment.class.getSimpleName();

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;
    private int pageSelected = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mViewPager = v.findViewById(R.id.viewPager);
        mTabLayout = v.findViewById(R.id.tabLayout);
        mFab = v.findViewById(R.id.fab);

        createTabLayoutViewPager();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageSelected) {
                    case 0:
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), AddOrEditLoanFragment.newInstance(), R.id.container);
                        break;
                    case 1:
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), AddOrEditBookFragment.newInstance(), R.id.container);
                        break;
                    case 2:
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), AddOrEditUserFragment.newInstance(), R.id.container);
                        break;
                }
            }
        });

        return v;
    }

    /**
     * Create Tab Layout and View Pager
     */
    private void createTabLayoutViewPager() {
        MainFragmentPagerAdapter mfpAdapter = new MainFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mfpAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                Log.i(TAG, "onPageSelected: " + position);
                pageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
