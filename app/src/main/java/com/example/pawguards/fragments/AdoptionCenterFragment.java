package com.example.pawguards.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.AdoptionPost;
import com.example.pawguards.AdoptionPostAdapter;
import com.example.pawguards.Animal;
import com.example.pawguards.HomeActivity;
import androidx.annotation.NonNull;
import com.example.pawguards.R;
import com.example.pawguards.RecyclerViewInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdoptionCenterFragment extends Fragment implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private AdoptionPostAdapter adoptionPostAdapter;
    private Button btnAddAdoptionPost;
    private List<AdoptionPost> adoptionArrayList;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adoption_center, container, false);

        // Find the RecyclerView in the layout
        recyclerView = view.findViewById(R.id.rvAdoptionCenter);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Set up the RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Retrieve adoption posts from Firestore and update UI
        adoptionArrayList = new ArrayList<>();
        retrieveAdoptionPosts();

        btnAddAdoptionPost = view.findViewById(R.id.btnCreatePost);
        btnAddAdoptionPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new PostCreationFragment());
            }
        });
        return view;
    }

     private void retrieveAdoptionPosts() {
         db.collection("Animals").get()
                 .addOnCompleteListener(task -> {
                     if (task.isSuccessful() && adoptionArrayList != null) {
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             if (!((Boolean) document.get("isAdopted"))) {
                                 System.out.println(document.getId() + " => " + document.getData());
                                 String title = document.getString("title");
                                 String description = document.getString("description");
                                 String location = document.getString("location");
                                 String animalName = document.getString("name");
                                 int animalAge = document.getLong("age").intValue();
                                 String animalType = document.getString("type");
                                 String animalGender = document.getString("gender");
                                 Boolean isAdopted = document.getBoolean("isAdopted");
                                 DocumentReference whoAdopted = document.getDocumentReference("whoAdopted");
                                 DocumentReference whoPosted = document.getDocumentReference("whoPosted");
                                 String image = document.getString("profilePicture");

                                 Animal animal = new Animal(animalName,animalAge,animalType,description, animalGender, isAdopted, whoAdopted, whoPosted, image, title, location);

                                 String availability = document.getString("availability");

                                 AdoptionPost adoptionPost = new AdoptionPost(image, title, description, location, animal, availability);
                                 adoptionArrayList.add(adoptionPost);
                             }
                         }
                         updateUI(adoptionArrayList);
                     } else {
                         Toast.makeText(getActivity(), "Error retrieving adoption posts: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
     }

     private void updateUI(List<AdoptionPost> adoptionArrayList) {
         adoptionPostAdapter = new AdoptionPostAdapter(this, adoptionArrayList);
         recyclerView.setAdapter(adoptionPostAdapter);
     }

    @Override
    public void OnItemClick(int position) {
        AdoptionPostDetailFragment adoptionPostDetailFragment = new AdoptionPostDetailFragment();
        AdoptionPost adoptionPost = adoptionArrayList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("title", adoptionPost.getTitle());
        bundle.putString("description", adoptionPost.getDescription());
        bundle.putString("location", adoptionPost.getLocation());
        bundle.putString("age", String.valueOf(adoptionPost.getAnimal().getAge()));
        bundle.putString("name", adoptionPost.getAnimal().getName());


        adoptionPostDetailFragment.setArguments(bundle);
        ((HomeActivity) getActivity()).changeFragment(adoptionPostDetailFragment);
    }
}