package com.example.wapcosfeedback.adapters;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wapcosfeedback.R;

import java.util.logging.LogRecord;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

    private final int[] imageIds;
    private final Handler handler;
    private final Runnable runnable;
    private final ViewPager2 imageViewPager;

    public ImagePagerAdapter(int[] imageIds, ViewPager2 imageViewPager) {
        this.imageIds = imageIds;
        this.imageViewPager = imageViewPager;

        // Initialize the handler and runnable
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }

            public void publish(LogRecord record) {
            }

            public void flush() {
            }

            public void close() throws SecurityException {
            }

            public void removeRunnable(Runnable runnable) {
                super.removeCallbacks(runnable);
            }
        };
        runnable = new Runnable() {
            int position = 0;

            @Override
            public void run() {
                position = (position + 1) % imageIds.length;
                imageViewPager.setCurrentItem(position);
                handler.postDelayed(this, 2000); // Switch image every 3 seconds
            }
        };
        handler.postDelayed(runnable, 2000); // Start the timer
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageIds[position]);
    }

    @Override
    public int getItemCount() {
        return imageIds.length;
    }

    // Stop the timer when the activity is destroyed
    public void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}
