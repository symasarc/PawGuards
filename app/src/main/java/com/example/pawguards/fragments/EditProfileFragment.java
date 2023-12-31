package com.example.pawguards.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
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
import com.example.pawguards.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutionException;

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
    //firebase database tables object
    private FirebaseAuth auth;
    private User user;
    private FirebaseFirestore db;

    

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public void init() throws ExecutionException, InterruptedException {
        etNameSurname = getView().findViewById(R.id.etNameSurname);
        etEmail = getView().findViewById(R.id.etEmail);
        etPassword = getView().findViewById(R.id.etPassword);
        spCountry = getView().findViewById(R.id.spCountry);
        ivProfilePicture = getView().findViewById(R.id.ivProfilePicture);
        btnBack = getView().findViewById(R.id.btnBack);
        btnSaveChanges = getView().findViewById(R.id.btnSaveChanges);
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.country_array, // Array resource containing country names
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spCountry.setAdapter(adapter);

        DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DocumentReference", "DocumentSnapshot data: " + document.getData());
                        User user1 = document.toObject(User.class);
                        etEmail.setText(user1.getEmail());
                        etNameSurname.setText(user1.getName() + " " + user1.getSurname());
                    } else {
                        Log.d("DocumentReference", "No such document");
                    }
                } else {
                    Log.d("DocumentReference", "get failed with ", task.getException());
                }
            }
        });
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            init();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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