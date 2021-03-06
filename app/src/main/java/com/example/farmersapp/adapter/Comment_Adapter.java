package com.example.farmersapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.farmersapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Map<String, String>> items;
    private CollectionReference collectionReferenceComment;

    public static final String TAG = "checked";


    public Comment_Adapter(Context mContext, List<Map<String, String>> items) {
        this.mContext = mContext;
        this.items = items;
        collectionReferenceComment = FirebaseFirestore.getInstance().collection("Comment");
    }

    public Comment_Adapter() {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.nameTextView.setText(items.get(position).get("name"));
        commentViewHolder.commentExpandableTextView.setText(items.get(position).get("comment"));
        commentViewHolder.commentEditText.setVisibility(View.GONE);

        commentViewHolder.saveCancelButtonsLayout.setVisibility(View.GONE);

        commentViewHolder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentViewHolder.commentExpandableTextView.setVisibility(View.GONE);
                commentViewHolder.editRemoveButtonsLayout.setVisibility(View.GONE);

                commentViewHolder.commentEditText.setVisibility(View.VISIBLE);
                commentViewHolder.saveCancelButtonsLayout.setVisibility(View.VISIBLE);

                commentViewHolder.commentEditText.setText(commentViewHolder.commentExpandableTextView.getText());


            }
        });
        commentViewHolder.removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    removeAt(position);




            }
        });

        commentViewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentViewHolder.commentExpandableTextView.setVisibility(View.VISIBLE);
                commentViewHolder.editRemoveButtonsLayout.setVisibility(View.VISIBLE);

                commentViewHolder.commentEditText.setVisibility(View.GONE);
                commentViewHolder.saveCancelButtonsLayout.setVisibility(View.GONE);

            }
        });

        commentViewHolder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,items.get(position).get("blogId"));



                        commentViewHolder.commentExpandableTextView.setVisibility(View.VISIBLE);
                        commentViewHolder.editRemoveButtonsLayout.setVisibility(View.VISIBLE);

                        commentViewHolder.commentEditText.setVisibility(View.GONE);
                        commentViewHolder.saveCancelButtonsLayout.setVisibility(View.GONE);
                        commentViewHolder.commentExpandableTextView.setText(commentViewHolder.commentEditText.getText().toString());


                        items.get(position).put("comment",commentViewHolder.commentEditText.getText().toString());
                        Map<String,Object> tempDataComment = new HashMap<>();
                        tempDataComment.put("commentList",items);
                        collectionReferenceComment.document(items.get(position).get("blogId")).set(tempDataComment);





                        Toast.makeText(commentViewHolder.saveButton.getContext(), "Comment updated.", Toast.LENGTH_SHORT).show();

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
        ImageButton editImageButton, removeImageButton;
        ExpandableTextView commentExpandableTextView;
        Button cancelButton, saveButton;
        EditText commentEditText;
        LinearLayout saveCancelButtonsLayout, editRemoveButtonsLayout;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.imageView_user);

            nameTextView = itemView.findViewById(R.id.textView__name);
            editImageButton = itemView.findViewById(R.id.imageButton_edit_comment);
            removeImageButton = itemView.findViewById(R.id.imageButton_remove_comment);
            commentExpandableTextView = itemView.findViewById(R.id.expand_text_view);
            cancelButton = itemView.findViewById(R.id.button_cancel);
            saveButton = itemView.findViewById(R.id.button_save);
            commentEditText = itemView.findViewById(R.id.editText_comment);
            saveCancelButtonsLayout = itemView.findViewById(R.id.linearLayout_cancel_save_buttons);
            editRemoveButtonsLayout = itemView.findViewById(R.id.linearLayout_edit_remove_buttons);
        }
    }

    public void removeAt(int position) {
        String blogId = items.get(position).get("blogId");
        items.remove(position);

        Map<String,Object> tempDataComment = new HashMap<>();
        tempDataComment.put("commentList",items);
        collectionReferenceComment.document(blogId).set(tempDataComment);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

}
