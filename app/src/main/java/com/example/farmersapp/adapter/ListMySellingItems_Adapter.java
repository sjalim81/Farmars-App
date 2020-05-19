package com.example.farmersapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.R;

public class ListMySellingItems_Adapter extends RecyclerView.Adapter<ListMySellingItems_Adapter.ViewHolder> {
    @NonNull
    @Override
    public ListMySellingItems_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_myselling_items, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMySellingItems_Adapter.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productTitle, productId;
        public Switch switchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productId = itemView.findViewById(R.id.productId);
            switchButton = itemView.findViewById(R.id.switchButton);
        }
    }
}
