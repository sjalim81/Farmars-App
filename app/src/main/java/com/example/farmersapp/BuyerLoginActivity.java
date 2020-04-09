package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BuyerLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView phoneNumberText;
    private EditText pinNumberText;
    private TextView forgotPinText;
    private Button loginButton;
    private TextView createAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);

        phoneNumberText = findViewById(R.id.buyLogActivity_enter_phoneText);
        pinNumberText = findViewById(R.id.buyLogActivity_enter_pinText);
        forgotPinText = findViewById(R.id.buyLogActivity_forgotPin);
        loginButton = findViewById(R.id.buyLogActivity_loginButton);
        createAccountText = findViewById(R.id.buyLogActivity_createAccount);

        forgotPinText.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        createAccountText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buyLogActivity_forgotPin:
                //forgot pin code
                break;
            case R.id.buyLogActivity_loginButton:
                //login button code
                break;
            case R.id.buyLogActivity_createAccount:
                //create account code
                break;

        }
    }
}
