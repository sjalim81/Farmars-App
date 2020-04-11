package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private EditText enterNameText, phoneNoText, occupationText;
    Spinner birth_dayText, birth_monthText, birth_yearText, divisionText, districtText,
                        subDistrictText, unionText, thanaText,villageText;
    private Button regConfirmButton;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        enterNameText = findViewById(R.id.regActivity_name);
        phoneNoText = findViewById(R.id.regActivity_phoneNo);
       // pinText = findViewById(R.id.regActivity_pin);
      //  confirmPinText = findViewById(R.id.regActivity_confirmPin);
        occupationText = findViewById(R.id.regActivity_occupation);
        birth_dayText = findViewById(R.id.regActivity_birth_day);
        birth_monthText = findViewById(R.id.regActivity_birth_month);
        birth_yearText = findViewById(R.id.regActivity_birth_year);
        divisionText = findViewById(R.id.regActivity_division);
        districtText = findViewById(R.id.regActivity_district);
        subDistrictText = findViewById(R.id.regActivity_subDistrict);
        unionText =findViewById(R.id.regActivity_union);
        thanaText = findViewById(R.id.regActivity_thana);
        villageText = findViewById(R.id.regActivity_village);
        regConfirmButton = findViewById(R.id.regActivity_confimButton);

        regConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
