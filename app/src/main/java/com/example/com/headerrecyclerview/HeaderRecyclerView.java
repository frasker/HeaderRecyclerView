package com.example.com.headerrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 默默 on 17/9/22.
 */

@SuppressWarnings("ALL")
public class HeaderRecyclerView extends RecyclerView {
    private Adapter mAdapter;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    public HeaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderRecyclerView(Context context) {
        super(context);
    }

    public HeaderRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View v) {

        if (mAdapter != null && !(mAdapter instanceof HeaderViewRecyclerAdapter)) {
            throw new IllegalStateException(
                    "Cannot add header view to list -- setAdapter has already been called.");
        }
        mHeaderViews.add(v);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public boolean removeHeaderView(View v) {
        if (mHeaderViews.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderViewRecyclerAdapter) mAdapter).removeHeader(v)) {
                mAdapter.notifyDataSetChanged();
                result = true;
            }
            removeFixedView(v, mHeaderViews);
            return result;
        }
        return false;
    }

    private void removeFixedView(View v, ArrayList<View> where) {
        int len = where.size();
        for (int i = 0; i < len; ++i) {
            View view = where.get(i);
            if (view == v) {
                where.remove(i);
                break;
            }
        }
    }

    public void addFooterView(View v) {

        if (mAdapter != null && !(mAdapter instanceof HeaderViewRecyclerAdapter)) {
            throw new IllegalStateException(
                    "Cannot add header view to list -- setAdapter has already been called.");
        }
        mFooterViews.add(v);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public boolean removeFooterView(View v) {
        if (mFooterViews.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderViewRecyclerAdapter) mAdapter).removeFooter(v)) {
                mAdapter.notifyDataSetChanged();
                result = true;
            }
            removeFixedView(v, mFooterViews);
            return result;
        }
        return false;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeaderViews.size() > 0 || mFooterViews.size() > 0) {
            mAdapter = new HeaderViewRecyclerAdapter(mHeaderViews, mFooterViews, adapter);
        } else {
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);
    }

    private class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_HEADER = Integer.MIN_VALUE;
        private static final int VIEW_TYPE_FOOTER = Integer.MIN_VALUE / 2;
        private static final int VIEW_TYPE_LOADER = Integer.MAX_VALUE;
        private ArrayList<View> mHeaderViews;
        private ArrayList<View> mFooterViews;
        private RecyclerView.Adapter mAdapter;

        HeaderViewRecyclerAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFooterViews,
                                  RecyclerView.Adapter adapter) {
            if (adapter == null)
                throw new RuntimeException("adapter is null");
            this.mHeaderViews = mHeaderViews;
            this.mFooterViews = mFooterViews;
            if (mAdapter != null) {
                notifyItemRangeRemoved(getHeadersCount(), mAdapter.getItemCount());
                mAdapter.unregisterAdapterDataObserver(mObserver);
            }
            mAdapter = adapter;
            mAdapter.registerAdapterDataObserver(mObserver);
            notifyItemRangeInserted(getHeadersCount(), mAdapter.getItemCount());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType < VIEW_TYPE_HEADER + getHeadersCount()) {
                return new ViewHolder(mHeaderViews.get(viewType - VIEW_TYPE_HEADER));
            } else if (viewType < VIEW_TYPE_FOOTER + getFootersCount()) {
                return new ViewHolder(mFooterViews.get(viewType - VIEW_TYPE_FOOTER));
            }
            return mAdapter.createViewHolder(viewGroup, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position < getHeadersCount() || position >= getHeadersCount() + getCount())
                return;
            mAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }

        @Override
        public int getItemCount() {
            return getFootersCount() + getHeadersCount() + getCount();
        }

        private int getCount() {
            return mAdapter != null ? mAdapter.getItemCount() : 0;
        }

        @Override
        public int getItemViewType(int position) {
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return VIEW_TYPE_HEADER + position;
            }
            // Adapter
            final int adjPosition = position - numHeaders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemViewType(adjPosition);
                }
            }
            int numFooters = getFootersCount();
            final int footPosition = position - numHeaders - adapterCount;
            if (footPosition < numFooters) {
                return VIEW_TYPE_FOOTER + footPosition;
            }
            return VIEW_TYPE_LOADER;
        }

        private int getHeadersCount() {
            return mHeaderViews.size();
        }

        private int getFootersCount() {
            return mFooterViews.size();
        }

        private boolean removeHeader(View v) {
            boolean success = mHeaderViews.remove(v);
            if (success)
                notifyDataSetChanged();
            return success;
        }

        private boolean removeFooter(View v) {
            boolean success = mFooterViews.remove(v);
            if (success)
                notifyDataSetChanged();
            return success;
        }

        private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart + getHeadersCount(), itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                int headerViewsCountCount = getHeadersCount();
                notifyItemRangeChanged(fromPosition + headerViewsCountCount,
                        toPosition + headerViewsCountCount + itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart + getHeadersCount(), itemCount);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
