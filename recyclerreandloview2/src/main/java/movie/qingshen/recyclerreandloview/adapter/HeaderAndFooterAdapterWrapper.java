package movie.qingshen.recyclerreandloview.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import movie.qingshen.recyclerreandloview.viewholder.ViewHolder;


/**
 * Created by yyp on 2018/4/11.
 */

public class HeaderAndFooterAdapterWrapper<T> extends RecyclerView.Adapter<RecyclerView
        .ViewHolder> {
    protected static final int TYPE_HEADER = 10000;
    protected static final int TYPE_FOOT = 20000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    protected RecyclerView.Adapter mInnerAdapter;
    public HeaderAndFooterAdapterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    public void setHeadViewHeight(int height) {
        View view = mHeaderViews.get(TYPE_HEADER);
        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + TYPE_FOOT, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {

            ViewHolder holder = new ViewHolder(parent.getContext(), mHeaderViews.get
                    (viewType), parent);
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            ViewHolder holder = new ViewHolder(parent.getContext(), mFootViews.get
                    (viewType), parent);
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    public int getHeaderViewHeight() {
        int height = 0;
        for (int i = 0; i < mHeaderViews.size(); i++) {
            View view = mHeaderViews.get(TYPE_HEADER + i);
            LayoutParams layoutParams = view.getLayoutParams();
            height += layoutParams.height;
        }
        return height;
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    public void hidefootview() {
        for (int i = 0; i < mFootViews.size(); i++) {
            mFootViews.get(i + TYPE_FOOT).setVisibility(View.GONE);
        }
    }

    public void showfootview() {
        for (int i = 0; i < mFootViews.size(); i++) {
            mFootViews.get(i + TYPE_FOOT).setVisibility(View.VISIBLE);
        }
    }
}
