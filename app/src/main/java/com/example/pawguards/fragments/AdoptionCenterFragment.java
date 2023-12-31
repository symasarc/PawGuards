package com.example.pawguards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.AdoptionPost;
import com.example.pawguards.AdoptionPostAdapter;
import com.example.pawguards.Animal;
import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdoptionCenterFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdoptionPostAdapter adoptionPostAdapter;
    private Button btnAddAdoptionPost;
    private List<AdoptionPost> adoptionArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adoption_center, container, false);

        // Find the RecyclerView in the layout
        recyclerView = view.findViewById(R.id.rvAdoptionCenter);

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


        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String description = getArguments().getString("description");
            String location = getArguments().getString("location");
            String animalName = getArguments().getString("name");
            String animalAge = getArguments().getString("age");
            String animalType = getArguments().getString("species");
            String availability = getArguments().getString("availability");
            String image = getArguments().getString("image");


            Animal animal = new Animal(animalName, description, Integer.parseInt(animalAge), animalType, "gender");
            addAdoptionPost(title, description, location, animal, availability, image);
        }

        return view;
    }

     private void retrieveAdoptionPosts() {
         FirebaseFirestore.getInstance().collection("adoptions").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("posts").get()
                 .addOnCompleteListener(task -> {
                     if (task.isSuccessful() && adoptionArrayList != null) {
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             System.out.println(document.getId() + " => " + document.getData());
                             String title = document.getString("title");
                             String description = document.getString("description");
                             String location = document.getString("location");
                             String animalName = document.getString("animal_name");
                             String animalDescription = document.getString("animal_description");
//                             int animalAge = Integer.parseInt(document.getString("animal_age"));
                             String animalType = document.getString("animal_type");
                             String animalGender = document.getString("animal_gender");

                             Animal animal = new Animal(animalName, animalDescription, 21, animalType, animalGender);

                             String availability = document.getString("availability");
                             //String image = document.getString("image");
                             AdoptionPost adoptionPost = new AdoptionPost(" ", title, description, location, animal, availability);
                             adoptionArrayList.add(adoptionPost);
                         }
                         updateUI(adoptionArrayList);
                     } else {
                         Toast.makeText(getActivity(), "Error retrieving adoption posts: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
     }


     private void updateUI(List<AdoptionPost> adoptionArrayList) {
         adoptionPostAdapter = new AdoptionPostAdapter(adoptionArrayList);
         recyclerView.setAdapter(adoptionPostAdapter);
     }

     public void addAdoptionPost(String title, String description, String location, Animal animal, String availability, String image) {
         FirebaseAuth auth = FirebaseAuth.getInstance();

         Map<String, Object> adoptionPost = new HashMap<>();
         adoptionPost.put("title", title);
         adoptionPost.put("description", description);
         adoptionPost.put("location", location);
         adoptionPost.put("animal_name", animal.getName());
         adoptionPost.put("animal_description", animal.getDescription());
         adoptionPost.put("animal_age", animal.getAge());
         adoptionPost.put("animal_type", animal.getType());
         adoptionPost.put("availability", availability);
         adoptionPost.put("animal_gender", animal.getGender());
         //adoptionPost.put("image", image);

         FirebaseFirestore.getInstance().collection("adoptions").document(auth.getCurrentUser().getUid()).collection("posts").add(adoptionPost)
                 .addOnCompleteListener(task -> {
                     if (task.isSuccessful()) {
                         Toast.makeText(getActivity(), "Adoption post added successfully!", Toast.LENGTH_SHORT).show();
                         AdoptionPost newAdoptionPost = new AdoptionPost(" ", title, description, location, animal, availability);
                         adoptionArrayList.add(newAdoptionPost);  // Add the new post to the list
                         updateUI(adoptionArrayList);
                     } else {
                         Toast.makeText(getActivity(), "Error adding adoption post: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
     }

}