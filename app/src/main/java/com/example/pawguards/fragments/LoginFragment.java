package com.example.pawguards.fragments;

import static com.example.pawguards.fragments.CreateAccountFragment.EMAIL_REGEX;

import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {


    private EditText emailLogin, passwordLogin;
    private TextView toRegister, forgotLogin;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView registerText;
    private FirebaseAuth auth;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();

    }

    private void init(View view) {

        emailLogin = view.findViewById(R.id.emailLogin);
        passwordLogin = view.findViewById(R.id.passwordLogin);
        loginBtn = view.findViewById(R.id.loginBtn);
        toRegister = view.findViewById(R.id.toRegister);
        forgotLogin = view.findViewById(R.id.forgotLogin);
        progressBar = view.findViewById(R.id.progressBar);
        registerText = view.findViewById(R.id.toRegister);

        auth = FirebaseAuth.getInstance();

    }

    private void clickListener() {

        forgotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ForgotPassFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                if (fragment instanceof LoginFragment) {
                    fragmentTransaction.addToBackStack(null);
                }

                // Passing mail to fragment
                Bundle bundle = new Bundle();
                bundle.putString("email", emailLogin.getText().toString());
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LOGINE BASINCA HOME ACTIVITYE GECIS YAPACAK
                //openHomeActivity();

                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailLogin.setError("Input valid email");
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    passwordLogin.setError("Input 6 digit valid password ");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser user = auth.getCurrentUser();

                                    if (!user.isEmailVerified()) {
                                        Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }

                                    openHomeActivity();

                                } else {
                                    String exception = "Error: " + task.getException().getMessage();
                                    Toast.makeText(getContext(), exception, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });

            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new CreateAccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                if (fragment instanceof LoginFragment) {
                    fragmentTransaction.addToBackStack(null);
                }

                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

    }

    private void openHomeActivity() {

        if (getActivity() == null)
            return;

        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
        getActivity().finish();

    }


}