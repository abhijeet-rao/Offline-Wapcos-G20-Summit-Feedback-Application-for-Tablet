package com.example.wapcosfeedback.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wapcosfeedback.Models.Feedback;
import com.example.wapcosfeedback.R;
import com.example.wapcosfeedback.fragments.DetailFragment;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.FeedbackViewHolder> implements Filterable {

    private static List<Feedback> feedbackList;
    private List<Feedback> feedbackListFull;
    private List<Feedback> filteredList;
    private final Filter feedbackFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Feedback> filteredList = new ArrayList<>();

            if (feedbackListFull == null) {
                feedbackListFull = new ArrayList<>(feedbackList);
            }

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(feedbackListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Feedback feedback : feedbackListFull) {
                    if (feedback.getName().toLowerCase().contains(filterPattern) || feedback.getDesignation().toLowerCase().contains(filterPattern)) {
                        filteredList.add(feedback);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.values != null) {
                feedbackList.clear();
                filteredList = (List<Feedback>) results.values;
                notifyDataSetChanged();
            }
        }
    };

    public FeedbackListAdapter(List<Feedback> feedbackList) {
        FeedbackListAdapter.feedbackList = feedbackList;
        this.filteredList = new ArrayList<>(feedbackList);
    }

    // Set the list of feedbacks
    public void setFeedbackList(List<Feedback> feedbackList) {
        FeedbackListAdapter.feedbackList = feedbackList;
        this.filteredList = new ArrayList<>(feedbackList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return feedbackFilter;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list_item, parent, false);
        return new FeedbackViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        if (position < filteredList.size()) {
            Feedback feedback = filteredList.get(position);
            holder.nameTextView.setText(feedback.getName());
            holder.designationTextView.setText(feedback.getDesignation());
            holder.serialNoTextView.setText(String.valueOf(position + 1));
        }
    }


//    @Override
//    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
//        Feedback feedback = feedbackList.get(position);
//        holder.nameTextView.setText(feedback.getName());
//        holder.designationTextView.setText(feedback.getDesignation());
//        holder.organisationTextView.setText(feedback.getOrganisation());
//        holder.countryTextView.setText(feedback.getCountry());
//        holder.mobileTextView.setText(feedback.getMobile());
//        holder.emailTextView.setText(feedback.getEmail());
//        holder.areaOfInterestTextView.setText(feedback.getAreaOfInterest());
//        holder.remarksTextView.setText(feedback.getRemarks());
//    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public Feedback getFeedback(int position) {
        return filteredList.get(position);
    }


    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        public TextView serialNoTextView;
        public TextView nameTextView;
        public TextView designationTextView;
        public TextView organisationTextView;
        public TextView countryTextView;
        public TextView mobileTextView;
        public TextView emailTextView;
        public TextView areaOfInterestTextView;
        public TextView remarksTextView;


        public FeedbackViewHolder(View view) {
            super(view);

            serialNoTextView = itemView.findViewById(R.id.serial_no_textview);
            nameTextView = view.findViewById(R.id.nameTextView);
            nameTextView.setOnClickListener(v -> {
                // Get the feedback object for this position
                Feedback feedback = getFeedback(getAdapterPosition());

                // Open the detail fragment
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, DetailFragment.newInstance(feedback));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });


            designationTextView = view.findViewById(R.id.designationTextView);
            organisationTextView = view.findViewById(R.id.organisationTextView);
            countryTextView = view.findViewById(R.id.countryTextView);
            mobileTextView = view.findViewById(R.id.mobileTextView);
            emailTextView = view.findViewById(R.id.emailTextView);
            areaOfInterestTextView = view.findViewById(R.id.areaOfInterestTextView);
            remarksTextView = view.findViewById(R.id.remarksTextView);
        }
    }
}
