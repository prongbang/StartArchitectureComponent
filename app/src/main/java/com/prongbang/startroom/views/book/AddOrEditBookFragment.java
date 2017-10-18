package com.prongbang.startroom.views.book;


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
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.utils.KeyboardUtil;
import com.prongbang.startroom.views.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrEditBookFragment extends BaseFragment {

    private TextInputEditText etTitle;
    private TextView tvTitle;
    private View loading;
    private Book book;
    private BookViewModel mBookViewModel;

    public AddOrEditBookFragment() {
        // Required empty public constructor
    }

    public static AddOrEditBookFragment newInstance() {
        return new AddOrEditBookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_or_edit_book, container, false);

        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);

        etTitle = v.findViewById(R.id.etTitle);
        tvTitle = v.findViewById(R.id.tvTitle);
        loading = v.findViewById(R.id.loading);
        Button btnAdd = v.findViewById(R.id.btnAdd);
        Button btnEdit = v.findViewById(R.id.btnEdit);

        if (getArguments() != null) {
            String crudKey = getArguments().getString(ConstantUtil.CRUD_KEY);
            book = getArguments().getParcelable(ConstantUtil.BOOK_KEY);
            if (ConstantUtil.UPDATE_KEY.equals(crudKey)) {
                tvTitle.setText(getString(R.string.label_edit_book));
            }
            if (book != null) setBook(book);
            btnAdd.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook(book);
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

    private void updateBook(Book mBook) {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            Book book = new Book();
            book.setId(mBook.getId());
            book.setTitle(etTitle.getText().toString());
            try {
                long lastId = mBookViewModel.update(book);
                Log.i(TAG, "updateBook: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Update book failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addBook() {
        KeyboardUtil.hideKeyboard(getActivity());
        if (validate()) {
            loading.setVisibility(View.VISIBLE);
            Book book = new Book();
            book.setTitle(etTitle.getText().toString());
            try {
                long lastId = mBookViewModel.create(book);
                Log.i(TAG, "addBook: " + lastId);
                loading.setVisibility(View.GONE);
                if (lastId > 0) {
                    reset();
                    FragmentUtil.popBackStack(getActivity().getSupportFragmentManager());
                } else {
                    alert(getContext(), "Error", "Add book failed!");
                }
            } catch (Exception e) {
                loading.setVisibility(View.GONE);
                alert(getContext(), "Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void setBook(Book book) {
        etTitle.setText(String.valueOf(book.getTitle()));
    }

    private boolean validate() {

        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("Title Empty!");
            return false;
        }

        return true;
    }

    private void reset() {
        etTitle.setText("");
    }
}
