package com.example.easymeal.app_features.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.app_features.search.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private CategoriesAdapter adapter;

    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private Chip categoryChip;
    private List<String> strings;
    private List<String> filteredNames;
    private TextInputEditText searchEditText;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        recyclerView=view.findViewById(R.id.search_recycler_view);
        categoryChip=view.findViewById(R.id.chip_category);
        searchEditText=view.findViewById(R.id.search_text_field);
    }
    private void filterCategories(String query) {
        Observable.create(emitter -> {
                    List<String> filteredList = strings.stream()
                            .filter(name -> name.toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());
                    emitter.onNext(filteredList);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filteredList -> {
                            filteredNames.clear();
                            filteredNames.addAll((List<String>) filteredList);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.i(TAG, "filterNames: error");
                        }
                );
    }

}