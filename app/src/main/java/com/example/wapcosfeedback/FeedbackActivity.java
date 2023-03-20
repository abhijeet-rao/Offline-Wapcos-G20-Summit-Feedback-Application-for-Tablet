package com.example.wapcosfeedback;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.wapcosfeedback.fragments.FeedbackFormFragment;
import com.example.wapcosfeedback.fragments.FeedbackListFragment;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();
        String fragmentType = intent.getStringExtra("fragment");

        if (fragmentType.equals("list")) {
            loadFeedbackListFragment();
        } else if (fragmentType.equals("form")) {
            loadFeedbackFormFragment();
        }
    }

    private void loadFeedbackListFragment() {
        FeedbackListFragment feedbackListFragment = new FeedbackListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, feedbackListFragment);
        transaction.commit();
    }

    private void loadFeedbackFormFragment() {
        FeedbackFormFragment feedbackFormFragment = new FeedbackFormFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, feedbackFormFragment);
        transaction.commit();
    }
}
