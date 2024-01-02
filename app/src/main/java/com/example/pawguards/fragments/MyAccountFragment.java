package com.example.pawguards.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.MainActivity;
import com.example.pawguards.R;
import com.example.pawguards.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MyAccountFragment extends Fragment {
    private final int GALLERY_REQUEST_CODE = 1000;
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
    private Uri imageURI;

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




        //---------------------------------------------------------------
        //post eklemek için açık bırakın altı!!!
        //for photos to upload in firebase storage
        //Intent iGallery = new Intent(Intent.ACTION_PICK);
        //iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
        //---------------------------------------------------------------


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
                db.collection("Users").document(auth.getCurrentUser().getUid()).update("moneyRemaining", user.getMoneyRemaining() + 100);
                Toast.makeText(getContext(), "100 TL added to your balance", Toast.LENGTH_SHORT).show();
                updateUserInfo();
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
        updateUserInfo();
        setListeners();
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
                        locationTextView.setText("Turkey " + (user.getCountry().equals("-1") ? "" : user.getCountry()));
                        emailTextView.setText(user.getEmail());
                        currentBalanceTextView.setText(user.getMoneyRemaining() + " TL");
                        donationsMadeTextView.setText((user.getDonationsMade() == null ? "0" : user.getDonationsMade().size()* 50) + " TL");
                        pawsSavedTextView.setText((user.getAnimalsAdopted() == null ? "0" : user.getAnimalsAdopted().size())+ " Paws Rescued");
                        furEverHomePostsTextView.setText( (user.getAdoptionPosts() == null ? "0" : user.getAdoptionPosts().size()) + " Paws posted on FurEverHome");

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


    private void addDonationPost(String title, String description, float raisedAmount, float goalAmount) {
        Map<String, Object> donationsMap = new HashMap<>();
        donationsMap.put("title", title);
        donationsMap.put("description", description);
        donationsMap.put("raisedAmount", raisedAmount);
        donationsMap.put("goalAmount", goalAmount);

        FirebaseFirestore.getInstance().collection("donations").add(donationsMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Donation added", Toast.LENGTH_SHORT).show();
                            // Now, you can retrieve all donations or update your UI accordingly
                            DocumentReference documentReference = task.getResult();
                            StorageReference storageRef = firebaseStorage.getReference().child("images/Donations/" + documentReference.getId());
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageURI), null, null);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = storageRef.putBytes(data);
                                //starting to upload photo...
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //getting the url of the photo and adding it to the document
                                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                documentReference.update("imageLink", uri.toString());
                                            }
                                        });
                                    }
                                });
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error adding donation: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle result of picked image
        if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            //set image to image view
            imageURI = data.getData();
            addDonationPost("2. DONATION DENEMESI!!!", "We need your help to save THIS RANDOM DOG", 15151, 99999);
        }
    }
}