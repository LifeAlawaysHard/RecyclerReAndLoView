package view.qingshen.recyclerreandloview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import movie.qingshen.recyclerreandloview.RecyclerReAndLoView;
import movie.qingshen.recyclerreandloview.RecyclerReAndLoView.OnDataUpdataListener;
import movie.qingshen.recyclerreandloview.adapter.BaseAdapter;
import movie.qingshen.recyclerreandloview.viewholder.ViewHolder;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> datas = new ArrayList<>();
    private BaseAdapter<String> adapter;
    private RecyclerReAndLoView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerreandloview);
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        TabLayout tl = (TabLayout) findViewById(R.id.tl);
        rv = (RecyclerReAndLoView) findViewById(R.id.rv);
        Button btn = (Button) findViewById(R.id.btn);
        tl.addTab(tl.newTab().setText("page1"));
        tl.addTab(tl.newTab().setText("page2"));
        tl.addTab(tl.newTab().setText("page3"));
        tl.addTab(tl.newTab().setText("page4"));
        tl.setTabTextColors(Color.parseColor("#333333"), Color.parseColor("#ff4444"));
//        tl.setSelectedTabIndicatorColor(Color.parseColor("#ff4444"));
        for (int i = 0; i < 20; i++) {
            datas.add("第" + i + "行");
        }
        adapter = new BaseAdapter<String>(this, R.layout.item, datas) {
            @Override
            public void getview(ViewHolder holder, String s) {
                holder.setText(R.id.tv, s);
            }
        };
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        final View view = LayoutInflater.from(this).inflate(R.layout.item_header, rv, false);
        rv.addHeaderView(view);
        final View footview = LayoutInflater.from(this).inflate(R.layout.item_foot, rv, false);
        rv.addFootView(footview);
        rv.setOnDataUpdataListener(new OnDataUpdataListener() {
            @Override
            public void onRefresh() {
                TextView viewById = (TextView) view.findViewById(R.id.tv);
                viewById.setText("正在刷新");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView iv = (ImageView) view.findViewById(R.id.iv);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 720f, 0f);
                        animator.setInterpolator(new LinearInterpolator());
                        animator.setDuration(2000);
                        animator.addListener(new AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                rv.loadingComplet();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        animator.start();
                    }
                });
                refresh();
            }

            @Override
            public void onReadyRefresh() {
                TextView viewById = (TextView) view.findViewById(R.id.tv);
                viewById.setText("松开开始刷新");
            }

            @Override
            public void onLoading() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        wrapper.notifyDataSetChanged();
                        for (int i = 0; i < 5; i++) {
                            datas.add("第" + i + "行");
                        }
                        rv.getAdapter().notifyDataSetChanged();
//                        wrapper.addFootView(footview);
                        rv.loadingComplet();
                    }
                }, 1000);

            }

            @Override
            public void onReadyLoading() {
                TextView tv = (TextView) footview.findViewById(R.id.tv);
                tv.setText("正在加载");
            }
        });

    }

    private void refresh() {
//        rv.scrollToPosition(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rv.refreshComplet();
            }
        }, 2000);
//        rv.smoothScrollToPosition(1);

    }
}
