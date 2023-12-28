package com.example.pawguards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pawguards.CustomListAdapter;
import com.example.pawguards.CustomListItem;
import com.example.pawguards.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    ListView newsView;
    CustomListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    public void init() {
        // Find the ListView element

        // Create an ArrayList of CustomListItem objects
        ArrayList<CustomListItem> dataList = new ArrayList<>();
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Item 1"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Item 2"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Item 3"));
        // Set up the ListView and custom adapter
        newsView = view.findViewById(R.id.newsListView);
        adapter = new CustomListAdapter(requireContext(), dataList);
        newsView.setAdapter(adapter);
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Set up the ListView and custom adapter


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        fillListView();
        return view;
    }

    public void fillListView() {
        // Create an ArrayList of CustomListItem objects
        ArrayList<CustomListItem> dataList = new ArrayList<>();
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        // Set up the ListView and custom adapter
        newsView = view.findViewById(R.id.newsListView);
        adapter = new CustomListAdapter(requireContext(), dataList);
        newsView.setAdapter(adapter);
    }
}