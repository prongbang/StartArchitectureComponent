package com.prongbang.startroom.views.loan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.User;

import java.util.List;

/**
 * Created by mdev on 10/17/2017 AD.
 */

public class BookSpinerAdapter extends BaseAdapter {

    private Context context;
    private List<Book> mBooks;

    public BookSpinerAdapter(Context context) {
        this.context = context;
    }

    public void setValues(List<Book> books) {
        mBooks = books;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBooks == null ? 0 : mBooks.size();
    }

    @Override
    public Book getItem(int position) {
        return mBooks == null ? null : mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item_book, parent, false);

        TextView tvName = view.findViewById(R.id.tvName);
        Book book = mBooks.get(position);
        tvName.setText(book.getTitle());

        return view;
    }
}
