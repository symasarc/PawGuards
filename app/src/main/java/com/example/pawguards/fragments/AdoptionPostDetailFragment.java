package com.example.pawguards.fragments;

import android.app.admin.SecurityLog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pawguards.Animal;
import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;
import java.util.ArrayList;

public class AdoptionPostDetailFragment extends Fragment {

    public FirebaseStorage firebaseStorage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Animal animal;
    private String animalID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adoption_post_detail, container, false);

        // Find TextViews in the layout
        ImageView imageAnimal = view.findViewById(R.id.imageAnimal);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textName = view.findViewById(R.id.textName);
        TextView textAge = view.findViewById(R.id.textAge);
        TextView textGender = view.findViewById(R.id.textGender);
        TextView textDescription = view.findViewById(R.id.textDescription);
        TextView textLocation = view.findViewById(R.id.textLocation);
        Button btnBack = view.findViewById(R.id.buttonBack);
        Button adoptButton = view.findViewById(R.id.buttonAdopt);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }
        });


        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").document(mAuth.getCurrentUser().getUid()).
                        update("animalsAdopted", FieldValue.
                                arrayUnion(db.collection("Animals").document(animalID)));

                db.collection("Animals").document(animal.getAnimalRef().getId()).update("isAdopted", true);
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }

        });

        // Get data from arguments
        Bundle args = getArguments();
        if (args != null) {

            db.collection("Animals").document(args.getString("animalRef")).get().addOnSuccessListener(task -> {
                Animal an = task.toObject(Animal.class);
                animalID= task.getId();
                this.animal = an;
                textTitle.setText(an.getTitle());
                textName.setText(an.getName());
                textAge.setText(""+an.getAge());
                textGender.setText(an.getGender());
                textDescription.setText(an.getDescription());
                textLocation.setText(an.getLocation());

                if(an.getGender().toLowerCase().equals("male")){
                    textGender.setTextColor(0xFF3360FF);
                }else if(an.getGender().toLowerCase().equals("female")){
                    textGender.setTextColor(0xFFFF56EE);
                }

                // Set image
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        try {

                            //get profile picture address from user object and download it
                            StorageReference picRef = firebaseStorage.getReferenceFromUrl(an.getAnimalPic());
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
                            imageAnimal.setImageBitmap(bitmap);
                        } else {
                            // Handle download failure
                        }
                    }
                }.execute();
            });
        }
        return view;
    }
}
