package com.example.pawguards.fragments;

// CampaignAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.DonationPost;
import com.example.pawguards.R;

import java.util.List;

public class DonationPostAdapter extends RecyclerView.Adapter<DonationPostAdapter.ViewHolder> {

    private final List<DonationPost> donationsList;

    public DonationPostAdapter(List<DonationPost> donationsList) {
        this.donationsList = donationsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageCampaign;
        public TextView textCampaignTitle;
        public TextView textCampaignDescription;
        public TextView textAmountRaised;
        public Button buttonDonateNow;

        public ViewHolder(View itemView) {
            super(itemView);
            imageCampaign = itemView.findViewById(R.id.imageCampaign);
            textCampaignTitle = itemView.findViewById(R.id.textCampaignTitle);
            textCampaignDescription = itemView.findViewById(R.id.textCampaignDescription);
            textAmountRaised = itemView.findViewById(R.id.textAmountRaised);
            buttonDonateNow = itemView.findViewById(R.id.buttonDonateNow);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DonationPost donationPost = donationsList.get(position);

        holder.imageCampaign.setImageResource(Integer.parseInt(donationPost.getImage()));
        holder.textCampaignTitle.setText(donationPost.getTitle());
        holder.textCampaignDescription.setText(donationPost.getDescription());
        holder.textAmountRaised.setText("Amount raised: " + donationPost.getRaisedAmount());

        holder.buttonDonateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Donate Now clicked for " + donationPost.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }
}
