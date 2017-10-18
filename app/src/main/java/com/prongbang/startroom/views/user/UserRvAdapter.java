package com.prongbang.startroom.views.user;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prongbang.startroom.R;
import com.prongbang.startroom.model.Book;
import com.prongbang.startroom.model.User;
import com.prongbang.startroom.views.book.BookRvAdapter;

import java.util.List;

/**
 * Created by mdev on 10/12/2017 AD.
 */

public class UserRvAdapter extends RecyclerView.Adapter<UserRvAdapter.UserVHolder> {

    private List<User> mUsers;
    private Context mContext;
    private OnMoreClickListener mMoreClickListener;

    public UserRvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    public void setOnMoreClickListener(OnMoreClickListener mMoreClickListener) {
        this.mMoreClickListener = mMoreClickListener;
    }

    @Override
    public UserVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new UserVHolder(view);
    }

    @Override
    public void onBindViewHolder(UserVHolder holder, int position) {

        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    class UserVHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvName;
        private TextView tvAge;
        private ImageView ivMore;

        public UserVHolder(View itemView) {
            super(itemView);

            view = itemView;
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            ivMore = itemView.findViewById(R.id.ivMore);
        }

        void bind(final User user) {
            tvName.setText(user.getName() + " " + user.getLastName());
            tvAge.setText(user.getAge() + " year ago");
            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(mContext, v);
                    pm.getMenuInflater().inflate(R.menu.menu_main, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (mMoreClickListener != null) mMoreClickListener.onClick(item.getItemId(), user);
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        }
    }

    public interface OnMoreClickListener {
        void onClick(int itemId, User data);
    }
}
