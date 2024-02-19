package com.example.easymeal.app_features.favourite.view;

import static com.example.easymeal.util.Constants.USER_ID_KEY;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easymeal.R;
import com.example.easymeal.app_features.MainActivity;
import com.example.easymeal.app_features.favourite.presenter.FavouritePresenter;
import com.example.easymeal.app_features.favourite.presenter.FavouritePresenterImpl;
import com.example.easymeal.app_features.favourite.view.adapters.FavouriteMealsAdapter;
import com.example.easymeal.app_features.favourite.view.adapters.OnDeleteMealListener;
import com.example.easymeal.app_features.favourite.view.adapters.OnItemClickListener;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.ConnectivityUtils;
import com.example.easymeal.util.SharedPreferencesManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView, OnDeleteMealListener, OnItemClickListener {

    private static final String TAG = "FavouriteFragment";
    private RecyclerView favouriteMealRecyclerView;
    private TextView guestTextView;
    private FavouritePresenter presenter;
    private FavouriteMealsAdapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        sharedPreferencesManager = new SharedPreferencesManager(requireActivity());
        presenter = new FavouritePresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
        presenter.getFavouriteMeals();

        if (!sharedPreferencesManager.isLoggedIn()) {
            favouriteMealRecyclerView.setVisibility(View.GONE);
            guestTextView.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        favouriteMealRecyclerView = view.findViewById(R.id.favourite_meals_recycler_view);
        guestTextView = view.findViewById(R.id.guest_mode_text_view_favourite);
    }

    private void setFavouriteMealRecyclerView() {
        favouriteMealRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        favouriteMealRecyclerView.setLayoutManager(manager);
    }


    @Override
    public void showFavouriteMeals(List<MealDetailsResponse.MealDetails> mealDetails) {
        setFavouriteMealRecyclerView();
        adapter = new FavouriteMealsAdapter(mealDetails, getActivity(),
                this,this);
        favouriteMealRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showFavouriteMealsErrorMessage(String errorMessage) {

    }

    @Override
    public void onClick(MealDetailsResponse.MealDetails mealDetails) {
        presenter.deleteFavouriteMeal(mealDetails);
        adapter.notifyDataSetChanged();
        deleteFavouriteMeal(mealDetails.getIdMeal(),
                getActivity().getIntent().getStringExtra(USER_ID_KEY));
    }

    private void deleteFavouriteMeal(String mealId, String userId) {
        DatabaseReference favouritesRef = database.getReference("favourites");

        favouritesRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        String currentMealId = mealSnapshot.child("mealId").getValue(String.class);

                        if (mealId.equals(currentMealId)) {
                            String mealDetailsId = mealSnapshot.getKey();
                            favouritesRef.child(mealDetailsId).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Meal deleted from Firebase successfully!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "Error deleting meal from Firebase:", e);
                                        }
                                    });
                            return;
                        }
                    }

                    // If you reach here, the meal with the specified ID was not found
                    Log.d(TAG, "Meal with ID " + mealId + " not found for user with ID: " + userId);
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

    @Override
    public void onClick(View view, String mealId) {
        Log.i(TAG, "onClick: "+mealId);
        if (ConnectivityUtils.isNetworkAvailable(getApplicationContext())) {
            FavouriteFragmentDirections.ActionFavouriteFragmentToMealDetailsFragment action=
                    FavouriteFragmentDirections.actionFavouriteFragmentToMealDetailsFragment(mealId);
            Navigation.findNavController(view).navigate(action);
        } else {
            Toast.makeText(getActivity(), "You should be online to show the details", Toast.LENGTH_SHORT).show();
        }

    }
}