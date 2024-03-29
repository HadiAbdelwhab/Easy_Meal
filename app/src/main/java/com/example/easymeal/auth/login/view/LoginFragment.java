package com.example.easymeal.auth.login.view;

import static com.example.easymeal.util.Constants.FAVOURITE_KEY;
import static com.example.easymeal.util.Constants.PHOTO_URL_KEY;
import static com.example.easymeal.util.Constants.PLAN_KEY;
import static com.example.easymeal.util.Constants.USER_ID_KEY;
import static com.example.easymeal.util.Constants.USER_NAME_KEY;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.R;
import com.example.easymeal.app_features.MainActivity;
import com.example.easymeal.auth.login.presenter.LoginPresenter;
import com.example.easymeal.auth.login.presenter.LoginPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.ConnectivityUtils;
import com.example.easymeal.util.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {


    private static final String TAG = "LoginFragment";
    private TextInputEditText emailEdittext, passwordEditText;
    private Button loginButton;
    private SignInButton signInButtonGoogle;
    private TextView registerTextView, joinAsGuestTextView;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private LoginPresenter presenter;
    private SharedPreferencesManager prefManager;
    private ProgressBar progressBar;
    private Context context;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        if (prefManager.isLoggedIn()) {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                retrieveFavouriteMeals(currentUser.getUid());
                retrievePlanMeals(currentUser.getUid());
                fetchAndNavigateToMain(currentUser.getUid());
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureGoogleSignIn();
        auth = FirebaseAuth.getInstance();
        initView(view);
        setListeners();


        prefManager = new SharedPreferencesManager(requireContext());

        presenter = new LoginPresenterImpl(MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(context),
                MealsLocalDataSourceImpl.getInstance(context)));



    }

    private void initView(View view) {
        registerTextView = view.findViewById(R.id.register_text_view);
        emailEdittext = view.findViewById(R.id.email_text_field);
        passwordEditText = view.findViewById(R.id.password_text_field);
        loginButton = view.findViewById(R.id.login_button);
        signInButtonGoogle = view.findViewById(R.id.login_google_button);
        progressBar = view.findViewById(R.id.register_progress_bar);
        joinAsGuestTextView = view.findViewById(R.id.join_as_guest_text_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void setListeners() {
        progressBar.setVisibility(View.GONE);
        joinAsGuestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.truncateMeals();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = emailEdittext.getText().toString();
                password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();

                                    if (user != null) {
                                        retrieveFavouriteMeals(user.getUid());
                                        fetchAndNavigateToMain(user.getUid());
                                        prefManager.setLoggedIn(true);
                                    }
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);

            }
        });

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        retrieveFavouriteMeals(user.getUid());
                        retrievePlanMeals(user.getUid());
                        prefManager.setLoggedIn(true);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(USER_NAME_KEY, user.getDisplayName());
                        intent.putExtra(PHOTO_URL_KEY,user.getPhotoUrl());
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(context, "Google Sign-In Failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                progressBar.setVisibility(View.VISIBLE);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                retrieveFavouriteMeals(account.getId());
                retrievePlanMeals(account.getId());
                firebaseAuthWithGoogle(account.getIdToken());


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void fetchAndNavigateToMain(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("name");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (context != null && snapshot.exists()) {
                    String userName = snapshot.getValue(String.class);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(USER_NAME_KEY, userName);
                    intent.putExtra(USER_ID_KEY, userId);
                    Log.i(TAG, "onDataChange: " + userId);

                    if (isAdded() && getActivity() != null) {
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if any
                Log.e(TAG, "Error fetching user's name from database:", error.toException());
            }
        });
    }


    private void retrieveFavouriteMeals(String userId) {
        DatabaseReference favouritesRef = database.getReference(FAVOURITE_KEY);

        favouritesRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        String mealId = mealSnapshot.child("mealId").getValue(String.class);
                        String mealName = mealSnapshot.child("mealName").getValue(String.class);
                        String mealImage = mealSnapshot.child("mealImage").getValue(String.class);
                        String mealInstructions=mealSnapshot.child("instructions").getValue(String.class);

                        if (mealImage == null) {
                            String mealDetailsId = mealSnapshot.getKey();
                            DataSnapshot mealDetailsSnapshot = dataSnapshot.child(mealDetailsId);
                            mealImage = mealDetailsSnapshot.child("mealImage").getValue(String.class);
                        }

                        MealDetailsResponse.MealDetails meal = new MealDetailsResponse.MealDetails(mealId, mealName, mealImage,"0",mealInstructions);

                        meal.setDatabaseKey(FAVOURITE_KEY);
                        presenter.insertMeal(meal);

                        Log.d(TAG, "Favourite Meal ID: " + mealId);
                        Log.d(TAG, "Favourite Meal Name: " + mealName);
                        Log.d(TAG, "Favourite Meal Image: " + mealImage);
                    }
                } else {
                    Log.d(TAG, "No favourite meals found for user with ID: " + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving favourite meals from Firebase: " + databaseError.getMessage());
            }
        });
    }

    private void retrievePlanMeals(String userId) {
        DatabaseReference planRef = database.getReference("plan");
        Log.i(TAG, "retrievePlanMeals: ");
        planRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        String mealId = mealSnapshot.child("mealId").getValue(String.class);
                        String mealName = mealSnapshot.child("mealName").getValue(String.class);
                        String mealImage = mealSnapshot.child("mealImage").getValue(String.class);
                        String mealDate = mealSnapshot.child("date").getValue(String.class);
                        String mealInstructions=mealSnapshot.child("instructions").getValue(String.class);

                        if (mealImage == null) {
                            String mealDetailsId = mealSnapshot.getKey();
                            DataSnapshot mealDetailsSnapshot = dataSnapshot.child(mealDetailsId);
                            mealImage = mealDetailsSnapshot.child("mealImage").getValue(String.class);
                        }

                        MealDetailsResponse.MealDetails meal = new MealDetailsResponse.MealDetails(mealId, mealName, mealImage, mealDate,mealInstructions);

                        meal.setDatabaseKey(PLAN_KEY);
                        presenter.insertMeal(meal);

                        Log.d(TAG, "PLan Meal ID: " + mealId);
                        Log.d(TAG, "Plan Meal Name: " + mealName);
                        Log.d(TAG, "Plan Meal Image: " + mealImage);
                    }
                } else {
                    Log.d(TAG, "No favourite meals found for user with ID: " + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving plan meals from Firebase: " + databaseError.getMessage());
                databaseError.toException().printStackTrace();
            }

        });
    }


}