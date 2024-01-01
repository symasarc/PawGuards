package com.example.pawguards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;

public class AdoptionPostDetailFragment extends Fragment {

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


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
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
            String animalDetails = args.getString("animalDetails", "");
            String availability = args.getString("availability", "");
            String image = args.getString("image", "");


            // Set data to TextViews
            textTitle.setText(title);

            textDescription.setText(description);
            textLocation.setText(location);

        }

        return view;
    }
}
