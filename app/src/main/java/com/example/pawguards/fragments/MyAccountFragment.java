package com.example.pawguards.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.MainActivity;
import com.example.pawguards.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccountFragment extends Fragment {
    ImageView profileImage;
    Button editProfileButton;
    TextView usernameTextView;
    TextView emailTextView;
    TextView phoneNumberTextView;
    TextView locationTextView;
    TextView bioTextView;
    Button logoutButton;
    FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();
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
                //logout from firebase auth
                auth.signOut();
                //Changing activity
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //Finishing activity
                getActivity().finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //bundle geliyorsa burada handle edilecek
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
        /* ?????????????????????????????????????????????????????????????????????????????????????????????????????????????
        profileImage.setImageResource();
        usernameTextView.setText();
        emailTextView.setText();
        phoneNumberTextView.setText();
        locationTextView.setText();
        bioTextView.setText();
        */
    }
}