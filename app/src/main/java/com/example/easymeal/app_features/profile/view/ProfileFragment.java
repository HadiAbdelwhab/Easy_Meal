package com.example.easymeal.app_features.profile.view;

import static android.view.View.GONE;
import static com.example.easymeal.util.Constants.IMAGES_BASE_URL;
import static com.example.easymeal.util.Constants.PHOTO_URL_KEY;
import static com.example.easymeal.util.Constants.USER_NAME_KEY;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easymeal.R;
import com.example.easymeal.app_features.profile.presenter.ProfilePresenter;
import com.example.easymeal.app_features.profile.presenter.ProfilePresenterImpl;
import com.example.easymeal.auth.AuthenticationActivity;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.ConnectivityUtils;
import com.example.easymeal.util.SharedPreferencesManager;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private TextView nameTextView;
    private Button logoutButton;
    private ProfilePresenter presenter;
    private SharedPreferencesManager prefManager;
    private ImageView userImage;
    private String imageURL;
    private String name;

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
        prefManager = new SharedPreferencesManager(requireContext());

        presenter = new ProfilePresenterImpl(MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));

        name = getActivity().getIntent().getStringExtra(USER_NAME_KEY);
        imageURL = getActivity().getIntent().getStringExtra(PHOTO_URL_KEY);
        Log.i(TAG, "onViewCreated: " + imageURL);
        nameTextView.setText(name);
        Glide.with(getActivity())
                .load(imageURL)
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(userImage);
        setListeners();
    }

    private void setListeners() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectivityUtils.isNetworkAvailable(getApplicationContext())) {
                    Toast.makeText(getActivity(), "You are on offline mode", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.truncateMeals();
                    prefManager.setLoggedIn(false);
                    Intent logoutIntent = new Intent(getActivity(), AuthenticationActivity.class);
                    startActivity(logoutIntent);
                    getActivity().finish();
                }
            }
        });
    }

    private void initViews(View view) {
        nameTextView = view.findViewById(R.id.user_name_text_view);
        logoutButton = view.findViewById(R.id.log_out_button);
        userImage = view.findViewById(R.id.user_image_view);
    }
}