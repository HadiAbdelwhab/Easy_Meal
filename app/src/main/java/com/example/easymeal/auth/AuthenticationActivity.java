package com.example.easymeal.auth;


import static com.example.easymeal.util.Constants.LOGIN_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.easymeal.R;
import com.example.easymeal.auth.login.view.LoginFragment;

public class AuthenticationActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_auht);

        navController = navHostFragment.getNavController();
        /*FragmentManager manager = getSfupportFragmentManager();

        if (savedInstanceState == null) {

            loginFragment = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_authentication_container, loginFragment, LOGIN_FRAGMENT);
            transaction.commit();
        } else {

            loginFragment = (LoginFragment) manager.findFragmentByTag(LOGIN_FRAGMENT);
        }*/


    }
}