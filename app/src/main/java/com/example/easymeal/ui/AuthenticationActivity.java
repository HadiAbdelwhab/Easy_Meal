package com.example.easymeal.ui;


import static com.example.easymeal.util.Constants.LOGIN_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.easymeal.R;
import com.example.easymeal.login.view.LoginFragment;

public class AuthenticationActivity extends AppCompatActivity {
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            loginFragment = new LoginFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_authentication_container, loginFragment, LOGIN_FRAGMENT);
            transaction.commit();
        } else {

            loginFragment = (LoginFragment) manager.findFragmentByTag(LOGIN_FRAGMENT);
        }


    }
}