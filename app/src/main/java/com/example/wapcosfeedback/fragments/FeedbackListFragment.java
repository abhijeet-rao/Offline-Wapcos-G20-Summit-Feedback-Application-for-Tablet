package com.example.wapcosfeedback.fragments;

import android.Manifest;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    private void exportToExcel(List<Feedback> feedbackList) {
        // Check if permission to write to external storage is granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission to write to external storage
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return;
        }
        if (feedbackList.isEmpty()) {
            Toast.makeText(getActivity(), "No data to export", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "feedbacks.csv";
        File file = new File(requireActivity().getExternalFilesDir(null), fileName);

        try {
            FileWriter writer = new FileWriter(file);
            writer.append("Name,Designation,Organisation,Country,Mobile,Email,Area of Interest,Remarks,Date Time\\n");

            for (Feedback feedback : feedbackList) {
                writer.append(feedback.getName());
                writer.append(",");
                writer.append(feedback.getDesignation());
                writer.append(",");
                writer.append(feedback.getOrganisation());
                writer.append(",");
                writer.append(feedback.getCountry());
                writer.append(",");
                writer.append(feedback.getMobile());
                writer.append(",");
                writer.append(feedback.getEmail());
                writer.append(",");
                writer.append(feedback.getAreaOfInterest());
                writer.append(",");
                writer.append(feedback.getRemarks());
                writer.append(",");
                writer.append(feedback.getDateTime());
                writer.append("\n");
            }

            writer.flush();
            writer.close();

            Context context = getContext();
            if (context != null) {
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/csv");
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(shareIntent, "Export feedbacks to CSV"));
            } else {
                Toast.makeText(getActivity(), "Error exporting feedbacks to CSV", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error exporting feedbacks to CSV", Toast.LENGTH_SHORT).show();
        }
    }


}
