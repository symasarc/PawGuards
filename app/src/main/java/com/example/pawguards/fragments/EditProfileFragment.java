package com.example.pawguards.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pawguards.HomeActivity;
import com.example.pawguards.MainActivity;
import com.example.pawguards.R;
import com.example.pawguards.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class EditProfileFragment extends Fragment {

    private final int GALLERY_REQUEST_CODE = 1000;
    private EditText etName;
    private EditText etEmail;
    private EditText etsurname;
    private Spinner spCountry;
    private ImageView ivProfilePicture;
    private Button btnBack;
    private Button chngPassword;
    private Button btnSaveChanges;
    //firebase database tables object
    private FirebaseAuth auth;
    private User user;
    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;

    

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public void init() throws ExecutionException, InterruptedException {
        etName = getView().findViewById(R.id.etName);
        etsurname = getView().findViewById(R.id.etSurname);
        etEmail = getView().findViewById(R.id.etEmail);
        spCountry = getView().findViewById(R.id.spCountry);
        ivProfilePicture = getView().findViewById(R.id.ivProfilePicture);
        btnBack = getView().findViewById(R.id.btnBack);
        btnSaveChanges = getView().findViewById(R.id.btnSaveChanges);
        chngPassword = getView().findViewById(R.id.chngPassword);
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.country_array, // Array resource containing country names
                R.layout.spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spCountry.setAdapter(adapter);

        //get user data from database
        DocumentReference docRef = db.collection("Users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DocumentReference", "DocumentSnapshot data: " + document.getData());
                        user = document.toObject(User.class);
                        new AsyncTask<Void, Void, Bitmap>() {
                            @Override
                            protected Bitmap doInBackground(Void... voids) {
                                try {

                                    //get profile picture address from user object and download it
                                    StorageReference picRef = firebaseStorage.getReferenceFromUrl(user.getProfilePicture());
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
                                    ivProfilePicture.setImageBitmap(bitmap);
                                } else {
                                    // Handle download failure
                                }
                            }
                        }.execute();

                        etEmail.setText(user.getEmail());
                        etName.setText(user.getName());
                        etsurname.setText(user.getSurname());
                        if(!user.getCountry().equals(" ")){
                            spCountry.setSelection(adapter.getPosition(user.getCountry()));
                        }
                    } else {
                        Log.d("DocumentReference", "No such document");
                    }
                } else {
                    Log.d("DocumentReference", "get failed with ", task.getException());
                }
            }
        });
    }

    public void setListeners() {

        chngPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String email = etEmail.getText().toString();
                    if(email.isEmpty()){
                        email=user.getEmail();
                    }
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity().getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                            //logout from firebase auth
                            auth.signOut();
                            //Changing activity
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            //Finishing activity
                            getActivity().finish();
                        }
                    });

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).changeFragment(new MyAccountFragment());

                //getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            init();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle result of picked image
        if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            //set image to image view
            ivProfilePicture.setImageURI(data.getData());
        }
    }

    private void saveChanges() {


        if (etName.getText().toString().isEmpty()) {
            etName.setError("Please enter your name");
            Toast.makeText(getActivity().getApplicationContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etsurname.getText().toString().isEmpty()) {
            etsurname.setError("Please enter your surname");
            Toast.makeText(getActivity().getApplicationContext(), "Surname cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Please enter your email");
            Toast.makeText(getActivity().getApplicationContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etName.getText().toString().equals(user.getName()) && etsurname.getText().toString().equals(user.getSurname())
                && etEmail.getText().toString().equals(user.getEmail()) && spCountry.getSelectedItem().toString().equals(user.getCountry())
                && ivProfilePicture.getDrawable() == null) {
            Toast.makeText(getActivity().getApplicationContext(), "No changes made", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etEmail.getText().toString().equals(user.getEmail())) {
            auth.getCurrentUser().updateEmail(etEmail.getText().toString());
            db.collection("Users").document(auth.getCurrentUser().getUid()).update("name", etEmail.getText().toString());
        }

        if (!etName.getText().toString().equals(user.getName())) {
            db.collection("Users").document(auth.getCurrentUser().getUid()).update("name", etName.getText().toString());
        }
        if (!etsurname.getText().toString().equals(user.getSurname())) {
            db.collection("Users").document(auth.getCurrentUser().getUid()).update("surname", etsurname.getText().toString());
        }
        if (!spCountry.getSelectedItem().toString().equals(user.getCountry())) {
            db.collection("Users").document(auth.getCurrentUser().getUid()).update("country", spCountry.getSelectedItem().toString());
        }


        if (ivProfilePicture.getDrawable() != null) {
            StorageReference storageRef = firebaseStorage.getReference().child("images/" + auth.getCurrentUser().getUid());

            // Get the data from an ImageView as bytes
            ivProfilePicture.setDrawingCacheEnabled(true);
            ivProfilePicture.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) ivProfilePicture.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getActivity().getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.collection("Users").document(auth.getCurrentUser().getUid()).update("profilePicture", uri.toString());
                            Toast.makeText(getActivity().getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

}