package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FarmerLoginActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private static final String TAG = "Printing";
    private AutoCompleteTextView phoneNumberText;
    //   private EditText pinNumberText;
//    private TextView forgotPinText;
    private Button loginButton;
//    private TextView createAccount;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks McallBack;

    private String sentVerificationId, inputOtpCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        phoneNumberText = findViewById(R.id.farLogActivity_enter_phoneText);
        //      pinNumberText = findViewById(R.id.farLogActivity_enter_pinText);
        //     forgotPinText = findViewById(R.id.farLogActivity_forgotPin);
        loginButton = findViewById(R.id.farLogActivity_loginButton);
        //      createAccount = findViewById(R.id.farLogActivity_createAccount);

        //       forgotPinText.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();



        Log.d("calls", "2");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String country_code = "+88";
                String phone_number = phoneNumberText.getText().toString().trim();
                String complete_phone_number = country_code + phone_number;
                if (phone_number.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please Fill", Toast.LENGTH_LONG).show();
                } else {
//                    mLoginProgress.setVisibility(View.VISIBLE);
                    Log.d("calls", "3");

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            FarmerLoginActivity.this,
                            McallBack
                    );
                }

            }
        });
        //      createAccount.setOnClickListener(this);




        McallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(getApplicationContext(), "verification Failed " + e, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                sentVerificationId = s;
//                Log.d("printId",s);
                Log.d("calls", "4");
               // createOTPpopupDialog();
                Intent otpIntent = new Intent(FarmerLoginActivity.this,VerificationActivity.class);
                otpIntent.putExtra("AuthCredentials",s);
                otpIntent.putExtra("phoneNumber",phoneNumberText.getText().toString());
                otpIntent.putExtra("activity","Farmer");
                startActivity(otpIntent);

            }
        };


    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential) {
        Log.d("calls", "7");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(FarmerLoginActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    registerPage();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(getApplicationContext(), "error In verifying OTP", Toast.LENGTH_LONG).show();
                                    }
                                }
                                // mLoginProgress.setVisibility(View.VISIBLE);
                            }
                        }

                );

    }

    private void registerPage() {
        Log.d("calls", "8");
        Intent registerPageTransition = new Intent(FarmerLoginActivity.this, RegistrationActivity.class);
        startActivity(registerPageTransition);
        finish();

    }


    private void createOTPpopupDialog() {
        final String pass;

        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.otp_popup, null);

        otpPassText = view.findViewById(R.id.otp_password);
        otpConfirmButton = view.findViewById(R.id.otp_button);

        pass = otpPassText.getText().toString();
        otpConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!otpPassText.getText().toString().isEmpty()) {
                    Log.d("Print", "pass is: " + otpPassText.getText().toString().trim());
                    Log.d("calls", "5");
                    Log.d("PrintId",sentVerificationId);

                    PhoneAuthCredential credential;
                    credential = PhoneAuthProvider.getCredential(sentVerificationId, pass);
                    Log.d("calls", "6");
                    signInWithPhoneAuthCredentials(credential);

                    /**
                     * Passing values from popup to new activty
                     * **/
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                            startActivity(new Intent(FarmerLoginActivity.this, RegistrationActivity.class));
//                        }
//                    },1200);

                    //Check authentication here
                } else {
                    Snackbar.make(v, "ভুল পাসওয়ার্ড! পুনরায় চেষ্টা করুন।", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("calls", "1");
        if (mCurrentUser != null) {
//            registerPage();
//            sendUserhome();
        }

    }

}
