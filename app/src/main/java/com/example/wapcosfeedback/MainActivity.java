package com.example.wapcosfeedback;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wapcosfeedback.adapters.ImagePagerAdapter;

public class MainActivity extends AppCompatActivity {

    private CardView feedbackListCardView;
    private CardView feedbackFormCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Hide the title bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Set the activity to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Check the orientation of the device
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main);
        }

        feedbackListCardView = findViewById(R.id.listCardView);
        feedbackFormCardView = findViewById(R.id.feedbackCardView);

        // initialize view pager
        // Initialize view pager
        int[] imageIds = {R.drawable.feedbacklogo};
        ViewPager2 imageViewPager = findViewById(R.id.imageViewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds, imageViewPager);
        imageViewPager.setAdapter(adapter);

        feedbackListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                intent.putExtra("fragment", "list");
                startActivity(intent);
            }
        });

        feedbackFormCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                intent.putExtra("fragment", "form");
                startActivity(intent);
            }
        });
    }
}
