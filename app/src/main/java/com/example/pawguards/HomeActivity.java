package com.example.pawguards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    ImageView navButton1;
    ImageView navButton2;
    ImageView navButton3;
    ImageView navButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    public void init() {
        // Find the ImageView elements
        navButton1 = findViewById(R.id.navButton1);
        navButton2 = findViewById(R.id.navButton2);
        navButton3 = findViewById(R.id.navButton3);
        navButton4 = findViewById(R.id.navButton4);
        // Set click listeners for each ImageView
        navButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavClick(view);
            }
        });

        navButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavClick(view);
            }
        });

        navButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavClick(view);
            }
        });

        navButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavClick(view);
            }
        });
    }






        // Method to handle clicks on ImageView elements
    public void onNavClick(View view) {

        if(view.getId() == R.id.navButton1) {
            navButton1.setImageResource(R.drawable.ic_home_colorized_24dp);
            navButton2.setImageResource(R.drawable.ic_home_black_24dp);
            navButton3.setImageResource(R.drawable.ic_home_black_24dp);
            navButton4.setImageResource(R.drawable.ic_home_black_24dp);
        } else if(view.getId() == R.id.navButton2) {
            navButton1.setImageResource(R.drawable.ic_home_black_24dp);
            navButton2.setImageResource(R.drawable.ic_home_colorized_24dp);
            navButton3.setImageResource(R.drawable.ic_home_black_24dp);
            navButton4.setImageResource(R.drawable.ic_home_black_24dp);
        } else if(view.getId() == R.id.navButton3) {
            navButton1.setImageResource(R.drawable.ic_home_black_24dp);
            navButton2.setImageResource(R.drawable.ic_home_black_24dp);
            navButton3.setImageResource(R.drawable.ic_home_colorized_24dp);
            navButton4.setImageResource(R.drawable.ic_home_black_24dp);
        } else if(view.getId() == R.id.navButton4) {
            navButton1.setImageResource(R.drawable.ic_home_black_24dp);
            navButton2.setImageResource(R.drawable.ic_home_black_24dp);
            navButton3.setImageResource(R.drawable.ic_home_black_24dp);
            navButton4.setImageResource(R.drawable.ic_home_colorized_24dp);
        }
    }
}