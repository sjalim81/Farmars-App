package com.example.farmersapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
;import com.example.farmersapp.R;
import com.example.farmersapp.SliderAdapterExample;
import com.example.farmersapp.model.BlogItem;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.okhttp.Cache;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListBlogItem_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_WITH_PIC = 1;
    private static final int ITEM_TYPE_WITHOUT_PIC = 2;
    public static final String TAG = "checked";
    List<BlogItem> items;
    Context mContext;
     String tempLikedCount;
    FirebaseAuth mAuth;
    DocumentReference documentReferenceUser;
    FirebaseFirestore firebaseFirestore;



    public ListBlogItem_Adapter(List<BlogItem> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        documentReferenceUser = FirebaseFirestore.getInstance().collection("users").document(Objects.requireNonNull(mAuth.getUid()));
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == ITEM_TYPE_WITH_PIC) {
            view = layoutInflater.inflate(R.layout.customlist_item_blog_with_pic, parent, false);

            return new WithPicViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.customlist_item_blog_without_pic, parent, false);

            return new WithoutPicViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int tempNumber = Integer.parseInt(items.get(position).getNumberOfPhotos());

        Log.d(TAG,"type check number ");

        if ( tempNumber== 0) {
            return ITEM_TYPE_WITHOUT_PIC;
        } else {
            return ITEM_TYPE_WITH_PIC;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Object item = items.get(position);

        switch (holder.getItemViewType()) {
            case ITEM_TYPE_WITH_PIC:

                final WithPicViewHolder viewHolderPic = (WithPicViewHolder) holder;
                List<String> images = new ArrayList<>();
                for (int i = 0; i < Integer.parseInt(items.get(position).getNumberOfPhotos()); i++) {
                    images.add("blog/" + items.get(position).getBlogId() + "/" + i+".jpg");
                }

                SliderAdapterExample sliderAdapter = new SliderAdapterExample(viewHolderPic.sliderView.getContext(), images);
                viewHolderPic.sliderView.setSliderAdapter(sliderAdapter);

                viewHolderPic.likeTextView.setText(items.get(position).getLike());
                viewHolderPic.commentTextView.setText(items.get(position).getComment());
                viewHolderPic.viewTextView.setText(items.get(position).getView());
                viewHolderPic.nameTextView.setText(items.get(position).getOwnerName());
                viewHolderPic.dateTextView.setText(items.get(position).getDate());
                viewHolderPic.postExpandableTextView.setText(items.get(position).getPost());
                if(items.get(position).getPeopleWhoLiked().contains(mAuth.getUid()))
                {
                    viewHolderPic.likeImageButton.setImageResource(R.drawable.ic_like_blue);
                    viewHolderPic.likeImageButton.setTag("liked");
                }
                else
                {
                    viewHolderPic.likeImageButton.setImageResource(R.drawable.ic_like_new);
                    viewHolderPic.likeImageButton.setTag("notLiked");
                }

                viewHolderPic.likeImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewHolderPic.likeImageButton.getTag().equals("liked"))
                        {
                            viewHolderPic.likeImageButton.setImageResource(R.drawable.ic_like_new);
                            viewHolderPic.likeImageButton.setTag("notLiked");
                            Log.d(TAG,items.get(position).getBlogId());
                            final DocumentReference blogDocumentReference = firebaseFirestore.document("Blog/"+items.get(position).getBlogId());
                            blogDocumentReference.update("peopleWhoLiked",FieldValue.arrayRemove(mAuth.getUid()));


                            blogDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    tempLikedCount = documentSnapshot.getString("like");
                                    blogDocumentReference.update("like",String.valueOf(Integer.parseInt(tempLikedCount)-1));

                                    viewHolderPic.likeTextView.setText(String.valueOf(Integer.parseInt(tempLikedCount)-1));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"runtime like retrieve failed");
                                }
                            });



                        }
                        else
                        {
                            viewHolderPic.likeImageButton.setImageResource(R.drawable.ic_like_blue);
                            viewHolderPic.likeImageButton.setTag("liked");
                            final DocumentReference blogDocumentReference = firebaseFirestore.document("Blog/"+items.get(position).getBlogId());
                            blogDocumentReference.update("peopleWhoLiked",FieldValue.arrayUnion(mAuth.getUid()));


                            blogDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    tempLikedCount = documentSnapshot.getString("like");
                                    blogDocumentReference.update("like",String.valueOf(Integer.parseInt(tempLikedCount)+1));

                                    viewHolderPic.likeTextView.setText(String.valueOf(Integer.parseInt(tempLikedCount)+1));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"runtime like retrieve failed");
                                }
                            });



                        }



                    }
                });
                viewHolderPic.commentImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });


                break;
            case ITEM_TYPE_WITHOUT_PIC:
                final WithoutPicViewHolder viewHolderNoPic = (WithoutPicViewHolder) holder;
                viewHolderNoPic.likeTextView.setText(items.get(position).getLike());
                viewHolderNoPic.commentTextView.setText(items.get(position).getComment());
                viewHolderNoPic.viewTextView.setText(items.get(position).getView());
                viewHolderNoPic.nameTextView.setText(items.get(position).getOwnerName());
                viewHolderNoPic.dateTextView.setText(items.get(position).getDate());
                viewHolderNoPic.postExpandableTextView.setText(items.get(position).getPost());
                if(items.get(position).getPeopleWhoLiked().contains(mAuth.getUid()))
                {
                    viewHolderNoPic.likeImageButton.setImageResource(R.drawable.ic_like_blue);
                    viewHolderNoPic.likeImageButton.setTag("liked");
                }
                else
                {
                    viewHolderNoPic.likeImageButton.setImageResource(R.drawable.ic_like_new);
                    viewHolderNoPic.likeImageButton.setTag("notLiked");
                }
                viewHolderNoPic.likeImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(viewHolderNoPic.likeImageButton.getTag().equals("liked"))
                        {
                            viewHolderNoPic.likeImageButton.setImageResource(R.drawable.ic_like_new);
                            viewHolderNoPic.likeImageButton.setTag("notLiked");
                            final DocumentReference blogDocumentReference = firebaseFirestore.document("Blog/"+items.get(position).getBlogId());
                            blogDocumentReference.update("peopleWhoLiked",FieldValue.arrayRemove(mAuth.getUid()));

                            blogDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                               tempLikedCount = documentSnapshot.getString("like");

                                    blogDocumentReference.update("like",String.valueOf(Integer.parseInt(tempLikedCount)-1));

                                    viewHolderNoPic.likeTextView.setText(String.valueOf(Integer.parseInt(tempLikedCount)-1));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"runtime like retrieve failed");
                                }
                            });


                        }
                        else
                        {
                            viewHolderNoPic.likeImageButton.setImageResource(R.drawable.ic_like_blue);
                            viewHolderNoPic.likeImageButton.setTag("liked");
                            final DocumentReference blogDocumentReference = firebaseFirestore.document("Blog/"+items.get(position).getBlogId());
                            blogDocumentReference.update("peopleWhoLiked",FieldValue.arrayUnion(mAuth.getUid()));


                            blogDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    tempLikedCount = documentSnapshot.getString("like");

                                    blogDocumentReference.update("like",String.valueOf(Integer.parseInt(tempLikedCount)+1));

                                    viewHolderNoPic.likeTextView.setText(String.valueOf(Integer.parseInt(tempLikedCount)+1));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"runtime like retrieve failed");
                                }
                            });

                        }


                    }
                });
                viewHolderNoPic.commentImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private static class WithPicViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView, nameTextView, viewTextView, likeTextView, commentTextView;
        SliderView sliderView;
        ImageButton likeImageButton, commentImageButton;
        ExpandableTextView postExpandableTextView;
        ShimmerFrameLayout shimmerFrameLayout;



        public WithPicViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textView_date);
            nameTextView = itemView.findViewById(R.id.textView_name);
            viewTextView = itemView.findViewById(R.id.textView_views);
            likeTextView = itemView.findViewById(R.id.textView_likes);
            commentTextView = itemView.findViewById(R.id.textView_comments);
            sliderView = itemView.findViewById(R.id.imageSlider);
            likeImageButton = itemView.findViewById(R.id.imageButton_like);
            commentImageButton = itemView.findViewById(R.id.imageButton_comment);


            postExpandableTextView = itemView.findViewById(R.id.expand_text_view);



        }

    }

    private static class WithoutPicViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, nameTextView, viewTextView, likeTextView, commentTextView;

        ImageButton likeImageButton, commentImageButton;

        ExpandableTextView postExpandableTextView;

        public WithoutPicViewHolder(@NonNull View itemView) {
            super(itemView);


            dateTextView = itemView.findViewById(R.id.textView_date);
            nameTextView = itemView.findViewById(R.id.textView_name);
            viewTextView = itemView.findViewById(R.id.textView_views);
            likeTextView = itemView.findViewById(R.id.textView_likes);
            commentTextView = itemView.findViewById(R.id.textView_comments);
            likeImageButton = itemView.findViewById(R.id.imageButton_like);
            commentImageButton = itemView.findViewById(R.id.imageButton_comment);

            postExpandableTextView = itemView.findViewById(R.id.expand_text_view);




        }

    }
}
