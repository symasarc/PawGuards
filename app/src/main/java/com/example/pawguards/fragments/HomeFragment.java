package com.example.pawguards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pawguards.CustomListAdapter;
import com.example.pawguards.CustomListItem;
import com.example.pawguards.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;
    private ImageView heartImage;
    private ListView newsView;
    private CustomListAdapter adapter;
    private GestureDetector gestureDetector;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    public void init() {
        newsView = view.findViewById(R.id.newsListView);
        heartImage = view.findViewById(R.id.heartImageView);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        fillListView();

        view.setClickable(true);
        return view;
    }

    public void fillListView() {
        // Create an ArrayList of CustomListItem objects
        ArrayList<CustomListItem> dataList = new ArrayList<>();
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        // Set up the ListView and custom adapter
        adapter = new CustomListAdapter(requireContext(), dataList);
        newsView.setAdapter(adapter);
    }

}