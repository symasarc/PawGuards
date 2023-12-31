package com.example.pawguards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdoptionPostAdapter extends RecyclerView.Adapter<AdoptionPostAdapter.ViewHolderAdopt>  {

    private List<AdoptionPost> adoptionsList;

    public AdoptionPostAdapter(List<AdoptionPost> adoptionsList) {
        this.adoptionsList = adoptionsList;
    }

    public static class ViewHolderAdopt extends RecyclerView.ViewHolder {

        public ImageView imageAdopt;
        public TextView textAdoptTitle;
        public TextView textAdoptDescription;
        public TextView textAdoptLocation;
        public TextView textAvailability;

        public ViewHolderAdopt(@NonNull View itemView) {
            super(itemView);
            imageAdopt = itemView.findViewById(R.id.itemImageView);
            textAdoptTitle = itemView.findViewById(R.id.itemTitleTextView);
            textAdoptDescription = itemView.findViewById(R.id.itemDescriptionTextView);
            textAdoptLocation = itemView.findViewById(R.id.itemDistanceTextView);
            textAvailability = itemView.findViewById(R.id.avaiableForTextView);
        }
    }


    @NonNull
    @Override
    public ViewHolderAdopt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        return new ViewHolderAdopt(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdopt holder, int position) {

            AdoptionPost adoptionPost = adoptionsList.get(position);

//            holder.imageAdopt.setImageResource(Integer.parseInt(adoptionPost.getImage()));
            holder.textAdoptTitle.setText(adoptionPost.getTitle());
            holder.textAdoptDescription.setText(adoptionPost.getDescription());
            holder.textAdoptLocation.setText(adoptionPost.getLocation());
            holder.textAvailability.setText(adoptionPost.getAvailability());
    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }

}

