package com.prongbang.startroom.views.loan;


import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.Loan;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.DateUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.utils.KeyboardUtil;
import com.prongbang.startroom.views.BaseFragment;
import com.prongbang.startroom.views.book.BookViewModel;
import com.prongbang.startroom.views.user.UserViewModel;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrEditLoanFragment extends BaseFragment implements View.OnClickListener {

    private String TAG = AddOrEditLoanFragment.class.getSimpleName();

    private UserViewModel mUserViewModel;
    private BookViewModel mBookViewModel;
    private LoanViewModel mLoanViewModel;
    private UserSpinerAdapter mUserSpinerAdapter;
    private BookSpinerAdapter mBookSpinerAdapter;
    private Loan loan;
    private User user;
    private Book book;

    private TextInputEditText etStartTime;
    private TextInputEditText etEndTime;
    private View loading;
    private TextView tvTitle;
    private Spinner mSpinner;
    private Spinner pSpinner;

    public AddOrEditLoanFragment() {
        // Required empty public constructor
    }

    public static AddOrEditLoanFragment newInstance() {
        return new AddOrEditLoanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_or_edit_loan, container, false);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mLoanViewModel = ViewModelProviders.of(this).get(LoanViewModel.class);

        mSpinner = v.findViewById(R.id.spinnerUser);
        pSpinner = v.findViewById(R.id.spinnerBook);
        tvTitle = v.findViewById(R.id.tvTitle);
        etStartTime = v.findViewById(R.id.etStartTime);
        etEndTime = v.findViewById(R.id.etEndTime);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        loading = v.findViewById(R.id.loading);
        Button btnAdd = v.findViewById(R.id.btnAdd);
        Button btnEdit = v.findViewById(R.id.btnEdit);

        if (getArguments() != null) {
            String crudKey = getArguments().getString(ConstantUtil.CRUD_KEY);
            loan = getArguments().getParcelable(ConstantUtil.LOAN_KEY);
            if (ConstantUtil.UPDATE_KEY.equals(crudKey)) {
                tvTitle.setText(getString(R.string.label_edit_loan));
            }
            if (loan != null) setLoan(loan);
            btnAdd.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoan();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLoan(loan);
            }
        });

        v.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
            }
        });

        // User spinner
        mUserSpinerAdapter = new UserSpinerAdapter(getContext());
        mSpinner.setAdapter(mUserSpinerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                user = mUserSpinerAdapter.getItem(position);
                // Here you can do the action you want to...
                Log.i(TAG, "onItemSelected: " + user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        mUserViewModel.findAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                mUserSpinerAdapter.setValues(users);
                activeUser(users);
            }
        });

        // Book spinner
        mBookSpinerAdapter = new BookSpinerAdapter(getContext());
        pSpinner.setAdapter(mBookSpinerAdapter);
        pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                book = mBookSpinerAdapter.getItem(position);
                // Here you can do the action you want to...
                Log.i(TAG, "onItemSelected: " + book);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        mBookViewModel.findAll().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                mBookSpinerAdapter.setValues(books);
                activeBook(books);
            }
        });

        return v;
    }

    private void updateLoan(Loan mLoan) {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            Loan loan = new Loan();
            loan.setId(mLoan.getId());
            loan.setBookId(book.getId());
            loan.setUserId(user.getId());
            loan.setEndTime(DateUtil.toDate(etEndTime.getText().toString()));
            loan.setStartTime(DateUtil.toDate(etStartTime.getText().toString()));
            try {
                long lastId = mLoanViewModel.update(loan);
                Log.i(TAG, "updateLoan: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Update loan failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addLoan() {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            Loan loan = new Loan();
            loan.setBookId(book.getId());
            loan.setUserId(user.getId());
            loan.setEndTime(DateUtil.toDate(etEndTime.getText().toString()));
            loan.setStartTime(DateUtil.toDate(etStartTime.getText().toString()));
            try {
                long lastId = mLoanViewModel.create(loan);
                Log.i(TAG, "addLoan: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Add loan failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        switch (v.getId()) {
            case R.id.etStartTime:
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etStartTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay).show();
                break;
            case R.id.etEndTime:

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etEndTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay).show();

                break;
        }
    }

    public void setLoan(Loan loan) {
        etEndTime.setText(DateUtil.toYyyyMmDd(loan.getEndTime()));
        etStartTime.setText(DateUtil.toYyyyMmDd(loan.getStartTime()));
    }

    private void activeUser(List<User> mUsers) {
        if (getArguments() != null) {
            String crudKey = getArguments().getString(ConstantUtil.CRUD_KEY);
            if (ConstantUtil.UPDATE_KEY.equals(crudKey)) {
                for (int i = 0; i < mUsers.size(); i++) {
                    if (loan.getUserId() == mUsers.get(i).getId()) {
                        mSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void activeBook(List<Book> mBooks) {
        if (getArguments() != null) {
            String crudKey = getArguments().getString(ConstantUtil.CRUD_KEY);
            if (ConstantUtil.UPDATE_KEY.equals(crudKey)) {
                for (int i = 0; i < mBooks.size(); i++) {
                    if (loan.getBookId() == mBooks.get(i).getId()) {
                        pSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private boolean validate() {

        if (book == null) {
            Toast.makeText(getContext(), "Please Select Book Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user == null) {
            Toast.makeText(getContext(), "Please Select User Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etStartTime.getText().toString().isEmpty()) {
            etStartTime.setError("Start Time Empty!");
            return false;
        }
        if (etEndTime.getText().toString().isEmpty()) {
            etStartTime.requestFocus();
            etEndTime.setError("End Time Empty!");
            return false;
        }

        return true;
    }

    private void reset() {
        etStartTime.setText("");
        etEndTime.setText("");
    }
}
