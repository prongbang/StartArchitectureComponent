package com.prongbang.startroom.views.loan;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.User;

import java.util.List;

/**
 * Created by mdev on 10/17/2017 AD.
 */

public class UserSpinerAdapter extends BaseAdapter {

    private Context context;
    private List<User> mUsers;

    public UserSpinerAdapter(Context context) {
        this.context = context;
    }

    public void setValues(List<User> users) {
        this.mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    @Override
    public User getItem(int position) {
        return mUsers == null ? null : mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_item_user, parent, false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvAge = view.findViewById(R.id.tvAge);
        User user = mUsers.get(position);
        tvAge.setText(String.valueOf(user.getAge()) + " year ago");
        tvName.setText(user.getName());

        return view;
    }

}
