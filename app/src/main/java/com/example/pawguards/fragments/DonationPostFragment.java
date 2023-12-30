// CampaignFragment.java
package com.example.pawguards.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawguards.DonationPost;
import com.example.pawguards.R;
import com.example.pawguards.Donation;

import java.util.ArrayList;
import java.util.List;

public class DonationPostFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonationPostAdapter donationPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donationpost, container, false);

        // Find the RecyclerView in the layout
        recyclerView = view.findViewById(R.id.recyclerViewCampaigns);

        // Set up the RecyclerView with a LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Populate your campaignsList with sample data (replace this with your actual data)
        List<DonationPost> donationPostList = generateSampleDonationPosts();

        // Create and set the adapter
        donationPostAdapter = new DonationPostAdapter(donationPostList);
        recyclerView.setAdapter(donationPostAdapter);

        return view;
    }

    private List<DonationPost> generateSampleDonationPosts() {
        List<DonationPost> donationsArrayList = new ArrayList<>();

        donationsArrayList.add(new DonationPost("Help us save the dogs", "We need your help to save the dogs from the streets", "drawable://" + R.drawable.cat_dog_ic, 1000));
        donationsArrayList.add(new DonationPost("Help us save the cats", "We need your help to save the cats from the streets", "drawable://" + R.drawable.cat_dog_ic, 1000));
        donationsArrayList.add(new DonationPost("Help us save the birds", "We need your help to save the birds from the streets", "drawable://" + R.drawable.cat_dog_ic, 1000));
        donationsArrayList.add(new DonationPost("Help us save the horses", "We need your help to save the horses from the streets", "drawable://" + R.drawable.cat_dog_ic, 1000));

        return donationsArrayList;

    }
}
