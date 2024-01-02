package com.example.pawguards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;
import java.util.List;

public class AdoptionPostAdapter extends RecyclerView.Adapter<AdoptionPostAdapter.ViewHolderAdopt> {

    private final RecyclerViewInterface recyclerViewInterface;
    public FirebaseStorage firebaseStorage;
    private List<AdoptionPost> adoptionsList;

    public int savedCount = 0;

    public AdoptionPostAdapter(RecyclerViewInterface recyclerViewInterface, List<AdoptionPost> adoptionsList) {
        this.adoptionsList = adoptionsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    public static class ViewHolderAdopt extends RecyclerView.ViewHolder {

        public ImageView imageAdopt;
        public TextView textAdoptTitle;
        public TextView textAdoptDescription;
        public TextView textAdoptLocation;
        public TextView textGender;
        public TextView textAge;
        public TextView textName;


        public ViewHolderAdopt(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageAdopt = itemView.findViewById(R.id.itemImageView);
            textAdoptTitle = itemView.findViewById(R.id.itemTitleTextView);
            textAdoptDescription = itemView.findViewById(R.id.itemDescriptionTextView);
            textAdoptLocation = itemView.findViewById(R.id.itemLocationTextView);
            textAge = itemView.findViewById(R.id.itemAgeTextView);
            textGender = itemView.findViewById(R.id.itemGenderTextView);
            textName = itemView.findViewById(R.id.itemNameTextView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnItemClick(position);
                        }
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public ViewHolderAdopt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoption_item, parent, false);
        return new ViewHolderAdopt(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdopt holder, int position) {

        AdoptionPost adoptionPost = adoptionsList.get(position);
        firebaseStorage = FirebaseStorage.getInstance();

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {

                    //get profile picture address from user object and download it
                    StorageReference picRef = firebaseStorage.getReferenceFromUrl(adoptionPost.getImage());
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
                    holder.imageAdopt.setImageBitmap(bitmap);
                } else {
                    // Handle download failure
                }
            }
        }.execute();



        holder.textAdoptTitle.setText(adoptionPost.getTitle());
        holder.textAdoptDescription.setText(adoptionPost.getDescription());
        holder.textAdoptLocation.setText(adoptionPost.getLocation());
        holder.textAge.setText(adoptionPost.getAnimal().getStringAge());
        holder.textGender.setText(adoptionPost.getAnimal().getGender());
        if(adoptionPost.getAnimal().getGender().toLowerCase().equals("male")){
            holder.textGender.setTextColor(0xFF3360FF);
        }else if(adoptionPost.getAnimal().getGender().toLowerCase().equals("female")){
            holder.textGender.setTextColor(0xFFFF56EE);
        }
        holder.textName.setText(adoptionPost.getAnimal().getName());



    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }


}

