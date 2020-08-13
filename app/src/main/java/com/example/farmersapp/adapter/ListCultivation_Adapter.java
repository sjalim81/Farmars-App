package com.example.farmersapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.CultivationItemDetails;
import com.example.farmersapp.Cultivation_DiseaseFragment;
import com.example.farmersapp.Cultivation_FarmingInfoFragment;
import com.example.farmersapp.R;
import com.example.farmersapp.model.CropsModel;
import com.example.farmersapp.model.CustomListItem_Cultivation;
import com.example.farmersapp.model.CustomListItem_DiseaseCategories;
import com.example.farmersapp.model.CustomListItem_Diseases;
import com.example.farmersapp.model.DiseaseCategoriesModel;
import com.example.farmersapp.model.DiseasesModel;
import com.example.farmersapp.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ListCultivation_Adapter extends RecyclerView.Adapter<ListCultivation_Adapter.ListViewHolder> implements Filterable {

    Context mContext;
    List<CustomListItem_Cultivation> mData;
    List<CustomListItem_Cultivation> mDataFiltered;

    Map<String, CustomListItem_Diseases> diseasesModelMap;
    Map<String, CustomListItem_DiseaseCategories> diseaseCategoriesModelMap;


    public static final String DISEASE_PAGE = "disease";
    public static final String INFO_PAGE = "info";

    public ListCultivation_Adapter(Context mContext, List<CustomListItem_Cultivation> mData, Map<String, CustomListItem_Diseases> diseasesModelMap, Map<String, CustomListItem_DiseaseCategories> diseaseCategoriesModelMap) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
        this.diseasesModelMap = diseasesModelMap;
        this.diseaseCategoriesModelMap = diseaseCategoriesModelMap;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData;

                } else {
                    List<CustomListItem_Cultivation> lstFiltered = new ArrayList<>();
                    for (CustomListItem_Cultivation row : mData) {

                        if (row.getNameBangla().contains(Key)) {
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<CustomListItem_Cultivation>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    @NonNull
    @Override
    public ListCultivation_Adapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.customlist_item_cultivation, parent, false);
        return new ListViewHolder(layout);


    }

    @Override
    public void onBindViewHolder(@NonNull ListCultivation_Adapter.ListViewHolder holder, int position) {
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        holder.itemContainer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        holder.title.setText(mDataFiltered.get(position).getNameBangla());
        byte[] decodedByte = Base64.decode(mDataFiltered.get(position).getUserPhoto(), 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        holder.imageView.setImageBitmap(bmp);


    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView imageView;
        LinearLayout itemContainer;
        EasyFlipView mEasyFlipView;
        RelativeLayout tipsButton, diseaseButton;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemContainer = itemView.findViewById(R.id.item_container_cultivation);
            title = itemView.findViewById(R.id.text_view_item_title);
            imageView = itemView.findViewById(R.id.image_view_item);
            mEasyFlipView = itemView.findViewById(R.id.mEasyFlipView);
            tipsButton = itemView.findViewById(R.id.tipsLayout);
            diseaseButton = itemView.findViewById(R.id.diseaseLayout);

            itemView.setOnClickListener(this);
            diseaseButton.setOnClickListener(this);
            tipsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Fragment itemFragment = CultivationItemDetails.newInstance("", "");
            mEasyFlipView.flipTheView();
            int position = getAdapterPosition();
            CustomListItem_Cultivation item = mData.get(position);

            switch (v.getId()) {
                case R.id.diseaseLayout:
                    Fragment itemFragment = Cultivation_DiseaseFragment.newInstance("", "");
                    if (itemFragment != null) {
                        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle args = new Bundle();

                        args.putString("pass", DISEASE_PAGE);
                        CustomListItem_DiseaseCategories tempDiseaseCategoriesModel = diseaseCategoriesModelMap.get(item.getNameEnglish());

                        args.putParcelable("data", (Parcelable) tempDiseaseCategoriesModel);
                        args.putSerializable("dataMap", (Serializable) diseasesModelMap);

                        itemFragment.setArguments(args);

                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Log.d("error", "null exception");
                    }
                    break;
                case R.id.tipsLayout:
                    itemFragment = Cultivation_FarmingInfoFragment.newInstance("", "");
                    if (itemFragment != null) {

                        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle args = new Bundle();

                        args.putString("pass", INFO_PAGE);
                        itemFragment.setArguments(args);

                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Log.d("error", "null exception");
                    }
                    break;
            }

        }
    }
}
