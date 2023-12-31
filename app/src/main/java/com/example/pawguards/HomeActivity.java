package com.example.pawguards;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pawguards.fragments.AdoptionCenterFragment;
import com.example.pawguards.fragments.DonationPostFragment;
import com.example.pawguards.fragments.HomeFragment;
import com.example.pawguards.fragments.MyAccountFragment;

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
        changeFragment(new HomeFragment());

    }

    public void init() {
        // Find the ImageView elements
        navButton1 = findViewById(R.id.navButton1);
        navButton2 = findViewById(R.id.navButton2);
        navButton3 = findViewById(R.id.navButton3);
        navButton4 = findViewById(R.id.navButton4);

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

    public void onNavClick(View view) {

        if(view.getId() == R.id.navButton1) {

            changeFragment(new HomeFragment());
            navButton1.setImageResource(R.drawable.home_selected);
            navButton2.setImageResource(R.drawable.paw);
            navButton3.setImageResource(R.drawable.heart_outline);
            navButton4.setImageResource(R.drawable.account);

        } else if(view.getId() == R.id.navButton2) {
            changeFragment(new AdoptionCenterFragment());
            navButton1.setImageResource(R.drawable.home);
            navButton2.setImageResource(R.drawable.paw_selected);
            navButton3.setImageResource(R.drawable.heart_outline);
            navButton4.setImageResource(R.drawable.account);
        } else if(view.getId() == R.id.navButton3) {
            changeFragment(new DonationPostFragment());
            navButton1.setImageResource(R.drawable.home);
            navButton2.setImageResource(R.drawable.paw);
            navButton3.setImageResource(R.drawable.heart_outline_selected);
            navButton4.setImageResource(R.drawable.account);
        } else if(view.getId() == R.id.navButton4) {
            changeFragment(new MyAccountFragment());
            navButton1.setImageResource(R.drawable.home);
            navButton2.setImageResource(R.drawable.paw);
            navButton3.setImageResource(R.drawable.heart_outline);
            navButton4.setImageResource(R.drawable.account_selected);
        }
    }
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.homeContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void changeFragment(Fragment fragment,Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.homeContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}