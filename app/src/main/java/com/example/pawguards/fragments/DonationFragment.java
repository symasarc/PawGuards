// CampaignFragment.java
package com.example.pawguards.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.R;
import com.example.pawguards.fragments.Campaign;
import com.example.pawguards.fragments.CampaignAdapter;

import java.util.ArrayList;
import java.util.List;

public class DonationFragment extends Fragment {

    private RecyclerView recyclerView;
    private CampaignAdapter campaignAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        // Find the RecyclerView in the layout
        recyclerView = view.findViewById(R.id.recyclerViewCampaigns);

        // Set up the RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Populate your campaignsList with sample data (replace this with your actual data)
        List<Campaign> campaignsList = generateSampleCampaigns();

        // Create and set the adapter
        campaignAdapter = new CampaignAdapter(campaignsList);
        recyclerView.setAdapter(campaignAdapter);

        return view;
    }

    private List<Campaign> generateSampleCampaigns() {
        List<Campaign> campaignsList = new ArrayList<>();

        campaignsList.add(new Campaign("Save the Puppies", "Help us rescue and care for puppies in need.", "$5000", R.drawable.cat_dog_ic));
        campaignsList.add(new Campaign("Support Shelter Cats", "Provide shelter and care for homeless cats.", "$3000", R.drawable.cat_dog_ic));
        // Add more campaigns as needed

        return campaignsList;
    }
}
