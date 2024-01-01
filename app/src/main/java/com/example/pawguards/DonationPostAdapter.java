package com.example.pawguards;

// CampaignAdapter.java

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.fragments.DonationPostFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DonationPostAdapter extends RecyclerView.Adapter<DonationPostAdapter.ViewHolder> {
    private List<DonationPost> donationsList;

    public DonationPostAdapter(List<DonationPost> donationsList) {
        this.donationsList = donationsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageCampaign;
        public TextView textCampaignTitle;
        public TextView textCampaignDescription;
        public TextView textAmountRaised;
        public TextView textGoal;
        public Button buttonDonateNow;

        public ViewHolder(View itemView) {
            super(itemView);
            imageCampaign = itemView.findViewById(R.id.imageCampaign);
            textCampaignTitle = itemView.findViewById(R.id.textCampaignTitle);
            textCampaignDescription = itemView.findViewById(R.id.textCampaignDescription);
            textAmountRaised = itemView.findViewById(R.id.textAmountRaised);
            buttonDonateNow = itemView.findViewById(R.id.buttonDonateNow);
            textGoal = itemView.findViewById(R.id.textGoalAmount);
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

        DonationPost donationPost = donationsList.get(holder.getAdapterPosition());

//        holder.imageCampaign.setImageResource(Integer.parseInt(donationPost.getImage()));
        holder.textCampaignTitle.setText(donationPost.getTitle());
        holder.textCampaignDescription.setText(donationPost.getDescription());
        holder.textAmountRaised.setText("Amount raised: " + donationPost.getRaisedAmount());
        holder.textGoal.setText("Goal: " + donationPost.getGoalAmount());


        holder.buttonDonateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userID = auth.getCurrentUser().getUid();

                DonationPost donationPost = donationsList.get(holder.getAdapterPosition());
                Donation donation = new Donation(donationPost.getTitle(), 1);

                FirebaseFirestore.getInstance().collection("Users").document(userID).get().addOnSuccessListener(task -> {

                    ArrayList<Donation> donationsMade = (ArrayList<Donation>) task.get("donationsMade");

                    if (donationsMade == null) {
                        donationsMade = new ArrayList<>();
                    }

                    donationsMade.add(donation);

                    donationPost.setRaisedAmount(donationPost.getRaisedAmount() + 1);

                    // Update "donationsMade" field in the user document
                    FirebaseFirestore.getInstance().collection("Users").document(userID).update("donationsMade", donationsMade);

                    // Update "raisedAmount" field in the donations document
                    donationPost.getRaisedAmount();

                    FirebaseFirestore.getInstance().collection("donations").get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (DocumentSnapshot document : task1.getResult()) {
                                if (document.getString("title").equals(donationPost.getTitle())) {
                                    document.getReference().update("raisedAmount", donationPost.getRaisedAmount());
                                }
                            }
                        }
                    });

                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }

//    public List<DonationPost> update(List<DonationPost> previousList){
//        previousList = donationsList;
//        return previousList;
//    }


}
