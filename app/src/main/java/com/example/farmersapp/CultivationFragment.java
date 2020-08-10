package com.example.farmersapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.adapter.ListCultivation_Adapter;
import com.example.farmersapp.model.CustomListItem_Cultivation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CultivationFragment extends Fragment {

    RecyclerView CultivationRecylerView;
    ListCultivation_Adapter cultivationListAdapterCultivation;
    List<CustomListItem_Cultivation> mData;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";



    public static CultivationFragment newInstance() {
        return new CultivationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("check","cultivation_fragment");
        View contentView = inflater.inflate(R.layout.cultivation_fragment, container, false);

        rootLayout = contentView.findViewById(R.id.root_layout);
        searchInput = contentView.findViewById(R.id.search_input);
        CultivationRecylerView = contentView.findViewById(R.id.cultivation_rv);
        mData = new ArrayList<>();

        searchInput.setBackgroundResource(R.drawable.search_input_style);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));



        Type type = new TypeToken<List<CustomListItem_Cultivation>>() {
        }.getType();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(DataLoadActivity.SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(DataLoadActivity.CULTIVATION_ITEMS, "");
        mData = gson.fromJson(json, type);


        cultivationListAdapterCultivation = new ListCultivation_Adapter(this.getContext(),mData);
        CultivationRecylerView.setAdapter(cultivationListAdapterCultivation);
        CultivationRecylerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                cultivationListAdapterCultivation.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return contentView;
    }


}
