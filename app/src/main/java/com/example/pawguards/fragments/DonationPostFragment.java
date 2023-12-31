// CampaignFragment.java
package com.example.pawguards.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.DonationPost;
import com.example.pawguards.DonationPostAdapter;
import com.example.pawguards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationPostFragment extends Fragment {
        private RecyclerView recyclerView;
        private DonationPostAdapter donationPostAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_donationpost, container, false);

            // Find the RecyclerView in the layout
            recyclerView = view.findViewById(R.id.recyclerViewCampaigns);

            // Set up the RecyclerView with a LinearLayoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            // Retrieve donation posts from Firestore and update UI
            retrieveDonationPosts();

            return view;
        }

        private void retrieveDonationPosts() {
            List<DonationPost> donationsArrayList = new ArrayList<>();
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
                                    donationsArrayList.add(donationPost);
                                }
                                // Now, you can update your UI with the list of donation posts
                                updateUI(donationsArrayList);
                            } else {
                                Toast.makeText(getActivity(), "Error retrieving donations: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        private void updateUI(List<DonationPost> donationPosts) {
            // Create and set the adapter
            donationPostAdapter = new DonationPostAdapter(donationPosts);
            recyclerView.setAdapter(donationPostAdapter);
        }

        private void addDonationPost(String title, String description, String image, float raisedAmount, float goalAmount) {
            Map<String, Object> donationsMap = new HashMap<>();
            donationsMap.put("title", title);
            donationsMap.put("description", description);
            // donationsMap.put("image", image);
            donationsMap.put("raisedAmount", raisedAmount);
            donationsMap.put("goalAmount", goalAmount);

            FirebaseFirestore.getInstance().collection("donations").add(donationsMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Donation added", Toast.LENGTH_SHORT).show();
                                // Now, you can retrieve all donations or update your UI accordingly
                                retrieveDonationPosts();
                            } else {
                                Toast.makeText(getActivity(), "Error adding donation: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }