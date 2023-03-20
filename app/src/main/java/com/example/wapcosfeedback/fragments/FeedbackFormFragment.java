package com.example.wapcosfeedback.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wapcosfeedback.R;
import com.example.wapcosfeedback.database.FeedbackContract;
import com.example.wapcosfeedback.database.FeedbackDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeedbackFormFragment extends Fragment {

    private FeedbackDbHelper dbHelper;
    private EditText nameEditText, designationEditText, organisationEditText, countryEditText, mobileEditText, emailEditText, areaOfInterestEditText, remarksEditText;

    private void init(View view) {
        nameEditText = view.findViewById(R.id.nameEditText);
        designationEditText = view.findViewById(R.id.designationEditText);
        organisationEditText = view.findViewById(R.id.organisationEditText);
        countryEditText = view.findViewById(R.id.countryEditText);
        mobileEditText = view.findViewById(R.id.mobileEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        areaOfInterestEditText = view.findViewById(R.id.areaOfInterestEditText);
        remarksEditText = view.findViewById(R.id.remarksEditText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_form, container, false);
        dbHelper = new FeedbackDbHelper(getContext());

        init(view);

        Button submitBtn = view.findViewById(R.id.submitFeedbackButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String designation = designationEditText.getText().toString().trim();
                String organisation = organisationEditText.getText().toString().trim();
                String country = countryEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String areaOfInterest = areaOfInterestEditText.getText().toString().trim();
                String remarks = remarksEditText.getText().toString().trim();
                Log.d("MyApp", "name: " + name + ", email: " + email + ", areaOfInterest: " + areaOfInterest + ", remarks: " + remarks);
                if (name.isEmpty() || email.isEmpty() || areaOfInterest.isEmpty() || remarks.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String dateTime = dateFormat.format(currentTime);

                ContentValues values = new ContentValues();
                values.put(FeedbackContract.FeedbackEntry.COLUMN_NAME, name);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_DESIGNATION, designation);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_ORGANISATION, organisation);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_COUNTRY, country);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_MOBILE, mobile);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_EMAIL, email);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_AREA_OF_INTEREST, areaOfInterest);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_REMARKS, remarks);
                values.put(FeedbackContract.FeedbackEntry.COLUMN_DATE_TIME, dateTime);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long newRowId = db.insert(FeedbackContract.FeedbackEntry.TABLE_NAME, null, values);
                if (newRowId != -1) {
                    Toast.makeText(getContext(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                    nameEditText.setText("");
                    designationEditText.setText("");
                    organisationEditText.setText("");
                    countryEditText.setText("");
                    mobileEditText.setText("");
                    emailEditText.setText("");
                    areaOfInterestEditText.setText("");
                    remarksEditText.setText("");
                } else {
                    Toast.makeText(getContext(), "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

