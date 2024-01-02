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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;
import java.util.ArrayList;

public class AdoptionPostDetailFragment extends Fragment {

    public FirebaseStorage firebaseStorage;
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


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }
        });

        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                Animal animal; //GET ANIMAL INFO
                FirebaseFirestore.getInstance().collection("Users").document(mAuth.getUid()).get().addOnSuccessListener(task -> {
                    ArrayList<Animal> adoptionsMade = (ArrayList<Animal>) task.get("adoptionsMade");
                    if (adoptionsMade == null) {
                        adoptionsMade = new ArrayList<>();
                    }

                    //Postu kaldırmak lazım vallahi çok yoruldum

                    //adoptionsMade.add(animal);

                    FirebaseFirestore.getInstance().collection("Users").document(mAuth.getUid()).update("adoptionsMade", adoptionsMade);


            });
            }
        });
        // Get data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title", "");
            String name = args.getString("name", "");
            String age = args.getString("age", "");
            String gender = args.getString("gender", "");
            String description = args.getString("description", "");
            String location = args.getString("location", "");
            String image = args.getString("image", "");


            // Set data to TextViews
            textTitle.setText(title);
            textName.setText(name);
            textAge.setText(age);
            textGender.setText(gender);
            textDescription.setText(description);
            textLocation.setText(location);

            // Set image
            firebaseStorage = FirebaseStorage.getInstance();

            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    try {

                        //get profile picture address from user object and download it
                        StorageReference picRef = firebaseStorage.getReferenceFromUrl(image);
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


        }

        return view;
    }
}
