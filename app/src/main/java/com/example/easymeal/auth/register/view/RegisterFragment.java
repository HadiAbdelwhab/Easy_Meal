package com.example.easymeal.auth.register.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";
    private TextInputEditText emailEditText, passwordEditText, nameEditText, confirmPasswordEditText;
    private Button registerButton;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseDatabase database;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setListeners(view);
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.register_progress_bar);
        emailEditText = view.findViewById(R.id.email_text_field_register);
        passwordEditText = view.findViewById(R.id.password_text_field_register);
        registerButton = view.findViewById(R.id.register_button);
        nameEditText = view.findViewById(R.id.name_text_field_register);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_text_field_register);
    }

    private void setListeners(View view) {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                registerUser(email, password, name,v);
            }
        });
    }

    private void registerUser(String email, String password, String name, View view) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            handleRegistrationSuccess(name,view);
                        } else {
                            handleRegistrationFailure(task.getException());
                        }
                    }
                });
    }

    private void handleRegistrationSuccess(String name,View view) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Account Created", Toast.LENGTH_SHORT).show();

        Navigation.findNavController(view).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            saveNameToFirebase(name, userId);
        }
    }

    private void handleRegistrationFailure(Exception exception) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Registration failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error creating user:", exception);
    }

    private void saveNameToFirebase(String name, String userId) {
        DatabaseReference reference = database.getReference("users").child(userId).child("name");
        reference.setValue(name)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Name saved to Firebase successfully!");
                        // Handle successful registration (e.g., navigate to another screen)
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error saving name to Firebase:", e);
                        // Handle name saving failure (e.g., show error message)
                    }
                });
    }


}
