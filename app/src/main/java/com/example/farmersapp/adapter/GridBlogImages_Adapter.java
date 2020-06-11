package com.example.farmersapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.farmersapp.util.GlideApp;
import com.example.farmersapp.R;

import java.util.List;


public class GridBlogImages_Adapter extends BaseAdapter {

    List<Uri> images;
    LayoutInflater inflater;
    Context mContext;
    public static final String TAG = "checked";

    public GridBlogImages_Adapter(List<Uri> images, Context mContext) {
        this.images = images;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageURI(images.get(position));
        return imageView;




    }
}
