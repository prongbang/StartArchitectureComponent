package com.prongbang.startroom.views.book;

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

import java.util.List;

/**
 * Created by mdev on 10/12/2017 AD.
 */

public class BookRvAdapter extends RecyclerView.Adapter<BookRvAdapter.BookVHolder> {

    private List<Book> mBooks;
    private Context mContext;
    private OnMoreClickListener mMoreClickListener;

    public BookRvAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Book> books) {
        mBooks = books;
        notifyDataSetChanged();
    }

    public void setOnMoreClickListener(OnMoreClickListener mMoreClickListener) {
        this.mMoreClickListener = mMoreClickListener;
    }

    @Override
    public BookVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book, parent, false);
        return new BookVHolder(view);
    }

    @Override
    public void onBindViewHolder(BookVHolder holder, int position) {

        holder.bind(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks == null ? 0 : mBooks.size();
    }

    class BookVHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvBookTitle;
        private ImageView ivMore;

        BookVHolder(View itemView) {
            super(itemView);

            view = itemView;
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            ivMore = itemView.findViewById(R.id.ivMore);
        }

        void bind(final Book book) {
            tvBookTitle.setText(book.getTitle());
            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(mContext, v);
                    pm.getMenuInflater().inflate(R.menu.menu_main, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (mMoreClickListener != null) mMoreClickListener.onClick(item.getItemId(), book);
                            return true;
                        }
                    });
                    pm.show();
                }
            });
        }
    }

    public interface OnMoreClickListener {
        void onClick(int itemId, Book data);
    }

}
