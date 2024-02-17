package com.example.easymeal.app_features;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.easymeal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                navigateToFragment(R.id.homeFragment);
            } else if (itemId == R.id.favouriteFragment) {
                navigateToFragment(R.id.favouriteFragment);
            } else if (itemId == R.id.planFragment) {
                navigateToFragment(R.id.planFragment);
            } else if (itemId == R.id.profileFragment) {
                navigateToFragment(R.id.profileFragment);
            } else if (itemId == R.id.searchFragment) {
                navigateToFragment(R.id.searchFragment);
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {

        if (!navController.navigateUp()) {
            super.onBackPressed();
        }
    }

    private void navigateToFragment(int fragmentId) {
        if (navController.getCurrentDestination().getId() != fragmentId) {
            navController.popBackStack(R.id.homeFragment, false); // Consider the necessity based on your app's flow
            navController.navigate(fragmentId);
        }
    }

}

