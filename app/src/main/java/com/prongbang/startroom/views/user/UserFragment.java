package com.prongbang.startroom.views.user;

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

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.repository.UserRepository;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.views.BaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {

    private String TAG = UserFragment.class.getSimpleName();

    private UserRvAdapter mAdapter;
    private UserViewModel mUserViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        mAdapter = new UserRvAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnMoreClickListener(new UserRvAdapter.OnMoreClickListener() {
            @Override
            public void onClick(int itemId, final User data) {
                switch (itemId) {
                    case R.id.edit:
                        Log.i(TAG, "onClick: Edit -> " + data);
                        AddOrEditUserFragment aoeuFragment = new AddOrEditUserFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstantUtil.CRUD_KEY, ConstantUtil.UPDATE_KEY);
                        bundle.putParcelable(ConstantUtil.USER_KEY, data);
                        aoeuFragment.setArguments(bundle);
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), aoeuFragment, R.id.container);
                        break;
                    case R.id.delete:
                        Log.i(TAG, "onClick: Delete");
                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.label_confirm))
                                .setMessage(getString(R.string.label_confirm_delete, data.getName()))
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
                                            int id = mUserViewModel.delete(data);
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

        mUserViewModel.findAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                mAdapter.setData(users);
            }
        });

        return v;
    }

}
