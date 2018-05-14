package view.qingshen.recyclerreandloview.adapter;

import android.content.Context;
import android.support.constraint.solver.Goal;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import view.qingshen.recyclerreandloview.viewholder.ViewHolder;

/**
 * Created by yyp on 2018/4/4.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private ArrayList<T> datas;
    private LayoutInflater mInflater;

    public BaseAdapter(Context context, int layoutId, ArrayList<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getview(holder, datas.get(position));
    }

    public abstract void getview(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
