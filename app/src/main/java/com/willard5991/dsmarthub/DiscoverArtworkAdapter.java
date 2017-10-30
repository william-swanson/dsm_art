package com.willard5991.dsmarthub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by willard5991 on 10/25/2017.
 */

public class DiscoverArtworkAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<exhibit> mDataSource;

    public DiscoverArtworkAdapter(Context context, ArrayList<exhibit> items){
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return mDataSource.size();
    }

    @Override
    public exhibit getItem(int position){
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.exhibit_cell, parent, false);
        TextView name = rowView.findViewById(R.id.thumbnailLabel);
        name.setText(getItem(position).getName());

        ImageButton imageButton = rowView.findViewById(R.id.thumbnailButton);
        byte[] imageData = getItem(position).getPhotos().get(0).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        imageButton.setImageBitmap(bitmap);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), AddActivity.class); //TODO: will need to update the name of the class
//                startActivity(intent);
//            }
//        });

//        imageButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(mContext,ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        return rowView;
    }
}
