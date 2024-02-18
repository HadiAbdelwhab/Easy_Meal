package com.example.easymeal.app_features.profile.view;

import static com.example.easymeal.util.Constants.USER_NAME_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.easymeal.R;
import com.example.easymeal.app_features.profile.presenter.ProfilePresenter;
import com.example.easymeal.app_features.profile.presenter.ProfilePresenterImpl;
import com.example.easymeal.auth.AuthenticationActivity;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;


public class ProfileFragment extends Fragment {

    private TextView nameTextView;
    private Button logoutButton;
    private ProfilePresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter=new ProfilePresenterImpl( MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
        String name=getActivity().getIntent().getStringExtra(USER_NAME_KEY);
        nameTextView.setText(name);
        setListeners();
    }

    private void setListeners() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.truncateMeals();
                Intent logoutIntent=new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(logoutIntent);
                getActivity().finish();
            }
        });
    }

    private void initViews(View view){
        nameTextView=view.findViewById(R.id.textView);
        logoutButton=view.findViewById(R.id.log_out_button);
    }
}