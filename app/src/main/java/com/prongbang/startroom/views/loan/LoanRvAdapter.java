package com.prongbang.startroom.views.loan;

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
import com.prongbang.startroom.model.LoanWithUserAndBook;
import com.prongbang.startroom.utils.DateUtil;

import java.util.List;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class LoanRvAdapter extends RecyclerView.Adapter<LoanRvAdapter.LoanVHolder> {

    private List<LoanWithUserAndBook> mLoans;
    private Context mContext;
    private OnMoreClickListener mMoreClickListener;

    public LoanRvAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<LoanWithUserAndBook> loans) {
        mLoans = loans;
        notifyDataSetChanged();
    }

    @Override
    public LoanVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_loan, parent, false);

        return new LoanVHolder(view);
    }

    @Override
    public void onBindViewHolder(LoanVHolder holder, int position) {

        holder.bind(mLoans.get(position));
    }

    @Override
    public int getItemCount() {
        return mLoans == null ? 0 : mLoans.size();
    }

    public void setOnMoreClickListener(OnMoreClickListener mMoreClickListener) {
        this.mMoreClickListener = mMoreClickListener;
    }

    class LoanVHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView ivMore;
        private TextView tvBookTitle;
        private TextView tvName;
        private TextView tvStartEndDate;

        LoanVHolder(View itemView) {
            super(itemView);

            ivMore = itemView.findViewById(R.id.ivMore);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvName = itemView.findViewById(R.id.tvName);
            tvStartEndDate = itemView.findViewById(R.id.tvStartEndDate);
        }

        void bind(final LoanWithUserAndBook loans) {
            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(mContext, v);
                    pm.getMenuInflater().inflate(R.menu.menu_main, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (mMoreClickListener != null) mMoreClickListener.onClick(item.getItemId(), loans);
                            return true;
                        }
                    });
                    pm.show();
                }
            });

            tvBookTitle.setText(loans.getBookTitle());
            tvName.setText(loans.getUserName() + " " + loans.getLastName());
            tvStartEndDate.setText(DateUtil.toYyyyMmDd(loans.getStartTime()) + " - " + DateUtil.toYyyyMmDd(loans.getEndTime()));
        }
    }

    public interface OnMoreClickListener {
        void onClick(int itemId, LoanWithUserAndBook data);
    }
}
