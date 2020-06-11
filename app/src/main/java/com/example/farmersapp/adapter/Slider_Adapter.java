package com.example.farmersapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmersapp.util.GlideApp;
import com.example.farmersapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class Slider_Adapter extends
        SliderViewAdapter<Slider_Adapter.SliderAdapterVH> {

    private Context context;
    private String images[];
    public static final String TAG = "checked";
    public Slider_Adapter(Context context, String images[]) {
        this.images = images;
        this.context = context;
    }

    public Slider_Adapter(Context context, List<String>imagesList) {
        this.images =  imagesList.toArray(new String[0]);
        this.context = context;
        Log.d(TAG,this.images.toString());
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {




        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/").child(images[position]);
        Log.d("checkedimage",storageReference.toString());
        GlideApp.with(viewHolder.itemView)
                .load(storageReference)
                .fitCenter()
                .into(viewHolder.imageGifContainer);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return images.length;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;

        ImageView imageGifContainer;


        public SliderAdapterVH(View itemView) {
            super(itemView);

            imageGifContainer = itemView.findViewById(R.id.imageView_imageslider);

            this.itemView = itemView;
        }
    }

}