package com.example.pawguards.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.MainActivity;
import com.example.pawguards.R;
import com.example.pawguards.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;

public class MyAccountFragment extends Fragment {
    ImageView profileImage;
    Button editProfileButton;
    TextView usernameTextView;
    TextView emailTextView;
    TextView locationTextView;
    TextView currentBalanceTextView;
    TextView donationsMadeTextView;
    TextView pawsSavedTextView;
    TextView furEverHomePostsTextView;
    Button addBalanceButton;
    Button logoutButton;
    FirebaseAuth auth;
    User user;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore db;

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
        locationTextView = getView().findViewById(R.id.tvLocation);
        logoutButton = getView().findViewById(R.id.btnLogout);
        donationsMadeTextView = getView().findViewById(R.id.tvDonationsMade);
        pawsSavedTextView = getView().findViewById(R.id.tvPawsSaved);
        furEverHomePostsTextView = getView().findViewById(R.id.tvFurEverHomePosts);
        currentBalanceTextView = getView().findViewById(R.id.tvCurrentBalance);
        addBalanceButton = getView().findViewById(R.id.btnAddBalance);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
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

        addBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        //get user data from database
        DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DocumentReference", "DocumentSnapshot data: " + document.getData());
                        user = document.toObject(User.class);
                        Log.d("DocumentReference", "onComplete: " + user.toString());
                        new AsyncTask<Void, Void, Bitmap>() {
                            @Override
                            protected Bitmap doInBackground(Void... voids) {
                                try {

                                    //get profile picture address from user object and download it
                                    StorageReference picRef = firebaseStorage.getReferenceFromUrl(user.getProfilePicture());
                                    // Get the StreamDownloadTask
                                    StreamDownloadTask streamTask = picRef.getStream();

                                    // Await completion and retrieve the InputStream
                                    InputStream inputStream = Tasks.await(streamTask).getStream();
                                    return BitmapFactory.decodeStream(inputStream);
                                } catch (Exception e) {
                                    Log.e("PhotoDownload", "Error downloading image", e);
                                    return null;
                                }
                            }

                            @Override
                            protected void onPostExecute(Bitmap bitmap) {
                                if (bitmap != null) {
                                    profileImage.setImageBitmap(bitmap);
                                } else {
                                    // Handle download failure
                                }
                            }
                        }.execute();


                        usernameTextView.setText(user.getName() + " " + user.getSurname());
                        locationTextView.setText("Turkey " + user.getCountry());
                        emailTextView.setText(user.getEmail());
                        //currentBalanceTextView.setText(user.getM() + " TL");

                        Log.d("DocumentReference", "No such document");
                    }
                } else {
                    Log.d("DocumentReference", "get failed with ", task.getException());
                }
            }
        });


        //YEAH
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