// CampaignFragment.java
package com.example.pawguards.fragments;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.Donation;
import com.example.pawguards.DonationPost;
import com.example.pawguards.DonationPostAdapter;
import com.example.pawguards.R;
import com.example.pawguards.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationPostFragment extends Fragment {
        private final int GALLERY_REQUEST_CODE = 1000;
        private RecyclerView recyclerView;
        private DonationPostAdapter donationPostAdapter;
        private List<DonationPost> donationsArrayList;
        private FirebaseStorage firebaseStorage;
        private Uri imageURI;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_donationpost, container, false);

            // Find the RecyclerView in the layout
            recyclerView = view.findViewById(R.id.recyclerViewCampaigns);

            // Set up the RecyclerView with a LinearLayoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            // Retrieve donation posts from Firestore and update UI
            firebaseStorage= FirebaseStorage.getInstance();

            //addDonationPost("Fakir Semihe Donatetion topluyoz!!!", "We need your help to save SEMIH!", -1, 10);

            retrieveDonationPosts();

            return view;
        }

        private void retrieveDonationPosts() {
            donationsArrayList = new ArrayList<>();
            FirebaseFirestore.getInstance().collection("donations").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String title = document.getString("title");
                                    String description = document.getString("description");
                                    // String image = document.getString("image");
                                    float raisedAmount = document.getLong("raisedAmount").floatValue();
                                    float goalAmount = document.getLong("goalAmount").floatValue();
                                    DonationPost donationPost = new DonationPost(title, description, "", raisedAmount, goalAmount);

                                    if(raisedAmount < goalAmount) {
                                        donationsArrayList.add(donationPost);
                                    }
                                }
                                // Now, you can update your UI with the list of donation posts
                                //donationsArrayList = donationPostAdapter.update(donationsArrayList);
                                updateUI(donationsArrayList);
                            } else {
                                Toast.makeText(getActivity(), "Error retrieving donations: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        public void updateUI(List<DonationPost> donationPosts) {
            // Create and set the adapter
            donationPostAdapter = new DonationPostAdapter(donationPosts);
            recyclerView.setAdapter(donationPostAdapter);
        }

        private void addDonationPost(String title, String description, float raisedAmount, float goalAmount) {
            Map<String, Object> donationsMap = new HashMap<>();
            donationsMap.put("title", title);
            donationsMap.put("description", description);

            //for photos to upload in firebase storage
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQUEST_CODE);

            //donationsMap.put("image", imageURI.toString());

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
                                                    documentReference.update("image", uri.toString());
                                                }
                                            });
                                        }
                                    });
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                retrieveDonationPosts();
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
            }
        }


}