package com.example.farmersapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersapp.model.CropsModel;
import com.example.farmersapp.model.CustomListItem_Cultivation;
import com.example.farmersapp.model.DiseaseCategoriesModel;
import com.example.farmersapp.model.DiseasesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import is.arontibo.library.ElasticDownloadView;

public class DataLoadActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "offlineData";
    public static final String DISEASE_CATEGORIES_MODEL = "DiseaseCategoriesModel";
    public static final String CROPS_MODEL = "CropsModel";
    public static final String DISEASES_MODEL = "DiseasesModel";
    public static final String CULTIVATION_ITEMS = "cultivationItems";

    ProgressBar progressBarDataLoad;

    ElasticDownloadView mElasticDownloadView;

    Button mButton;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceCrops = firebaseFirestore.collection("Crops");
    private CollectionReference collectionReferenceDiseases = firebaseFirestore.collection("Diseases");
    private CollectionReference collectionReferenceDiseaseCategories = firebaseFirestore.collection("Disease_Categories");

    List<CustomListItem_Cultivation> mData;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean check1, check2, check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_load);
        mElasticDownloadView = findViewById(R.id.elastic_download_view);
        mElasticDownloadView.startIntro();

        mButton = findViewById(R.id.next_button);

        mData = new ArrayList<>();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserhome();
            }
        });


        check1 = false;
        check2 = false;
        check3 = false;

        collectionReferenceCrops.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<CropsModel> data = queryDocumentSnapshots.toObjects(CropsModel.class);
                    addCrops(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check1 = true;
                    mElasticDownloadView.setProgress(33);

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });
        collectionReferenceDiseaseCategories.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<DiseaseCategoriesModel> data = queryDocumentSnapshots.toObjects(DiseaseCategoriesModel.class);
                    addDiseaseCategories(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check2 = true;
                    mElasticDownloadView.setProgress(66);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


        collectionReferenceDiseases.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<DiseasesModel> data = queryDocumentSnapshots.toObjects(DiseasesModel.class);
                    addDisease(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check3 = true;
                    mElasticDownloadView.setProgress(100);
                    mElasticDownloadView.success();


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


        if (check1 && !check2 && !check3) {
            mElasticDownloadView.setProgress(33);
        } else if (check1 && check2 && !check3) {
            mElasticDownloadView.setProgress(66);
        } else if (check1 && check2 && check3) {
            mElasticDownloadView.setProgress(100);
            mElasticDownloadView.success();
//            sendUserhome();

        }

    }

    private void addDiseaseCategories(List<DiseaseCategoriesModel> data) {

        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(DISEASE_CATEGORIES_MODEL, json);
        editor.apply();
        Log.d("dataset", "stored ok1");
    }

    private void addCrops(final List<CropsModel> data) {
        Log.d("checked", "crops here");
        for (final CropsModel crop : data) {
            Log.d("checked", "crops here1 "+crop.getNameEnglish());
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/crops/").child(crop.getNameEnglish() + ".jpg");


            try {
                final File file = File.createTempFile("image1", "jpg");
                storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                        crop.setPictureWidth(Integer.toString(bitmap.getWidth()));
//                        crop.setPictureHeight(Integer.toString(bitmap.getHeight()));
//                        int size = bitmap.getRowBytes() * bitmap.getHeight();
//                        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//                        bitmap.copyPixelsToBuffer(byteBuffer);
//                        crop.setCropPicture(byteBuffer.array());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
                        byte[] b = baos.toByteArray();
                        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                   mData.add(new CustomListItem_Cultivation(crop.getNameBangla(),crop.getNameEnglish(),imageEncoded));




//                        Log.d("datasetCrop", crop.nameBangla + " " + crop.nameEnglish + " " + crop.cropPicture + " " + crop.cropPicture.length);
                    }
                });


            } catch ( IOException e) {
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        String json= gson.toJson(mData);
        editor.putString(CULTIVATION_ITEMS,json);
        editor.apply();
//        Gson gson = new Gson();
//        String json= gson.toJson(data);
//        editor.putString(CROPS_MODEL,json);
//        editor.apply();

        Log.d("dataset", "stored ok2");
    }

    private void addDisease(List<DiseasesModel> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(DISEASES_MODEL, json);
        editor.apply();
        Log.d("dataset", "stored ok3");
    }

    private void sendUserhome() {
        Intent homeIntent = new Intent(DataLoadActivity.this, ExploreActivity.class);
        startActivity(homeIntent);
        finish();
    }


}