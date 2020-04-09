package com.example.farmersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_as_farmer;
    private Button login_as_buyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_as_farmer = findViewById(R.id.login_as_farmer);
        login_as_buyer = findViewById(R.id.login_as_buyer);

        login_as_farmer.setOnClickListener(this);
        login_as_buyer.setOnClickListener(this);
        Log.d("che","badhon1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_as_farmer:
                startActivity(new Intent(MainActivity.this, FarmerLoginActivity.class));
                break;
            case R.id.login_as_buyer:
                startActivity(new Intent(MainActivity.this, BuyerLoginActivity.class));
                break;
        }

    }

}
