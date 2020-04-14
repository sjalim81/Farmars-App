package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {
    TextView nameTextView,phoneNumberTextView,jobTextView,dateOfBirthTextView,addressTextView;

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameTextView = findViewById(R.id.nameTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        jobTextView = findViewById(R.id.jobTextView);
        dateOfBirthTextView = findViewById(R.id.dateOfBirthTextButton);
        addressTextView = findViewById(R.id.dateOfBirthTextButton);




    }
}
