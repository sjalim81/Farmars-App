package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class BuyerLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private AutoCompleteTextView phoneNumberText;
//    private EditText pinNumberText;
//    private TextView forgotPinText;
    private Button loginButton;
 //   private TextView createAccountText;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);

        phoneNumberText = findViewById(R.id.buyLogActivity_enter_phoneText);
   //     pinNumberText = findViewById(R.id.buyLogActivity_enter_pinText);
  //      forgotPinText = findViewById(R.id.buyLogActivity_forgotPin);
        loginButton = findViewById(R.id.buyLogActivity_loginButton);
   //     createAccountText = findViewById(R.id.buyLogActivity_createAccount);

    //    forgotPinText.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    //    createAccountText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buyLogActivity_loginButton:
                //login button code
                createOTPpopupDialog();
                break;
        }
    }

    private void createOTPpopupDialog() {
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.otp_popup, null);

        otpPassText = view.findViewById(R.id.otp_password);
        otpConfirmButton = view.findViewById(R.id.otp_button);
        otpConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!otpPassText.getText().toString().isEmpty()) {

                    /**
                     * Passing values from popup to new activty
                     * **/
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();startActivity(new Intent(BuyerLoginActivity.this, RegistrationActivity.class));
//                        }
//                    },1200);

                    //Check authentication here

                    Log.d("Print","pass is: " +otpPassText.getText().toString().trim());
                }
                else {
                    Snackbar.make(v,"ভুল পাসওয়ার্ড! পুনরায় চেষ্টা করুন।", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
}
