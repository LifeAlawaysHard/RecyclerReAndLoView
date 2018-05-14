package view.qingshen.recyclerreandloview.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import view.qingshen.recyclerreandloview.viewholder.ViewHolder;

/**
 * Created by yyp on 2018/4/26.
 */

public abstract class TitleAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList titleList = new ArrayList();
    private ArrayList<ArrayList<T>> itemList = new ArrayList();
    public static final int TYPE_TITLE = 30000;
    public static final int TYPE_ITME = 40000;
    public ArrayList<InnerKV> tempList = new ArrayList();

    public TitleAdapter(ArrayList titleList, ArrayList<ArrayList<T>>
            itemList) {
        this.titleList = titleList;
        this.itemList = itemList;
        mixData();
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    private void mixData() {
        for (int i = 0; i < titleList.size(); i++) {
            tempList.add(new InnerKV(true, i));
            ArrayList arrayList = itemList.get(i);
            for (int k = 0; k < arrayList.size(); k++) {
                tempList.add(new InnerKV(false, i * 10000 + k));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (tempList.get(position).isTitle()) {
            return TYPE_TITLE;
        } else {
            return TYPE_ITME;
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (tempList.get(position).isTitle()) {
            onBindTitleViewHolder(holder, tempList.get(position).getPosition());
        } else {
            onBindItemViewHolder(holder, tempList.get(position).getPosition() / 10000, tempList
                    .get(position).getPosition() % 10000);
        }
    }

    public abstract void onBindItemViewHolder(ViewHolder holder, int titlePos, int itemPos);

    public abstract void onBindTitleViewHolder(ViewHolder holder, int TitlePos);

    private class InnerKV {
        private boolean isTitle;
        private int position;

        public boolean isTitle() {
            return isTitle;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public InnerKV(boolean isTitle, int position) {
            this.isTitle = isTitle;
            this.position = position;
        }
    }
}
