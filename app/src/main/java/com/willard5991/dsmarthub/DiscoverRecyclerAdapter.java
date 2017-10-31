package com.willard5991.dsmarthub;

import android.content.Context;
import android.content.Intent;
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
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //view variables here
        TextView name;
        ImageButton iButton;
        private View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            //use findviewbyid here on your cell
            name = (TextView) view.findViewById(R.id.thumbnailLabel);
            iButton = (ImageButton) view.findViewById(R.id.thumbnailButton);

        }

        public View getView(){
            return this.view;
        }
    }

    public DiscoverRecyclerAdapter(Context context, List<exhibit> dataSource) {
        this.mContext = context;
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
        ImageButton iButton;

        holder.name.setText(mExhibit.getName());
        holder.iButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, ProfileActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}