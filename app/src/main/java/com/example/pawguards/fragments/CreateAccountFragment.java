package com.example.pawguards.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.MainActivity;
import com.example.pawguards.R;
import com.example.pawguards.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountFragment extends Fragment {


    public static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private EditText nameSignUp, surnameSignUp, emailSignUp, passwordSignUp, passConfSignUp;
    private ProgressBar progressBar;
    private TextView toLogin;
    private Button signupBtn;
    private FirebaseAuth auth;

    public CreateAccountFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("CreateAccountFragment", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener();
    }

    private void init(View view) {

        nameSignUp = view.findViewById(R.id.nameSignUp);
        surnameSignUp = view.findViewById(R.id.surnameSignUp);
        emailSignUp = view.findViewById(R.id.emailSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        passConfSignUp = view.findViewById(R.id.passConfSignUp);
        toLogin = view.findViewById(R.id.toLogin);
        signupBtn = view.findViewById(R.id.signupBtn);
        progressBar = view.findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
            getActivity().finish();
        }
    }

    private void clickListener() {

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                if (fragment instanceof CreateAccountFragment) {
                    fragmentTransaction.addToBackStack(null);
                }

                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameSignUp.getText().toString();
                String surname = surnameSignUp.getText().toString();
                String email = emailSignUp.getText().toString();
                String password = passwordSignUp.getText().toString();
                String confirmPassword = passConfSignUp.getText().toString();

                if (name.isEmpty() || name.equals(" ")) {
                    nameSignUp.setError("Please input valid name");
                    return;
                }

                if (surname.isEmpty() || surname.equals(" ")) {
                    surnameSignUp.setError("Please input valid name");
                    return;
                }

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailSignUp.setError("Please input valid email");
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    passwordSignUp.setError("Please input valid password");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    passConfSignUp.setError("Password not match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                createAccount(name, surname, email, password);

            }
        });

    }

    private void createAccount(final String name, final String surname, final String email, String password) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();

                            String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwp--EwtYaxkfsSPIpoSPucdbxAo6PancQX1gw6ETSKI6_pGNCZY4ts1N6BV5ZcN3wPbA&usqp=CAU";

                            UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder();
                            request.setDisplayName(name);
                            request.setPhotoUri(Uri.parse(image));

                            user.updateProfile(request.build());

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Email verification link send", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            uploadUser(user, name, surname, email);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error: " + exception, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void uploadUser(FirebaseUser user, String name, String surname, String email) {

        User newUser = new User("-1", name, surname, email, user.getUid(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "-1", "-1");

        Map<String, Object> map = new HashMap<>();

        map.put("name", newUser.getName());
        map.put("surname", newUser.getSurname());
        map.put("email", newUser.getEmail());
        map.put("profilePicture", newUser.getProfilePicture());
        map.put("uid", newUser.getUid());
        map.put("donationsMade", newUser.getDonationsMade());
        map.put("animalsAdopted", newUser.getAnimalsAdopted());
        map.put("adoptionPosts", newUser.getAdoptionPosts());
        map.put("country", newUser.getCountry());
        map.put("gender", newUser.getGender());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            assert getActivity() != null;
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            getActivity().finish();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}