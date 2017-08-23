package com.example.tyler.test2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Tyler on 1/18/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView id, projtitle;
    public View rootView;

    public MyViewHolder(View view) {
        super(view);
        rootView = view;
        id = (TextView) view.findViewById(R.id.projid);
        projtitle = (TextView) view.findViewById(R.id.projtitle);
    }

    public View getRootView() {
        return rootView;
    }
}
