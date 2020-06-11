package com.example.farmersapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmersapp.adapter.GridBlogImages_Adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewBlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewBlogFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //widgets
    private ImageButton addPictureButton;
    private Button postButton;
    private TextView nameTextView, dateTextView;
    private GridView imagesGridView;
    private EditText postEditText;

    //resources
    List<Uri> imagesLoc;
    private String ownerName;

    //constants
    private final int REQUEST_CODE_PERMISSIONS = 1;
    private final int REQUEST_CODE_READ_STORAGE = 2;
    private final String TAG = "checked";

    //Firebase
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private CollectionReference collectionReferenceBlog, collectionReferenceBlogId, collectionReferenceusers;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReferenceBlogId, documentReferenceBlog;


    private String blogId;
    private String banglaDateTime;
    private String userId;


    public AddNewBlogFragment() {
        // Required empty public constructor
    }


    public static AddNewBlogFragment newInstance(String param1, String param2) {
        AddNewBlogFragment fragment = new AddNewBlogFragment();
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


        View contentView = inflater.inflate(R.layout.fragment_add_new_blog, container, false);


        imagesGridView = contentView.findViewById(R.id.gridView_pic);
        nameTextView = contentView.findViewById(R.id.textView_name);
        dateTextView = contentView.findViewById(R.id.textView_date);
        addPictureButton = contentView.findViewById(R.id.imageButton_add_pic);
        postButton = contentView.findViewById(R.id.button_post);
        postEditText = contentView.findViewById(R.id.editText_post);
        imagesLoc = new ArrayList<>();

        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        String dateText = df.format(new Date());

        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Log.d(TAG, "user id at blog " + userId);


        banglaDateTime = banglaDateTimeMaker(dateText);
        dateTextView.setText(banglaDateTime);


        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        collectionReferenceBlog = firebaseFirestore.collection("Blog");
        collectionReferenceBlogId = firebaseFirestore.collection("BlogId");
        collectionReferenceusers = firebaseFirestore.collection("users");
        documentReferenceBlogId = collectionReferenceBlogId.document("currentId");

        setName();
        getBlogId();


        postEditText.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (postEditText.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });


        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                   askForPermission();
