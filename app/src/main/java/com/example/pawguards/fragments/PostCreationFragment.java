package com.example.pawguards.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.pawguards.Animal;
import com.example.pawguards.HomeActivity;
import com.example.pawguards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.local.ReferenceSet;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PostCreationFragment extends Fragment {
    private final int GALLERY_REQUEST_CODE = 1000;
    private View view;
    private boolean imageRecievedFlag = false;
    private Button btnBack;
    private Button btnCreatePost;
    private Spinner spCountry;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etAge;
    private EditText etName;
    private RadioGroup rgSpecies;
    private RadioButton rbDog, rbCat, rbBird, rbOther;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private ImageView animPicture;
    private FirebaseAuth auth;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore db;
    public PostCreationFragment() {
        // Required empty public constructor
    }

    public void init() {


        btnBack = getView().findViewById(R.id.btnBack);
        spCountry = getView().findViewById(R.id.spCountry);
        btnCreatePost = getView().findViewById(R.id.btnCreatePost);
        etTitle = getView().findViewById(R.id.etPostTitle);
        etDescription = getView().findViewById(R.id.etDescription);
        etAge = getView().findViewById(R.id.etAge);
        etName = getView().findViewById(R.id.etPetName);
        rgSpecies = getView().findViewById(R.id.rgSpecies);
        rbDog = getView().findViewById(R.id.rbDog);
        rbCat = getView().findViewById(R.id.rbCat);
        rbBird = getView().findViewById(R.id.rbBird);
        rbOther = getView().findViewById(R.id.rbOther);
        rgGender = getView().findViewById(R.id.rgGender);
        rbMale = getView().findViewById(R.id.rbMale);
        rbFemale = getView().findViewById(R.id.rbFemale);
        animPicture = getView().findViewById(R.id.imageView);
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();


        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderButtonId = rgGender.getCheckedRadioButtonId();
                String gender = "";
                if (selectedGenderButtonId == rbMale.getId()) {
                    gender = "Male";
                } else if (selectedGenderButtonId == rbFemale.getId()) {
                    gender = "Female";
                }

                int selectedRadioButtonId = rgSpecies.getCheckedRadioButtonId();
                String species = "";
                if (selectedRadioButtonId == rbDog.getId()) {
                    species = "Dog";
                } else if (selectedRadioButtonId == rbCat.getId()) {
                    species = "Cat";
                } else if (selectedRadioButtonId == rbBird.getId()) {
                    species = "Bird";
                } else if (selectedRadioButtonId == rbOther.getId()) {
                    species = "Other";
                }

                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String location = spCountry.getSelectedItem().toString();
                String age = etAge.getText().toString();
                String name = etName.getText().toString();
                String availability = "available";
                String image = "image";

                if (title.isEmpty() || title.equals(" ")) {
                    etTitle.setError("Please input valid title");
                    return;
                }

                if (name.isEmpty() || name.equals(" ")) {
                    etName.setError("Please input valid name");
                    return;
                }

                if (age.isEmpty() || age.equals(" ")) {
                    etAge.setError("Please input valid age");
                    return;
                }

                if (species.isEmpty() || species.equals(" ")) {
                    Toast.makeText(getContext(), "Please select species", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.isEmpty() || description.equals(" ")) {
                    etDescription.setError("Please input valid description");
                    return;
                }

                if (location.isEmpty() || location.equals(" ")) {
                    Toast.makeText(getContext(), "Please select location", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gender.isEmpty() || gender.equals(" ")) {
                    Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(!imageRecievedFlag){
//                    Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                DocumentReference whoPosted = db.collection("users").document(auth.getCurrentUser().getUid());


                Map<String, Object> animal = new HashMap<>();
                animal.put("age", Integer.parseInt(etAge.getText().toString()));
                animal.put("animalPic", null);
                animal.put("description", etDescription.getText().toString());
                animal.put("gender", gender);
                animal.put("isAdopted", false);
                animal.put("title", etTitle.getText().toString());
                animal.put("name", etName.getText().toString());
                animal.put("type", species);
                animal.put("whoAdopted", null);
                animal.put("whoPosted", whoPosted);

                AtomicReference<String> animalID = new AtomicReference<>();

                db.collection("Animals").add(animal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        animalID.set(documentReference.getId());
                    }
                });

                //photo upload and updating user's adoptionPosts
                if (animPicture.getDrawable() != null) {
                    StorageReference storageRef = firebaseStorage.getReference().child("images/Animals/" + animalID.get());

                    // Get the data from an ImageView as bytes
                    animPicture.setDrawingCacheEnabled(true);
                    animPicture.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) animPicture.getDrawable()).getBitmap();
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
                                    //updating animal's profilePicture
                                    db.collection("Animals").document(animalID.get()).update("profilePicture", uri.toString());



                                    //updating user's adoptionPosts
                                    db.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful() && task.getResult() != null){
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                Map<String, Object> user = documentSnapshot.getData();
                                                List<DocumentReference> list = (List<DocumentReference>) user.get("adoptionPosts");
                                                list.add(db.collection("Animals").document(animalID.get()));
                                                db.collection("Users").document(auth.getCurrentUser().getUid()).update("adoptionPosts", list);
                                            }
                                        }
                                    });


                                    Toast.makeText(getActivity().getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }

                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.country_array, // Array resource containing country names
                R.layout.spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCountry.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).changeFragment(new AdoptionCenterFragment());
            }
        });

        animPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQUEST_CODE);
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_post_creation, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle result of picked image
        if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            //set image to image view
            animPicture.setImageURI(data.getData());
        }
    }


}