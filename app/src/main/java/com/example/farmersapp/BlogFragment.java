package com.example.farmersapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.adapter.ListBlogItem_Adapter;
import com.example.farmersapp.model.BlogItem;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BlogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static final String TAG = "checked";

    //widgets
    private RecyclerView recyclerViewBlogItem;
    private FloatingActionButton floatingActionButton;
    private ShimmerFrameLayout shimmerFrameLayout;

    List<BlogItem> mBlogData;


  //Firebase
  private FirebaseAuth mAuth;
  private StorageReference storageReference;
  private CollectionReference collectionReferenceBlog,collectionReferenceBlogId,collectionReferenceusers;
  private FirebaseFirestore firebaseFirestore;
  private DocumentReference documentReferenceBlogId,documentReferenceBlog;


  //adapter
  ListBlogItem_Adapter adapter;



  public BlogFragment() {
        // Required empty public constructor
    }


    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_blog, container, false);

        floatingActionButton = contentView.findViewById(R.id.floatingActionButton_blog);
        recyclerViewBlogItem = contentView.findViewById(R.id.recyclerView_blog);
        shimmerFrameLayout = contentView.findViewById(R.id.shimmerFrameLayout_blog);
        mBlogData = new ArrayList<>();

//        shimmerFrameLayout.startShimmer();

        mAuth = FirebaseAuth.getInstance();
        collectionReferenceBlog = FirebaseFirestore.getInstance().collection("Blog");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddNewBlogFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        setUpRecyclerViewManual();
        getDataToArray();



        return contentView;

    }


  private void getDataToArray() {



    collectionReferenceBlog.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
      @Override
      public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        if(queryDocumentSnapshots.isEmpty())
        {
          Log.d(TAG,"there is nothing in blog database");
        }
        else
        {
          List<BlogItem> data = queryDocumentSnapshots.toObjects(BlogItem.class);
          adapter = new ListBlogItem_Adapter(mBlogData, getContext());
          recyclerViewBlogItem.setAdapter(adapter);
          mBlogData.addAll(data);
          Log.d("checked success:", "ok " + mBlogData);
          shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }



      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
          Log.d(TAG, "blog data load failed");
      }
    });
  }

    private void setUpRecyclerViewManual() {
        recyclerViewBlogItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
//    adapter = new AlternateListAdapter_Market(mData,this.getContext());
//    marketRecyleView.setAdapter(adapter);
        Log.d("checked", "adapter called");
        recyclerViewBlogItem.setLayoutManager(layoutManager);
    }

}
