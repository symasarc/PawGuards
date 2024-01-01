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
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;

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
        rgGender = getView().findViewById(R.id.rgGender);
        rbMale = getView().findViewById(R.id.rbMale);
        rbFemale = getView().findViewById(R.id.rbFemale);


        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderButtonId = rgGender.getCheckedRadioButtonId();
                String gender = "";
                if (selectedGenderButtonId == rbMale.getId()) {
                    gender = "Male";
                } else if (selectedGenderButtonId == rbFemale.getId()) {
                    gender = "Female";
                }

                int selectedRadioButtonId = rgSpecies.getCheckedRadioButtonId();
                String species = "";
                if (selectedRadioButtonId == rbDog.getId()) {
                    species = "Dog";
                } else if (selectedRadioButtonId == rbCat.getId()) {
                    species = "Cat";
                } else if (selectedRadioButtonId == rbBird.getId()) {
                    species = "Bird";
                } else if (selectedRadioButtonId == rbOther.getId()) {
                    species = "Other";
                }

                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String location = spCountry.getSelectedItem().toString();
                String age = etAge.getText().toString();
                String name = etName.getText().toString();
                String availability = "available";
                String image = "image";

                if (title.isEmpty() || title.equals(" ")) {
                    etTitle.setError("Please input valid title");
                    return;
                }

                if (name.isEmpty() || name.equals(" ")) {
                    etName.setError("Please input valid name");
                    return;
                }

                if (age.isEmpty() || age.equals(" ")) {
                    etAge.setError("Please input valid age");
                    return;
                }

                if (species.isEmpty() || species.equals(" ")) {
                    Toast.makeText(getContext(), "Please select species", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.isEmpty() || description.equals(" ")) {
                    etDescription.setError("Please input valid description");
                    return;
                }

                if (location.isEmpty() || location.equals(" ")) {
                    Toast.makeText(getContext(), "Please select location", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gender.isEmpty() || gender.equals(" ")) {
                    Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }


                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("description", description);
                bundle.putString("location", location);
                bundle.putString("age", age);
                bundle.putString("name", name);
                bundle.putString("species", species);
                bundle.putString("gender", gender);
                bundle.putString("availability", "available");
                bundle.putString("image", "image");

                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment(), bundle);
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
        view = inflater.inflate(R.layout.fragment_post_creation, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }


}