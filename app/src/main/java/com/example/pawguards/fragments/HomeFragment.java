package com.example.pawguards.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pawguards.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeFragment extends Fragment {

    private View view;

    private TextView savedCountTextView;
    private ImageView heartImage;
    private GestureDetector gestureDetector;
    private Button btnEmergency;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void init() {
        heartImage = view.findViewById(R.id.heartImageView);
        btnEmergency = view.findViewById(R.id.btnEmergency);
        savedCountTextView = view.findViewById(R.id.savedCountText);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        calculateSavedCount();


        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "153";
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            }
        });
        // Initialize the GestureDetector
        gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                startHeartAnimation();
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }

        });

        // Set up onTouchListener for gesture detection
        heartImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;
    }

    public void startHeartAnimation() {
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(heartImage, "rotation", 0f, 10f, -10f, 5f, -5f, 0f);
        rotationAnim.setDuration(1000); // Set the duration of the animation in milliseconds
        rotationAnim.setRepeatCount(4); // Repeat the animation indefinitely
        rotationAnim.setRepeatMode(ObjectAnimator.REVERSE); // Reverse the animation on each iteration

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotationAnim);
        animatorSet.start();
    }

    public void calculateSavedCount() {
        AtomicInteger count = new AtomicInteger();
        db.collection("Animals").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if((boolean)document.get("isAdopted")){
                        count.getAndIncrement();
                    }
                }
                savedCountTextView.setText(String.valueOf(count.get()));
            }
        });
    }
}
