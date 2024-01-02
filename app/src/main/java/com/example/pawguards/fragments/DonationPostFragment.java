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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.AdoptionPost;
import com.example.pawguards.Donation;
import com.example.pawguards.DonationPost;
import com.example.pawguards.DonationPostAdapter;
import com.example.pawguards.R;
import com.example.pawguards.RecyclerViewInterface;
import com.example.pawguards.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationPostFragment extends Fragment {

        private RecyclerView recyclerView;
        private TextView wallet;
        private DonationPostAdapter donationPostAdapter;
        private List<DonationPost> donationsArrayList;
        private FirebaseStorage firebaseStorage;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_donationpost, container, false);

            // Find the RecyclerView in the layout
            recyclerView = view.findViewById(R.id.recyclerViewCampaigns);
            wallet = view.findViewById(R.id.tvWallet);

            // Set up the RecyclerView with a LinearLayoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            // Retrieve donation posts from Firestore and update UI
            firebaseStorage= FirebaseStorage.getInstance();



            //post eklemek istiyorsanız alttakini açın üstü commentlayın!!!!
            retrieveDonationPosts();
            updateWallet();


            return view;
        }

        private void updateWallet() {
            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            User user = task.getResult().toObject(User.class);
                            wallet.setText(user.getMoneyRemaining() + " TL");
                        }
                    });
        }

        private void retrieveDonationPosts() {
            donationsArrayList = new ArrayList<>();
            FirebaseFirestore.getInstance().collection("donations").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //document to donation post
                                    DonationPost adPost = document.toObject(DonationPost.class);
                                    if(adPost.getRaisedAmount() < adPost.getGoalAmount()) {
                                        donationsArrayList.add(adPost);
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
}