package com.example.pawguards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pawguards.R;

public class EditProfileFragment extends Fragment {

    private EditText etNameSurname;
    private EditText etEmail;
    private EditText etDateOfBirth;
    private EditText etPassword;
    private EditText etLocation;
    private ImageView ivProfilePicture;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public void init() {
        etNameSurname = getView().findViewById(R.id.etNameSurname);
        //etEmail = getView().findViewById(R.id.etEmail);
        //etDateOfBirth = getView().findViewById(R.id.etDateOfBirth);
        //etPassword = getView().findViewById(R.id.etPassword);
        etLocation = getView().findViewById(R.id.etLocation);
        ivProfilePicture = getView().findViewById(R.id.ivProfilePicture);
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


}