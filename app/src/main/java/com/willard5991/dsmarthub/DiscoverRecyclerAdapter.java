package com.willard5991.dsmarthub;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by clayton on 10/29/17.
 */

//This is a recyclerviewadapter implementation

public class DiscoverRecyclerAdapter extends RecyclerView.Adapter<DiscoverRecyclerAdapter.MyViewHolder> {

    //your data source
    private List<exhibit> mDataSource;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //view variables here
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            //use findviewbyid here on your cell
            name = (TextView) view.findViewById(R.id.thumbnailLabel);
        }
    }

    public DiscoverRecyclerAdapter(List<exhibit> dataSource) {
        this.mDataSource = dataSource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflates your cell layout here
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exhibit_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //sets your view content here
        exhibit mExhibit = mDataSource.get(position);

        holder.name.setText(mExhibit.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}