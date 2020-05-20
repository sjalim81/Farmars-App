package com.example.farmersapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class otpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private EditText mOtpText;
    private Button mVerifybtn;

    private ProgressBar verifyProgressBar;
    private String mAuthVerificationId, phoneNumber;
    private CollectionReference usersCollectionRef = FirebaseFirestore.getInstance().collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mOtpText = findViewById(R.id.verificattion_code_et);
        mVerifybtn = findViewById(R.id.Button_otp);
        verifyProgressBar = findViewById(R.id.otp_progress_bar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mVerifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = mOtpText.getText().toString();
                if (otp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill ", Toast.LENGTH_LONG).show();
                } else {
                    verifyProgressBar.setVisibility(View.VISIBLE);
                    Log.d("Print", "7");

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
                    signInWithOhoneAuthCredential(credential);
                }
            }
        });


    }

    private void signInWithOhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d("Print", "8");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(otpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            registerPage();
                            numberExistenceCheck();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "Error in Verifying OTP", Toast.LENGTH_LONG).show();
                            }
                        }
                        verifyProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Print", "10");

        if (mCurrentUser != null) {
            //  senduserHome();
        }

    }

    private void senduserHome() {
        Log.d("Print", "6");
        Intent homeIntent = new Intent(otpActivity.this, ExploreActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        phoneNumber = getIntent().getExtras().getString("phone");
        homeIntent.putExtra("phone", phoneNumber);
        startActivity(homeIntent);
        finish();
    }


    private void registerPage() {
        Log.d("Print", "9");
        Intent HomeiNtent = new Intent(otpActivity.this, RegistrationActivity.class);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        phoneNumber = getIntent().getExtras().getString("phone");
        Log.d("checked","otp activity"+phoneNumber+"  ");
        HomeiNtent.putExtra("phone", phoneNumber);
        startActivity(HomeiNtent);
        finish();
    }

    private void numberExistenceCheck() {

        Log.d("cheched","start number check");

        DocumentReference docIdRef = usersCollectionRef.document(phoneNumber);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               DocumentSnapshot documentSnapshot = task.getResult();
                if(task.isSuccessful())
                {
                    senduserHome();
                    Log.d("checked",phoneNumber+ " document is found!");
                }
                else
                {
                    registerPage();
                    Log.d("checked","otp check document existence not found");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked","otp generation failed!");
            }
        });


//        usersCollectionRef.whereEqualTo("logedInPhoneNumber", phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    Log.d("checked", "number found");
//
//                    senduserHome();
//                } else {
//                    registerPage();
//                    Log.d("checked", "number not found");
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("checked", "not number found");
//            }
//        });
    }

}