//               } else {
//
//               }
                showChooser();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < imagesLoc.size(); i++) {


                    final StorageReference riversRef1 = storageReference.child("blog/" + blogId + "/" + i + ".jpg");
                    UploadTask uploadTask1 = riversRef1.putFile(imagesLoc.get(i));

                    final int finalI = i;
                    uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Log.d("checked", "image " + finalI + " upload task ok");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("checked", "image upload failed 2");


                        }
                    });

                }
                storeData();


            }
        });


        return contentView;
    }

    private void storeData() {
        HashMap<String, String> dataMap = new HashMap<>();

        dataMap.put("blogId", blogId);
        dataMap.put("like", "0");
        dataMap.put("comment", "0");
        dataMap.put("view", "0");
        dataMap.put("ownerId", userId);
        dataMap.put("numberOfPhotos", String.valueOf(imagesLoc.size()));
        dataMap.put("photoUploadStatus", String.valueOf(imagesLoc.size()));
        dataMap.put("post", String.valueOf(postEditText.getText()));
        dataMap.put("date", banglaDateTime);
        dataMap.put("ownerName", ownerName);

        DocumentReference userDocRef = collectionReferenceusers.document(userId);

        userDocRef.update("blogList", FieldValue.arrayUnion(blogId));



        collectionReferenceBlog.document(blogId).set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Blog posted", Toast.LENGTH_LONG).show();
                collectionReferenceBlog.document(blogId).update("peopleWhoLiked",FieldValue.arrayUnion("testText"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Blog post failed", Toast.LENGTH_LONG).show();
            }
        });



        int tempBlogId = Integer.parseInt(blogId);

        tempBlogId++;

        collectionReferenceBlogId.document("currentId").update("id", String.valueOf(tempBlogId)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, " current id is update!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, " current id is error!");

            }
        });

    }

    private void setName() {


        collectionReferenceusers.document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ownerName = documentSnapshot.getString("name");
                nameTextView.setText(ownerName);
                Log.d(TAG, "user name retrieve success");
            }
        }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "user name retrieve error");

            }
        });
    }

    private void getBlogId() {

        documentReferenceBlogId.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                blogId = documentSnapshot.getString("id");
                Toast.makeText(getContext(), blogId + " is retrieved.", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "id retrieve error");


            }
        });

    }


    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;

                            Log.d("Uri Selected", imageUri.toString());

                            try {
                                imagesLoc.add(imageUri);
                                Log.d("checked", imagesLoc.toString());
                                GridBlogImages_Adapter mAdapter = new GridBlogImages_Adapter(imagesLoc, getContext());

                                imagesGridView.setAdapter(mAdapter);


                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.i(TAG, "Uri = " + uri.toString());

                        try {
                            imagesLoc.add(uri);
                            GridBlogImages_Adapter mAdapter = new GridBlogImages_Adapter(imagesLoc, this.getContext());
                            imagesGridView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
            }
        }
    }

    private void askForPermission() {
        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            /* Ask for permission */
            // need to request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

//                Snackbar.make(requireActivity().findViewById(android.R.id.content),
//                        "Please grant permissions to write data in sdcard",
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", ActivityCompat.requestPermissions( ,
//                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                REQUEST_CODE_PERMISSIONS)).show();
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String banglaDateTimeMaker(String dateText) {
        StringBuilder dateTime = new StringBuilder();

        String[] tokens = dateText.split(" ");


        for (int i = 0; i < tokens.length; i++) {
            StringBuilder temp = new StringBuilder();

            if (i == 1 || i == 4) {
                switch (tokens[i]) {
                    case "January":
                        temp = new StringBuilder("জানু");

                        break;
                    case "February":
                        temp = new StringBuilder("ফেব্রু");

                        break;
                    case "March":
                        temp = new StringBuilder("মার্চ");

                        break;
                    case "April":
                        temp = new StringBuilder("এপ্রিল");
                        break;
                    case "May":
                        temp = new StringBuilder("মে");
                        break;
                    case "June":
                        temp = new StringBuilder("মে");
                        break;
                    case "July":
                        temp = new StringBuilder("মে");
                        break;
                    case "August":
                        temp = new StringBuilder("মে");
                        break;
                    case "September":
                        temp = new StringBuilder("মে");
                        break;
                    case "October":
                        temp = new StringBuilder("মে");
                        break;
                    case "November":
                        temp = new StringBuilder("মে");
                        break;
                    case "December":
                        temp = new StringBuilder("মে");
                        break;
                    case "am":
                        temp = new StringBuilder("সকাল");
                        break;
                    case "pm":
                        temp = new StringBuilder("রাত");
                        break;
                }
            } else {
                temp = new StringBuilder();
                for (int j = 0; j < tokens[i].length(); j++) {
                    if ('0' <= tokens[i].charAt(j) && tokens[i].charAt(j) <= '9') {
                        if (tokens[i].charAt(j) == '0') {
                            temp.append("০");

                        } else if (tokens[i].charAt(j) == '1') {
                            temp.append("১");

                        } else if (tokens[i].charAt(j) == '2') {
                            temp.append("২");
                        } else if (tokens[i].charAt(j) == '3') {
                            temp.append("৩");
                        } else if (tokens[i].charAt(j) == '4') {
                            temp.append("৪");
                        } else if (tokens[i].charAt(j) == '5') {
                            temp.append("৫");
                        } else if (tokens[i].charAt(j) == '6') {
                            temp.append("৬");
                        } else if (tokens[i].charAt(j) == '7') {
                            temp.append("৭");
                        } else if (tokens[i].charAt(j) == '8') {
                            temp.append("৮");
                        } else if (tokens[i].charAt(j) == '9') {
                            temp.append("৯");
                        }
                    } else {
                        temp.append(":");
                    }
                }

            }
            tokens[i] = temp.toString();
        }
        for (String str : tokens) {
            dateTime.append(str).append(" ");
        }
        Log.d("checked", dateTime.toString());
        return dateTime.toString();

    }

}