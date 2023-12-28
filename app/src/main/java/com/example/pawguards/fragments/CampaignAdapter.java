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

import com.example.pawguards.R;

import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private final List<Campaign> campaignsList;

    public CampaignAdapter(List<Campaign> campaignsList) {
        this.campaignsList = campaignsList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Campaign campaign = campaignsList.get(position);

        // Bind campaign data to the ViewHolder
        holder.imageCampaign.setImageResource(campaign.getImageResourceId());
        holder.textCampaignTitle.setText(campaign.getTitle());
        holder.textCampaignDescription.setText(campaign.getDescription());
        holder.textAmountRaised.setText("Amount Raised: " + campaign.getAmountRaised());

        // Set up onClickListener for the "Donate Now" button
        holder.buttonDonateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the donation button click, e.g., open a donation form
                // You can pass the campaign details to the next screen if needed
                // For simplicity, you can use a Toast for demonstration purposes
                Toast.makeText(view.getContext(), "Donate Now clicked for " + campaign.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return campaignsList.size();
    }
}
