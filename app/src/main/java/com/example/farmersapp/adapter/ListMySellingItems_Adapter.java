package com.example.farmersapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.GlideApp;
import com.example.farmersapp.R;
import com.example.farmersapp.productsListOfMarketFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ListMySellingItems_Adapter extends RecyclerView.Adapter<ListMySellingItems_Adapter.ViewHolder> {

    List<productsListOfMarketFirestore>mData;
    Context mContext;

    public ListMySellingItems_Adapter(List<productsListOfMarketFirestore> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListMySellingItems_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_myselling_items, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMySellingItems_Adapter.ViewHolder viewHolder, int position) {
        productsListOfMarketFirestore currentItem = mData.get(position);

        viewHolder.productId.setText(currentItem.getProductId());
        viewHolder.productTitle.setText(currentItem.getProductTitle());
        if(currentItem.getProductSoldStatus().equals("no"))
        {
                viewHolder.textViewSoldUnsold.setText("Unsold");
        }
        else
        {
            viewHolder.textViewSoldUnsold.setText("sold");

        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/user_image1/").child(currentItem.getProductId() + ".jpg");
        Log.d("checkedImageOnProfile",storageReference.toString());
        GlideApp.with(viewHolder.productImage.getContext())
                .load(storageReference)
                .into(viewHolder.productImage);




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productTitle, productId,textViewSoldUnsold;


        public Switch switchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productId = itemView.findViewById(R.id.productId);
            switchButton = itemView.findViewById(R.id.switchButton);
            textViewSoldUnsold = itemView.findViewById(R.id.textView_sold_unsold);



        }
    }
}
