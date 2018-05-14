package view.qingshen.recyclerreandloview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import view.qingshen.recyclerreandloview.adapter.HeaderAndFooterAdapterWrapper;

/**
 * Created by yyp on 2018/3/14.
 */

public class RecyclerReAndLoView extends RecyclerView {
    private boolean refreshable = true;
    private boolean loadable = true;
    private HeaderAndFooterAdapterWrapper wrapper;
    private int headerHeight;
    private float downY;
    private float moveY;
    private float lasty;
    private float dis;
    private float upY;
    private OnDataUpdataListener listener;
    private boolean isRefresging = false;
    private boolean isloading = false;

    public RecyclerReAndLoView(Context context) {
        super(context);
        init();
    }

    public RecyclerReAndLoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerReAndLoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {

            private int anInt;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                int position = layoutManager.findLastCompletelyVisibleItemPosition();
                anInt = wrapper.getItemCount() - wrapper.getFootersCount();
                if (position == anInt) {
                    isloading = true;
//                    wrapper.showfootview();
                    listener.onReadyLoading();
                    listener.onLoading();
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        wrapper = new HeaderAndFooterAdapterWrapper(adapter);
        super.setAdapter(wrapper);
        headerHeight = wrapper.getHeaderViewHeight();
        scrollToPosition(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!refreshable || isRefresging || isloading)
            return super.onTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getRawY();
                lasty = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = e.getRawY();
                if (moveY - lasty >= headerHeight) {
                    listener.onReadyRefresh();
                    setHeadViewHeight(moveY - lasty);
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = e.getRawY();
                if (upY - lasty >= headerHeight) {
                    isRefresging = true;
                    listener.onRefresh();
                } else if (upY - lasty >= 0) {
                    post(new Runnable() {
                        @Override
                        public void run() {
//                            smoothScrollToPosition(1);
                            smoothScrollBy(0, getChildAt(1).getTop());
                            setHeadViewHeight(headerHeight);
                        }
                    });
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private void setHeadViewHeight(float v) {
        wrapper.setHeadViewHeight((int) (v * 0.7));
    }

    public void refreshComplet() {
        isRefresging = false;
//        smoothScrollToPosition(1);
        post(new Runnable() {
            @Override
            public void run() {
//                smoothScrollToPosition(1);
                smoothScrollBy(0, getChildAt(1).getTop());
            }
        });
    }

    public void loadingComplet() {
        isloading = false;
//        wrapper.hidefootview();
    }

    public void setOnDataUpdataListener(OnDataUpdataListener listener) {
        this.listener = listener;
    }

    public void addHeaderView(View view) {
        if (wrapper != null) {
            wrapper.addHeaderView(view);
        }
    }

    public void addFootView(View footview) {
        if (wrapper != null) {
            wrapper.addFootView(footview);
        }
    }

    public interface OnDataUpdataListener {
        void onRefresh();

        void onReadyRefresh();

        void onLoading();

        void onReadyLoading();
    }
}
