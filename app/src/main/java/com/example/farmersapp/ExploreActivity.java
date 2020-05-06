package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_bottomNavigation:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.marketPlace_bottomNavigation:
                            openFragment(MarketFragment.newInstance("", ""));
                            return true;
                        case R.id.blog_bottomNavigation:
                            openFragment(BlogFragment.newInstance("", ""));
                            return true;

                        case R.id.vehicleTrack_bottomNavigation:
                            openFragment(VehicleFragment.newInstance("", ""));
                            return true;
                        case R.id.more_bottomNavigation:
                            openFragment(MoreFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}
