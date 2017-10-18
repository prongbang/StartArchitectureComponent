package com.prongbang.startroom.views.book;


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
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.LoanWithUserAndBook;
import com.prongbang.startroom.utils.ConstantUtil;
import com.prongbang.startroom.utils.FragmentUtil;
import com.prongbang.startroom.views.BaseFragment;
import com.prongbang.startroom.views.loan.LoanRvAdapter;
import com.prongbang.startroom.views.user.AddOrEditUserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment {

    private String TAG = BookFragment.class.getSimpleName();

    private BookViewModel mBookViewModel;
    private BookRvAdapter mAdapter;

    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book, container, false);

        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        mAdapter = new BookRvAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnMoreClickListener(new BookRvAdapter.OnMoreClickListener() {
            @Override
            public void onClick(int itemId, final Book data) {
                switch (itemId) {
                    case R.id.edit:
                        Log.i(TAG, "onClick: Edit");
                        AddOrEditBookFragment aoeuFragment = new AddOrEditBookFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstantUtil.CRUD_KEY, ConstantUtil.UPDATE_KEY);
                        bundle.putParcelable(ConstantUtil.BOOK_KEY, data);
                        aoeuFragment.setArguments(bundle);
                        FragmentUtil.addFragmentToActivity(getActivity().getSupportFragmentManager(), aoeuFragment, R.id.container);
                        break;
                    case R.id.delete:
                        Log.i(TAG, "onClick: Delete");
                        new AlertDialog.Builder(getContext())
                                .setTitle(getString(R.string.label_confirm))
                                .setMessage(getString(R.string.label_confirm_delete, data.getTitle()))
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
                                            int id = mBookViewModel.delete(data);
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

        mBookViewModel.findAll().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                mAdapter.setData(books);
            }
        });

        return v;
    }

}
