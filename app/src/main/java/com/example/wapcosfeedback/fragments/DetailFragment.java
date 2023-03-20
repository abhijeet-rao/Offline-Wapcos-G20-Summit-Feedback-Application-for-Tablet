package com.example.wapcosfeedback.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wapcosfeedback.Models.Feedback;
import com.example.wapcosfeedback.R;

public class DetailFragment extends Fragment {
    private Feedback feedback;

    public static DetailFragment newInstance(Feedback feedback) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("feedback", feedback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedback = getArguments().getParcelable("feedback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Set the feedback details in the view
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView designationTextView = view.findViewById(R.id.designationTextView);
        TextView organisationTextView = view.findViewById(R.id.organisationTextView);
        TextView countryTextView = view.findViewById(R.id.countryTextView);
        TextView mobileTextView = view.findViewById(R.id.mobileTextView);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView areaOfInterestTextView = view.findViewById(R.id.areaOfInterestTextView);
        TextView remarksTextView = view.findViewById(R.id.remarksTextView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button backButton = view.findViewById(R.id.backButton);

        nameTextView.setText(feedback.getName());
        designationTextView.setText(feedback.getDesignation());
        organisationTextView.setText(feedback.getOrganisation());
        countryTextView.setText(feedback.getCountry());
        mobileTextView.setText(feedback.getMobile());
        emailTextView.setText(feedback.getEmail());
        areaOfInterestTextView.setText(feedback.getAreaOfInterest());
        remarksTextView.setText(feedback.getRemarks());

        // Set the click listener on the back button
        backButton.setOnClickListener(v -> {
            // Pop this fragment from the back stack
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}

