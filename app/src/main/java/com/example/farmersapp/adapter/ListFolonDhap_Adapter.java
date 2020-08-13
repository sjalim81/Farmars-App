package com.example.farmersapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.Cultivation_Disease_DetailsFragment;
import com.example.farmersapp.R;
import com.example.farmersapp.model.CustomListItem_Diseases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ListFolonDhap_Adapter extends RecyclerView.Adapter<ListFolonDhap_Adapter.ListFolonDhap_ViewHolder>  {

    Context mContext;
    List<String> folonDhap;
    Map<String, CustomListItem_Diseases> diseasesModelMap;
    CustomListItem_Diseases data;

    public ListFolonDhap_Adapter(Context mContext, List<String> folonDhap, Map<String, CustomListItem_Diseases> diseasesModelMap) {
        this.mContext = mContext;
        this.folonDhap = folonDhap;
        this.diseasesModelMap = diseasesModelMap;
    }

    @NonNull
    @Override
    public ListFolonDhap_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        mContext = parent.getContext();
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cultivation_disease, parent, false);

        return new ListFolonDhap_ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListFolonDhap_ViewHolder holder, int position) {

         data = diseasesModelMap.get(folonDhap.get(position));
        assert data != null;
        holder.diseaseTitle.setText(data.getDiseaseTitle());


        byte[] decodedByte = Base64.decode(data.getDiseasePhoto(), 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        holder.diseaseImage.setImageBitmap(bmp);

        holder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Cultivation_Disease_DetailsFragment();
                Bundle args = new Bundle();
                args.putSerializable("dataDisease",(Serializable)data);
                fragment.setArguments(args);
                FragmentManager fragmentManager = ((FragmentActivity)holder.nextButton.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }



    @Override
    public int getItemCount() {
        return folonDhap.size();
    }


    static class ListFolonDhap_ViewHolder extends RecyclerView.ViewHolder{

        ImageView diseaseImage;
        TextView diseaseTitle;
        ImageButton nextButton;
        public ListFolonDhap_ViewHolder(@NonNull View itemView) {
            super(itemView);


            diseaseImage = itemView.findViewById(R.id.diseaseImage_list);
            diseaseTitle = itemView.findViewById(R.id.diseaseTitle_list);

            nextButton = itemView.findViewById(R.id.next_button);

        }
    }
}
