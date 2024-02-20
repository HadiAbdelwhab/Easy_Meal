package com.example.easymeal.auth;


import static com.example.easymeal.util.Constants.LOGIN_FRAGMENT;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.easymeal.R;
import com.example.easymeal.app_features.MainActivity;
import com.example.easymeal.auth.login.view.LoginFragment;
import com.example.easymeal.util.ConnectivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AuthenticationActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (!ConnectivityUtils.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(this, "You are on offline mode", Toast.LENGTH_SHORT).show();
            Intent offlineModeIntent = new Intent(this, MainActivity.class);
            startActivity(offlineModeIntent);
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_auht);



    }
    @Override
    public void onBackPressed() {

        if (!navController.navigateUp()) {
            super.onBackPressed();
        }
    }
}