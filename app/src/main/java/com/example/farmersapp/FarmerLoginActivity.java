package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FarmerLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Printing";
    private AutoCompleteTextView phoneNumberText;
    private EditText pinNumberText;
    private TextView forgotPinText;
    private Button loginButton;
    private TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        phoneNumberText = findViewById(R.id.farLogActivity_enter_phoneText);
        pinNumberText = findViewById(R.id.farLogActivity_enter_pinText);
        forgotPinText = findViewById(R.id.farLogActivity_forgotPin);
        loginButton = findViewById(R.id.farLogActivity_loginButton);
        createAccount = findViewById(R.id.farLogActivity_createAccount);

        forgotPinText.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        createAccount.setOnClickListener(this);

        Log.d("test", "Matcovic");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.farLogActivity_forgotPin:
                //Log.d(TAG,"INPUT PIN");
                //forgot pin code
                break;
            case R.id.farLogActivity_loginButton:
                //login button code
                break;
            case R.id.farLogActivity_createAccount:
                //Log.d(TAG,"Create Account");
                //create account code
                break;
        }

    }
}
