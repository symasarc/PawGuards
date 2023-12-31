package com.example.pawguards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;

import com.example.pawguards.AdoptionPost;
import com.example.pawguards.Animal;
import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostCreationFragment extends Fragment {

    private View view;
    private Button btnBack;
    private Button btnCreatePost;
    private Spinner spCountry;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etAge;
    private EditText etName;
    private RadioGroup rgSpecies;
    private RadioButton rbDog, rbCat, rbBird, rbOther;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PostCreationFragment() {
        // Required empty public constructor
    }

    public void init() {


        btnBack = getView().findViewById(R.id.btnBack);
        spCountry = getView().findViewById(R.id.spCountry);
        btnCreatePost = getView().findViewById(R.id.btnCreatePost);
        etTitle = getView().findViewById(R.id.etPostTitle);
        etDescription = getView().findViewById(R.id.etDescription);
        etAge = getView().findViewById(R.id.etAge);
        etName = getView().findViewById(R.id.etPetName);
        rgSpecies = getView().findViewById(R.id.rgSpecies);
        rbDog = getView().findViewById(R.id.rbDog);
        rbCat = getView().findViewById(R.id.rbCat);
        rbBird = getView().findViewById(R.id.rbBird);
        rbOther = getView().findViewById(R.id.rbOther);



        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = rgSpecies.getCheckedRadioButtonId();
                String species = "";
                if(selectedRadioButtonId == rbDog.getId()){
                    species = "Dog";
                } else if(selectedRadioButtonId == rbCat.getId()){
                    species = "Cat";
                } else if(selectedRadioButtonId == rbBird.getId()){
                    species = "Bird";
                } else if(selectedRadioButtonId == rbOther.getId()){
                    species = "Other";
                }

                Bundle bundle = new Bundle();
                bundle.putString("title", etTitle.getText().toString());
                bundle.putString("description", etDescription.getText().toString());
                bundle.putString("location", spCountry.getSelectedItem().toString());
                bundle.putString("age", etAge.getText().toString());
                bundle.putString("name", etName.getText().toString());
                bundle.putString("species", species);
                bundle.putString("gender", "gender");
                bundle.putString("availability", "available");
                bundle.putString("image", "image");

                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment(),bundle);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.country_array, // Array resource containing country names
                R.layout.spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCountry.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }
        });


    }

    public static PostCreationFragment newInstance(String param1, String param2) {
        PostCreationFragment fragment = new PostCreationFragment();
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
        view =inflater.inflate(R.layout.fragment_post_creation, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }


}