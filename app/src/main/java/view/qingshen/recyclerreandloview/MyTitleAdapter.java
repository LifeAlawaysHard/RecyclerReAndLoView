package view.qingshen.recyclerreandloview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;

import view.qingshen.recyclerreandloview.adapter.TitleAdapter;
import view.qingshen.recyclerreandloview.viewholder.ViewHolder;

/**
 * Created by yyp on 2018/4/26.
 */

public class MyTitleAdapter extends TitleAdapter<String> {
    private Context context;
    private ArrayList<String> titleList = new ArrayList();
    private ArrayList<ArrayList<String>> itemList = new ArrayList();

    public MyTitleAdapter(Context context, ArrayList titleList, ArrayList<ArrayList<String>>
            itemList) {
        super(titleList, itemList);
        this.context = context;
        this.titleList = titleList;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        if (viewType == TitleAdapter.TYPE_ITME) {
            viewHolder = ViewHolder.get(context, parent, R.layout.item);
        } else {
            viewHolder = ViewHolder.get(context, parent, R.layout.title);
        }
        return viewHolder;
    }


    @Override
    public void onBindItemViewHolder(ViewHolder holder, int titlePos, int itemPos) {
        holder.setText(R.id.tv, itemList.get(titlePos).get(itemPos));
    }

    @Override
    public void onBindTitleViewHolder(ViewHolder holder, int TitlePos) {
        holder.setText(R.id.tv, titleList.get(TitlePos));
    }
}
