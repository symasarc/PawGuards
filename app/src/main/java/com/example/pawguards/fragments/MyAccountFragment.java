package com.example.pawguards.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;

public class MyAccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ImageView profileImage;
    Button editProfileButton;
    TextView usernameTextView;
    TextView emailTextView;
    TextView phoneNumberTextView;
    TextView locationTextView;
    TextView bioTextView;
    Button logoutButton;

    Activity activity;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public void init() {
        activity = getActivity();
        profileImage = getView().findViewById(R.id.ivProfilePicture);
        editProfileButton = getView().findViewById(R.id.btnEditProfile);
        usernameTextView = getView().findViewById(R.id.tvNameSurname);
        emailTextView = getView().findViewById(R.id.tvEmail);
        phoneNumberTextView = getView().findViewById(R.id.tvContact);
        locationTextView = getView().findViewById(R.id.tvLocation);
        bioTextView = getView().findViewById(R.id.tvBio);
        logoutButton = getView().findViewById(R.id.btnLogout);
    }

    public void setListeners() {
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) activity).changeFragment(new EditProfileFragment());
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: logout ????????????????????????????????????????????????????????????????????????????????????
            }
        });
    }
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListeners();
        updateUserInfo();
    }

    public void updateUserInfo() {
        /*
        profileImage.setImageResource();
        usernameTextView.setText();
        emailTextView.setText();
        phoneNumberTextView.setText();
        locationTextView.setText();
        bioTextView.setText();
        */
    }
}