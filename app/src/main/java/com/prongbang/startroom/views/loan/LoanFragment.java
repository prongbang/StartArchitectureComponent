package com.prongbang.startroom.views.loan;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.Loan;
import com.prongbang.startroom.model.LoanWithUserAndBook;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.views.BaseFragment;
import com.prongbang.startroom.views.user.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends BaseFragment {

    private String TAG = LoanFragment.class.getSimpleName();

    private LoanViewModel mLoanViewModel;
    private LoanRvAdapter mAdapter;
    private UserSpinerAdapter mUserSpinerAdapter;

    public LoanFragment() {
        // Required empty public constructor
    }

    public static LoanFragment newInstance() {
        return new LoanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loan, container, false);

        mLoanViewModel = ViewModelProviders.of(this).get(LoanViewModel.class);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        mAdapter = new LoanRvAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnMoreClickListener(new LoanRvAdapter.OnMoreClickListener() {
            @Override
            public void onClick(int itemId, final LoanWithUserAndBook data) {
                switch (itemId) {
                    case R.id.edit:
                        Log.i(TAG, "onClick: Edit");
                        AddOrEditLoanFragment aoeuFragment = new AddOrEditLoanFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstantUtil.CRUD_KEY, ConstantUtil.UPDATE_KEY);
                        Loan loan = new Loan();
                        loan.setId(data.getId());
                        loan.setStartTime(data.getStartTime());
                        loan.setEndTime(data.getEndTime());
                        loan.setBookId(data.getBookId());
                        loan.setUserId(data.getUserId());
                        bundle.putParcelable(ConstantUtil.LOAN_KEY, loan);
                        aoeuFragment.setArguments(bundle);
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), aoeuFragment, R.id.container);
                        break;
                    case R.id.delete:
                        Log.i(TAG, "onClick: Delete");
                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.label_confirm))
                                .setMessage(getString(R.string.label_confirm_delete, data.getBookTitle()))
                                .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            int id = mLoanViewModel.deleteById(data.getId());
                                            if (id > 0) {
                                                Log.i(TAG, "Delete: success");
                                            } else {
                                                Log.i(TAG, "Delete: fail");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                        break;
                }
            }
        });

        mLoanViewModel.findAllWithUserAndBook().observe(this, new Observer<List<LoanWithUserAndBook>>() {
            @Override
            public void onChanged(@Nullable List<LoanWithUserAndBook> loanWithUserAndBooks) {
                mAdapter.setData(loanWithUserAndBooks);
            }
        });

        return v;
    }

}
