package com.example.farmersapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewPagerImageAdapterMarketDetails extends PagerAdapter {
    private Context context;
    private String[] imageUrls;
    LayoutInflater inflater;
//     ImageView imageView ;
//     ViewGroup viewGroupContainer;

    ViewPagerImageAdapterMarketDetails(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {

       final ImageView imageView = new ImageView(context);


        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/").child(imageUrls[position]);
        Log.d("checkedimage",storageReference.toString());
        GlideApp.with(imageView.getContext())
                .load(storageReference)
                .into(imageView);

//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imageUrls[position]);
//        storageReference.getBytes(1024*1024)
//                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes) {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                        imageView.setImageBitmap(bitmap);
//                        Log.d("checked","load ok");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("checked",e.toString()) ;
//            }
//        });
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
