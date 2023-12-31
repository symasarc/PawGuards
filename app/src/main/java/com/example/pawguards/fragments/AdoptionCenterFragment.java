package com.example.pawguards.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.pawguards.CustomListAdapter;
import com.example.pawguards.CustomListItem;
import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;

import java.util.ArrayList;
import android.widget.Filter;

public class AdoptionCenterFragment extends Fragment {

    private View view;
    private Activity activity;
    private SearchView searchView;
    private CustomListAdapter adapter;
    private ListView adoptionCenterListView;
    private Button createPostButton;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdoptionCenterFragment() {
        // Required empty public constructor
    }

    public void init() {

        // Add the necessary import statements
        activity = getActivity();
        adoptionCenterListView = view.findViewById(R.id.lvAdoptionCenter);
        fillListView();
        searchView = view.findViewById(R.id.svAdoptionCenter);
        createPostButton = view.findViewById(R.id.btnCreatePost);
        // Set up the search view
        // Add the necessary import statements

        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // POST OLUŞTURUCAK FRAGMENTE GİDER??????????????????????????????????????????????????????????????
                //((HomeActivity) activity).changeFragment(new PostCreateFragment());

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the ListView based on the search text

                // Assuming your ListView has an adapter, you can call getFilter() on it
                if (adoptionCenterListView.getAdapter() instanceof Filterable) {
                    ((Filterable) adoptionCenterListView.getAdapter()).getFilter().filter(newText);
                }

                return false;
            }
        });

        
        
    }
    public static AdoptionCenterFragment newInstance(String param1, String param2) {
        AdoptionCenterFragment fragment = new AdoptionCenterFragment();
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
        view = inflater.inflate(R.layout.fragment_adoption_center, container, false);
        init();

        return view; }


    public void fillListView() {
        // Create an ArrayList of CustomListItem objects
        ArrayList<CustomListItem> dataList = new ArrayList<>();
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save a Life"));

        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save 2a Life"));
        dataList.add(new CustomListItem(R.drawable.cat_dog_ic, "Save 3a Life"));
        // Set up the ListView and custom adapter
        adapter = new CustomListAdapter(requireContext(), dataList);
        adoptionCenterListView.setAdapter(adapter);
    }
}