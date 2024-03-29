package com.example.easymeal.app_features.plan.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easymeal.R;
import com.example.easymeal.app_features.plan.presenter.PlanPresenter;
import com.example.easymeal.app_features.plan.presenter.PlanPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.SharedPreferencesManager;

import java.util.Calendar;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView {


    private static final String TAG = "PlanFragment";
    private PlanPresenter presenter;
    private CardView planCardView;
    private Button chosePlanMealButton;
    private TextView mealNameTextView, insructionsTextView, guestTextView;
    private ImageView mealImageView;
    private SharedPreferencesManager sharedPreferencesManager;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
        sharedPreferencesManager = new SharedPreferencesManager(requireActivity());
        if (!sharedPreferencesManager.isLoggedIn()) {
            guestTextView.setVisibility(View.VISIBLE);
            chosePlanMealButton.setVisibility(View.INVISIBLE);
        }
        presenter = new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
    }

    private void initViews(View view) {
        chosePlanMealButton = view.findViewById(R.id.show_meal_plan_button);
        planCardView = view.findViewById(R.id.plan_card);
        mealNameTextView = view.findViewById(R.id.meal_name_plan);
        mealImageView = view.findViewById(R.id.meal_image_view_plan);
        insructionsTextView = view.findViewById(R.id.instruction_text_view_plan);
        guestTextView = view.findViewById(R.id.guest_mode_text_view_plan);
    }

    @Override
    public void showPlanMeals(List<MealDetailsResponse.MealDetails> mealDetails) {
        Log.i(TAG, "showPlanMeals: " + mealDetails);
        if (mealDetails == null || mealDetails.isEmpty() || mealDetails.get(0) == null) {
            Toast.makeText(getActivity(), "No data saved for this day", Toast.LENGTH_SHORT).show();
        } else {
            planCardView.setVisibility(View.VISIBLE);
            MealDetailsResponse.MealDetails firstMeal = mealDetails.get(0);
            mealNameTextView.setText(firstMeal.getMealName());

            Glide.with(getActivity())
                    .load(firstMeal.getMealThumb())
                    .error(R.drawable.laod)
                    .placeholder(R.drawable.laod)
                    .into(mealImageView);

            String instructionsText = "Date: " + firstMeal.getPlanDate() + "\n"
                    + "Instructions\n" + firstMeal.getInstructions();
            insructionsTextView.setText(instructionsText);
        }
    }


    @Override
    public void showPlanMealsErrorMessage(String errorMessage) {
        Log.i(TAG, "showPlanMealsErrorMessage: ");
    }

    private void setListeners() {
        chosePlanMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        long minDateMillis = System.currentTimeMillis();
        long maxDateMillis = minDateMillis + (7 * 24 * 60 * 60 * 1000);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        presenter.getPlanMeals(selectedDate);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.getDatePicker().setMinDate(minDateMillis);
        datePickerDialog.getDatePicker().setMaxDate(maxDateMillis);
        datePickerDialog.show();
    }

}