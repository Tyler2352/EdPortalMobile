package com.example.tyler.test2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tyler on 1/17/2017.
 */
public class DocAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = DocAdapter.class.getSimpleName();
    private Context mContext;
    private List<Doc> docList;

    public DocAdapter(Context context, List<Doc> docList) {
        this.mContext = context;
        this.docList = docList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Doc doc = docList.get(position);
        holder.id.setText(doc.getId());
        holder.projtitle.setText(doc.getProjtitle());

        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, doc.getProjtitle() + "clicked!", Toast.LENGTH_SHORT);
                //Log.d(TAG, doc.getProjtitle() + " clicked!");
                Log.d(TAG, doc.getId());
                Intent intent = new Intent(mContext, DetailedView.class);
                intent.putExtra("DOC_ID",doc.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }
}
