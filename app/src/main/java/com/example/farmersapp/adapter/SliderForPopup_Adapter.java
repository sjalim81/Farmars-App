package com.example.farmersapp.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmersapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderForPopup_Adapter extends
        SliderViewAdapter<SliderForPopup_Adapter.SliderAdapterVH> {

    private Context context;
    private Uri images[];
    public SliderForPopup_Adapter(Context context, Uri images[]) {
        this.images = images;
        this.context = context;
    }



    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {




//        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/").child(images[position]);
//        Log.d("checkedimage",storageReference.toString());
//        GlideApp.with(viewHolder.itemView)
//                .load(images[position])
//                .fitCenter()
//                .into(viewHolder.imageGifContainer);

        viewHolder.imageGifContainer.setImageURI(images[position]);


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