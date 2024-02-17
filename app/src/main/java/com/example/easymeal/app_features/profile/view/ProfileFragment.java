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
import android.widget.TextView;

import com.example.easymeal.R;


public class ProfileFragment extends Fragment {

    TextView nameTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextView=view.findViewById(R.id.textView);
        String name=getActivity().getIntent().getStringExtra(USER_NAME_KEY);
        nameTextView.setText(name);
    }
}