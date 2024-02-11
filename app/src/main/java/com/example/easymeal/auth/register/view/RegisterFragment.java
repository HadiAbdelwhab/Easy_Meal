package com.example.easymeal.auth.register.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easymeal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";
    private TextInputEditText emailEdittext, passwordEditText;
    private Button regiterButton;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar=view.findViewById(R.id.register_progress_bar);
        emailEdittext = view.findViewById(R.id.email_text_field_register);
        passwordEditText = view.findViewById(R.id.password_text_field_register);
        regiterButton = view.findViewById(R.id.register_button);
        auth = FirebaseAuth.getInstance();
        regiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = emailEdittext.getText().toString();
                password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(getActivity(), "Account Created", Toast.LENGTH_SHORT).show();


                                    // Sign in success, update UI with the signed-in user's information


                                    Log.d(TAG, "createUserWithEmail:success");


                                    FirebaseUser user = auth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    progressBar.setVisibility(View.GONE);

                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }
                            }
                        });
            }
        });

    }

}