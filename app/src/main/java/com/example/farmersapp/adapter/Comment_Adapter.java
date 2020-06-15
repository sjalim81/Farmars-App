package com.example.farmersapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.R;

import com.example.farmersapp.model.CommentItem;
import com.example.farmersapp.model.CommentItemDetails;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import io.reactivex.internal.queue.MpscLinkedQueue;

public class Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

//    private List<CommentItemDetails> items;
    private Context mContext;
    private CollectionReference collectionReferenceUser = FirebaseFirestore.getInstance().collection("users");
//    private CommentItem item;
    private List<Map<String,String>> items;

//    public Comment_Adapter(List<CommentItemDetails> items, Context mContext) {
//        this.items = items;
//        this.mContext = mContext;
//    }


    public Comment_Adapter(Context mContext, List<Map<String,String>> items) {
        this.mContext = mContext;
        this.items = items;
    }

    public Comment_Adapter() {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder commentViewHolder = (CommentViewHolder)holder;
//        CommentItemDetails item = items.get(position);
        commentViewHolder.nameTextView.setText(items.get(position).get("name"));
        commentViewHolder.commentExpandableTextView.setText(items.get(position).get("comment"));
        commentViewHolder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        commentViewHolder.removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView;
        TextView nameTextView;
        ImageButton editImageButton,removeImageButton;
        ExpandableTextView commentExpandableTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
             userImageView  = itemView.findViewById(R.id.imageView_user);

             nameTextView = itemView.findViewById(R.id.textView__name);
             editImageButton = itemView.findViewById(R.id.imageButton_edit_comment);
             removeImageButton = itemView.findViewById(R.id.imageButton_remove_comment);
             commentExpandableTextView =  itemView.findViewById(R.id.expand_text_view);
        }
    }


}
