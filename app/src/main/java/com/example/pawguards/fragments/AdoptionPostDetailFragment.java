package com.example.pawguards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pawguards.R;

public class AdoptionPostDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adoption_post_detail, container, false);

        // Find TextViews in the layout
        ImageView imageAnimal = view.findViewById(R.id.imageAnimal);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDescription = view.findViewById(R.id.textDescription);
        TextView textLocation = view.findViewById(R.id.textLocation);
        TextView textAnimalDetails = view.findViewById(R.id.textAnimalDetails);
        TextView textAvailability = view.findViewById(R.id.textAvailability);

        // Get data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title", "");
            String description = args.getString("description", "");
            String location = args.getString("location", "");
            String animalDetails = args.getString("animalDetails", "");
            String availability = args.getString("availability", "");

            // Set data to TextViews
            textTitle.setText(title);
            textDescription.setText(description);
            textLocation.setText(location);
            textAnimalDetails.setText(animalDetails);
            textAvailability.setText(availability);

        }

        return view;
    }
}
