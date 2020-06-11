package com.example.farmersapp.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.model.CustomListItem_Market;
import com.example.farmersapp.MarketItemDetails;
import com.example.farmersapp.R;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ListMarket_Adapter extends FirestoreRecyclerAdapter<productsListOfMarketFirestore, ListMarket_Adapter.ListViewHolder> {
   ArrayList<CustomListItem_Market> mData;
   Context mContext;

    public ListMarket_Adapter(@NonNull FirestoreRecyclerOptions<productsListOfMarketFirestore> options) {
        super(options);


    }

//    public ListMarket_Adapter(@NonNull FirestoreRecyclerOptions<productsListOfMarketFirestore> options, ArrayList<CustomListItem_Market> mData) {
//        super(options);
//        this.mData = mData;
//    }

    @Override
    public ListMarket_Adapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        mContext = parent.getContext();
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlist_item_market, parent, false);
        return new ListViewHolder(layout);


    }

    @NonNull
    @Override
    protected void onBindViewHolder(@NonNull final ListViewHolder holder, int position, @NonNull productsListOfMarketFirestore model) {
//        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
//        holder.itemContainer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));


        holder.title.setText(model.getProductTitle());
        holder.price.setText(model.getProductPrice());
        holder.productId.setText(model.getProductId());

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/user_image1/").child(model.getProductId() + ".jpg");
        try {
            final File file = File.createTempFile("image", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.imageView.setImageBitmap(bitmap);

                }
            });
//            Log.d("checked",model.getProductTitle()+" "+model.getProductId()+" "+model.getProductPrice());



//            mData.add(new CustomListItem_Market(model.getProductTitle(),model.getProductId(),model.getProductPrice()));



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<CustomListItem_Market> getmData() {
        return mData;
    }

     class ListViewHolder extends RecyclerView.ViewHolder {

        TextView title, price,productId;

        ImageView imageView;
        RelativeLayout itemContainer;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment itemFragment = MarketItemDetails.newInstance("", "");
                    if (itemFragment != null) {

                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle args = new Bundle();

                        args.putString("productId", productId.getText().toString());

                        itemFragment.setArguments(args);

                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Log.d("error", "null exception");
                    }
                }
            });

            itemContainer = itemView.findViewById(R.id.item_container_market);
            title = itemView.findViewById(R.id.textView_item_title_market);
            imageView = itemView.findViewById(R.id.imageView_item_market);
            price = itemView.findViewById(R.id.textView_price);
            productId = itemView.findViewById(R.id.textView_productId);
        }



    }

}
