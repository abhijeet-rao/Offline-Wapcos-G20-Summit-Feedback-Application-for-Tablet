package com.example.wapcosfeedback.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wapcosfeedback.MainActivity;
import com.example.wapcosfeedback.Models.Feedback;
import com.example.wapcosfeedback.R;
import com.example.wapcosfeedback.adapters.FeedbackListAdapter;
import com.example.wapcosfeedback.database.FeedbackDbHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedbackListFragment extends Fragment {
    private FeedbackListAdapter feedbackListAdapter;
    private static final int REQUEST_PERMISSIONS = 1;
    private RecyclerView feedbackRecyclerView;

    public FeedbackListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_list, container, false);

        // Request "Storage" permission if it is not granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
        // Initialize SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                feedbackListAdapter.getFilter().filter(newText);
                return true;
            }
        });


        // Initialize RecyclerView
        feedbackRecyclerView = view.findViewById(R.id.feedbackRecyclerView);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter and set it to RecyclerView
        feedbackListAdapter = new FeedbackListAdapter(new ArrayList<>());
        feedbackRecyclerView.setAdapter(feedbackListAdapter);

        // Get all the feedbacks from the database and pass them to the adapter
        FeedbackDbHelper dbHelper = new FeedbackDbHelper(getContext());
        ArrayList<Feedback> feedbackList = dbHelper.getAllFeedbacks();
        feedbackListAdapter.setFeedbackList(feedbackList);
// Initialize back button
        view.findViewById(R.id.backButton).setOnClickListener(v -> {
            // Create an intent to go back to MainActivity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            // Start the activity
            startActivity(intent);
        });

        // Initialize export button
        view.findViewById(R.id.exportButton).setOnClickListener(v -> exportToExcel(feedbackList));


        return view;


    }

    private static final int REQUEST_CODE_CREATE_DOCUMENT = 2;

    private void exportToExcel(List<Feedback> feedbackList) {
        if (feedbackList.isEmpty()) {
            Toast.makeText(getActivity(), "No data to export", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "feedbacks.csv");
        startActivityForResult(intent, REQUEST_CODE_CREATE_DOCUMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CREATE_DOCUMENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            // Get all the feedbacks from the database and pass them to the adapter
            FeedbackDbHelper dbHelper = new FeedbackDbHelper(getContext());
            ArrayList<Feedback> feedbackList = dbHelper.getAllFeedbacks();
            feedbackListAdapter.setFeedbackList(feedbackList);

            try {
                ContentResolver resolver = requireActivity().getContentResolver();
                OutputStream outputStream = resolver.openOutputStream(uri);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                writer.write("Name,Designation,Organisation,Country,Mobile,Email,Area of Interest,Remarks,Date Time\n");

                for (Feedback feedback : feedbackList) {
                    writer.write(feedback.getName());
                    writer.write(",");
                    writer.write(feedback.getDesignation());
                    writer.write(",");
                    writer.write(feedback.getOrganisation());
                    writer.write(",");
                    writer.write(feedback.getCountry());
                    writer.write(",");
                    writer.write(feedback.getMobile());
                    writer.write(",");
                    writer.write(feedback.getEmail());
                    writer.write(",");
                    writer.write(feedback.getAreaOfInterest());
                    writer.write(",");
                    writer.write(feedback.getRemarks());
                    writer.write(",");
                    writer.write(feedback.getDateTime());
                    writer.write("\n");
                }

                writer.flush();
                writer.close();

                Toast.makeText(getActivity(), "Feedbacks exported to CSV", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error exporting feedbacks to CSV", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
