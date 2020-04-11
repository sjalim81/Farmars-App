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

public class FarmerLoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        loginButton.setOnClickListener(this);
  //      createAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.farLogActivity_loginButton:
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
                    Log.d("Print","pass is: " +otpPassText.getText().toString().trim());

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
