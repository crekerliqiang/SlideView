package com.cerkerli.slideview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.com.library.view.SlideView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    public String[] data;

    public MyAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public SlideView tvItem;
//
        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (SlideView) itemView.findViewById(R.id.slide_view1);
        }
    }
}