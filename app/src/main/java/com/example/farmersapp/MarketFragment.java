package com.example.farmersapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.adapter.ListMarket_Adapter;
import com.example.farmersapp.adapter.ListMarket_Alternate_Adapter;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MarketFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FloatingActionButton floatingAddNewItemButton;

    private String mParam1;
    private String mParam2;

    RecyclerView marketRecyleView;
    ListMarket_Adapter marketListAdapter;
    ArrayList<productsListOfMarketFirestore> mData;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";
    FirestoreRecyclerOptions<productsListOfMarketFirestore> options;

    ListMarket_Alternate_Adapter adapter;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference productsOfMarketCollectionRef = firebaseFirestore.collection("products_of_market");

    public MarketFragment() {
    }

    public static MarketFragment newInstance(String param1, String param2) {
        MarketFragment fragment = new MarketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    productsListOfMarketFirestore[] item = new productsListOfMarketFirestore[100];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_market, container, false);

        floatingAddNewItemButton = contentView.findViewById(R.id.floatingActionButton_blog);
        marketRecyleView = contentView.findViewById(R.id.market_rv);
        rootLayout = contentView.findViewById(R.id.root_layout);
        searchInput = contentView.findViewById(R.id.search_input);
        mData = new ArrayList<>();


        searchInput.setBackgroundResource(R.drawable.search_input_style);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        setUpRecyclerViewManual();
        getDataToArray();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                adapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        Log.d("checked", "i am here");
        floatingAddNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddNewItemFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return contentView;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getDataToArray() {
        productsOfMarketCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<productsListOfMarketFirestore> data = queryDocumentSnapshots.toObjects(productsListOfMarketFirestore.class);
                    adapter = new ListMarket_Alternate_Adapter(mData, getContext());
                    marketRecyleView.setAdapter(adapter);
                    mData.addAll(data);
                    Log.d("checked success:", "ok " + mData);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


    }

    private void setUpRecyclerViewManual() {
        marketRecyleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
//    adapter = new ListMarket_Alternate_Adapter(mData,this.getContext());
//    marketRecyleView.setAdapter(adapter);
        Log.d("checked", "adapter called");

        marketRecyleView.setLayoutManager(layoutManager);


    }

}
