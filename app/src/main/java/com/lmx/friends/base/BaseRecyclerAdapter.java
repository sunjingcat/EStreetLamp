package com.lmx.friends.base;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sunjing on 2018/6/2.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private ArrayList<T> mDatas = new ArrayList<>();

    private View mHeaderView, mFooterView;
    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public View getHeader() {
        return mHeaderView;
    }

    public void setHeader(View mHeader) {
        this.mHeaderView = mHeader;
        notifyItemInserted(0);
    }

    public View getFooter() {
        return mFooterView;
    }

    public void setFooter(View mFooter) {
        this.mFooterView = mFooter;
        if (mHeaderView == null) {
            notifyItemInserted(mDatas.size());
        } else {
            notifyItemInserted(mDatas.size() + 1);
        }
    }

    public void addDatas(ArrayList<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            if (mFooterView == null) {
                return TYPE_NORMAL;
            } else {
                if (position == mDatas.size()) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_NORMAL;
                }
            }
        } else {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                if (mFooterView == null) {
                    return TYPE_NORMAL;
                } else {
                    if (position != mDatas.size() + 1) {
                        return TYPE_NORMAL;
                    } else {
                        return TYPE_FOOTER;
                    }
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new Holder(mHeaderView);
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new Holder(mFooterView);
        }
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
            return;
        final int realPosition = getRealPosition(holder);
        final T data = mDatas.get(realPosition);
        onBind(holder, realPosition, data);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(realPosition, data);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(realPosition, data);
                    return false;
                }
            });
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder viewHolder) {
        int layoutPosition = viewHolder.getLayoutPosition();
        return mHeaderView == null ? layoutPosition : layoutPosition - 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            params.setFullSpan(getItemViewType(holder.getLayoutPosition()) == TYPE_HEADER || getItemViewType(holder.getLayoutPosition()) == TYPE_FOOTER);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            if (mFooterView == null) {
                return mDatas.size();
            } else {
                return mDatas.size() + 1;
            }
        } else {
            if (mFooterView == null) {
                return mDatas.size() + 1;
            } else {
                return mDatas.size() + 2;
            }
        }
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public interface onItemClickListener<T> {
        void onItemClick(int position, T data);

        void onItemLongClick(int position, T data);
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}