package com.example.pawguards.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;
import androidx.annotation.Nullable;

public class EditProfileFragment extends Fragment {

    private final int GALLERY_REQUEST_CODE = 1000;
    private EditText etNameSurname;
    private EditText etEmail;
    private EditText etDateOfBirth;
    private EditText etPassword;
    private Spinner spCountry;
    private ImageView ivProfilePicture;
    private Button btnBack;
    private Button btnSaveChanges;
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
        etEmail = getView().findViewById(R.id.etEmail);
        etDateOfBirth = getView().findViewById(R.id.etDateOfBirth);
        etPassword = getView().findViewById(R.id.etPassword);
        spCountry = getView().findViewById(R.id.spCountry);
        ivProfilePicture = getView().findViewById(R.id.ivProfilePicture);
        btnBack = getView().findViewById(R.id.btnBack);
        btnSaveChanges = getView().findViewById(R.id.btnSaveChanges);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.country_array, // Array resource containing country names
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spCountry.setAdapter(adapter);
    }

    public void setListeners() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).changeFragment(new MyAccountFragment());

                //getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

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
        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle result of picked image
        if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            //set image to image view
            ivProfilePicture.setImageURI(data.getData());
        }
    }

    private void saveChanges() {

        //etNameSurname.getText()
        //etEmail.getText()
        //etPassword.getText()
        //etDateOfBirth.getText()
        //spCountry.getSelectedItem()
        //ivProfilePicture.getDrawable()

        //save to firebase


        ((HomeActivity) getActivity()).changeFragment(new MyAccountFragment());
    }


}