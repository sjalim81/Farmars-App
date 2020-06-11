package com.example.farmersapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.GlideApp;
import com.example.farmersapp.R;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ListMySellingItems_Adapter extends RecyclerView.Adapter<ListMySellingItems_Adapter.ViewHolder> {

    List<productsListOfMarketFirestore>mData;
    Context mContext;
    CollectionReference productsListRef = FirebaseFirestore.getInstance().collection("products_of_market");
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
    public void onBindViewHolder(@NonNull final ListMySellingItems_Adapter.ViewHolder viewHolder, int position) {
        final productsListOfMarketFirestore currentItem = mData.get(position);

        viewHolder.productId.setText(currentItem.getProductId());
        viewHolder.productTitle.setText(currentItem.getProductTitle());
        viewHolder.radioButtonSold.setElevation(100);
        if(currentItem.getProductSoldStatus().equals("no"))
        {
            viewHolder.radioButtonUnsold.setBackgroundColor(Color.BLACK);
            viewHolder.radioButtonSold.setBackgroundColor(Color.TRANSPARENT);
        }
        else
        {
            viewHolder.radioButtonUnsold.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.radioButtonSold.setBackgroundColor(Color.BLACK);
        }
        viewHolder.radioGroupToggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId==R.id.radioButton_unsold)
                {

                    viewHolder.radioButtonUnsold.setBackgroundColor(Color.BLACK);
                    viewHolder.radioButtonSold.setBackgroundColor(Color.TRANSPARENT);

                    DocumentReference productDocument = productsListRef.document(currentItem.getProductId());
                    productDocument.update("productSoldStatus","no").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(viewHolder.radioButtonUnsold.getContext(),"Updated Unsold",Toast.LENGTH_SHORT).show();
                        }
                    });;

                }
                else if(checkedId==R.id.radioButton_sold)
                {
                    viewHolder.radioButtonUnsold.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.radioButtonSold.setBackgroundColor(Color.BLACK);
                    DocumentReference productDocument = productsListRef.document(currentItem.getProductId());
                    productDocument.update("productSoldStatus","yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(viewHolder.radioButtonSold.getContext(),"Updated Sold",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });


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
        public TextView productTitle, productId;
        public RadioGroup radioGroupToggle;
        public RadioButton radioButtonSold,radioButtonUnsold;


        public Switch switchButton;

        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productId = itemView.findViewById(R.id.productId);
            radioButtonSold = itemView.findViewById(R.id.radioButton_sold);
            radioButtonUnsold = itemView.findViewById(R.id.radioButton_unsold);
            radioGroupToggle = itemView.findViewById(R.id.radioGroup_toggle);




        }
    }
}
