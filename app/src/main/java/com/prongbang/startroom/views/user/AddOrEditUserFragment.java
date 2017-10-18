package com.prongbang.startroom.views.user;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.utils.KeyboardUtil;
import com.prongbang.startroom.views.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrEditUserFragment extends BaseFragment {

    private String TAG = AddOrEditUserFragment.class.getSimpleName();

    private UserViewModel mViewModel;
    private TextInputEditText etName;
    private TextInputEditText etLastName;
    private TextInputEditText etAge;
    private View loading;
    private User user;

    public AddOrEditUserFragment() {
        // Required empty public constructor
    }

    public static AddOrEditUserFragment newInstance() {
        return new AddOrEditUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_or_edit_user, container, false);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        etName = v.findViewById(R.id.etName);
        etLastName = v.findViewById(R.id.etLastName);
        etAge = v.findViewById(R.id.etAge);
        loading = v.findViewById(R.id.loading);
        Button btnAdd = v.findViewById(R.id.btnAdd);
        Button btnEdit = v.findViewById(R.id.btnEdit);

        if (getArguments() != null) {
            String crudKey = getArguments().getString(ConstantUtil.CRUD_KEY);
            user = getArguments().getParcelable(ConstantUtil.USER_KEY);
            if (ConstantUtil.UPDATE_KEY.equals(crudKey)) {
                tvTitle.setText(getString(R.string.label_edit_user));
            }
            Log.i(TAG, "onCreateView: "+ user);
            if (user != null) setUser(user);
            btnAdd.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(user);
            }
        });

        v.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
            }
        });

        return v;
    }

    private void addUser() {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            User user = new User();
            user.setName(etName.getText().toString());
            user.setLastName(etLastName.getText().toString());
            user.setAge(Integer.valueOf(etAge.getText().toString()));
            try {
                long lastId = mViewModel.create(user);
                Log.i(TAG, "addUser: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Add user failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void setUser(User user) {
        etName.setText(String.valueOf(user.getName()));
        etLastName.setText(String.valueOf(user.getLastName()));
        etAge.setText(String.valueOf(user.getAge()));
    }

    private void updateUser(User mUser) {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            User user = new User();
            user.setId(mUser.getId());
            user.setName(etName.getText().toString());
            user.setLastName(etLastName.getText().toString());
            user.setAge(Integer.valueOf(etAge.getText().toString()));
            try {
                long lastId = mViewModel.update(user);
                Log.i(TAG, "updateUser: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Update user failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validate() {

        if (etName.getText().toString().isEmpty()) {
            etName.setError("Name Empty!");
            return false;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Last Name Empty!");
            return false;
        }
        if (etAge.getText().toString().isEmpty()) {
            etAge.setError("Age Empty!");
            return false;
        }

        return true;
    }

    private void reset() {
        etName.setText("");
        etLastName.setText("");
        etAge.setText("");
    }

}
