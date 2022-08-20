package com.example.abbreviation.ui.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.abbreviation.R;
import com.example.abbreviation.databinding.FragmentSearchHistoryBinding;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class SearchHistoryFragment extends Fragment {

    FragmentSearchHistoryBinding binding;

    private ArrayAdapter<String> arrayAdapter;
    List<String> data;
    SwipeFlingAdapterView flingAdapterView;

    public SearchHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSearchHistoryBinding.inflate(getLayoutInflater());
        flingAdapterView = binding.swipe;
        //flingAdapterView = getView().findViewById(R.id.swipe);



        data = new ArrayList<>();
        data.add("java");
        data.add("python");
        data.add("php");
        data.add("html");
        data.add("kotlin");

        arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.card_items,R.id.data,data);

        flingAdapterView.setAdapter(arrayAdapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                data.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(requireContext(), "Left swipe",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(requireContext(), "Right Swipe",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_history, container, false);
        return binding.getRoot();
    }
}