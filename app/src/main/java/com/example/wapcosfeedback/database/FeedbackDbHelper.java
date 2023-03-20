package com.example.wapcosfeedback.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.wapcosfeedback.Models.Feedback;

import java.util.ArrayList;

public class FeedbackDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "feedback.db";
    private static final int DATABASE_VERSION = 2;

    public FeedbackDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FEEDBACK_TABLE = "CREATE TABLE " +
                FeedbackContract.FeedbackEntry.TABLE_NAME + " (" +
                FeedbackContract.FeedbackEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedbackContract.FeedbackEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FeedbackContract.FeedbackEntry.COLUMN_DESIGNATION + " TEXT, " +
                FeedbackContract.FeedbackEntry.COLUMN_ORGANISATION + " TEXT, " +
                FeedbackContract.FeedbackEntry.COLUMN_COUNTRY + " TEXT, " +
                FeedbackContract.FeedbackEntry.COLUMN_MOBILE + " TEXT, " +
                FeedbackContract.FeedbackEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                FeedbackContract.FeedbackEntry.COLUMN_AREA_OF_INTEREST + " TEXT NOT NULL, " +
                FeedbackContract.FeedbackEntry.COLUMN_REMARKS + " TEXT NOT NULL," +
                FeedbackContract.FeedbackEntry.COLUMN_DATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(SQL_CREATE_FEEDBACK_TABLE);
    }

    public ArrayList<Feedback> getAllFeedbacks() {
        ArrayList<Feedback> feedbackList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                FeedbackContract.FeedbackEntry._ID,
                FeedbackContract.FeedbackEntry.COLUMN_NAME,
                FeedbackContract.FeedbackEntry.COLUMN_DESIGNATION,
                FeedbackContract.FeedbackEntry.COLUMN_ORGANISATION,
                FeedbackContract.FeedbackEntry.COLUMN_COUNTRY,
                FeedbackContract.FeedbackEntry.COLUMN_MOBILE,
                FeedbackContract.FeedbackEntry.COLUMN_EMAIL,
                FeedbackContract.FeedbackEntry.COLUMN_AREA_OF_INTEREST,
                FeedbackContract.FeedbackEntry.COLUMN_REMARKS,
                FeedbackContract.FeedbackEntry.COLUMN_DATE_TIME

        };

        String sortOrder =
                FeedbackContract.FeedbackEntry.COLUMN_DATE_TIME + " DESC";
        Cursor cursor = db.query(
                FeedbackContract.FeedbackEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Feedback feedback = new Feedback(
                        cursor.getLong(cursor.getColumnIndex(FeedbackContract.FeedbackEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_DESIGNATION)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_ORGANISATION)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_MOBILE)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_AREA_OF_INTEREST)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_REMARKS)),
                        cursor.getString(cursor.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_DATE_TIME))
                );
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return feedbackList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + FeedbackContract.FeedbackEntry.TABLE_NAME);
        onCreate(db);
    }
}
