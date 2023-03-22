package com.example.wapcosfeedback.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wapcosfeedback.MainActivity;
import com.example.wapcosfeedback.R;
import com.example.wapcosfeedback.database.FeedbackContract;
import com.example.wapcosfeedback.database.FeedbackDbHelper;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FeedbackFormFragment extends Fragment {
    MaterialButton btnHomepage;
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
//----------    change color to red of star  of name -------///
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        String nameText = "Name*";
        SpannableString spannableName = new SpannableString(nameText);
        spannableName.setSpan(new ForegroundColorSpan(Color.RED), nameText.indexOf('*'), nameText.indexOf('*') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        nameTextView.setText(spannableName);
//----------    change color to red of star  of Email -------///
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        String emailText = "Email*";
        SpannableString spannableEmail = new SpannableString(emailText);
        spannableEmail.setSpan(new ForegroundColorSpan(Color.RED), emailText.indexOf('*'), emailText.indexOf('*') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        emailTextView.setText(spannableEmail);

//----------    change color to red of star  of Area of Interest -------///
        TextView interestTextView = view.findViewById(R.id.areaOfInterestTextView);
        String interestText = "Area of Interest*";
        SpannableString spannableInterest = new SpannableString(interestText);
        spannableInterest.setSpan(new ForegroundColorSpan(Color.RED), interestText.indexOf('*'), interestText.indexOf('*') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        interestTextView.setText(spannableInterest);

//----------    change color to red of star  of Remarks -------///
        TextView remarksTextView = view.findViewById(R.id.remarksTextView);
        String remarksText = "Remarks*";
        SpannableString spannableRemarks = new SpannableString(remarksText);
        spannableRemarks.setSpan(new ForegroundColorSpan(Color.RED), remarksText.indexOf('*'), remarksText.indexOf('*') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        remarksTextView.setText(spannableRemarks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_form, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dbHelper = new FeedbackDbHelper(getContext());

        init(view);

        Button submitBtn = view.findViewById(R.id.submitFeedbackButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Retrieve user input data from the EditText fields
                String name = nameEditText.getText().toString();
                String designation = designationEditText.getText().toString().trim();
                String organisation = organisationEditText.getText().toString().trim();
                String country = countryEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String areaOfInterest = areaOfInterestEditText.getText().toString().trim();
                String remarks = remarksEditText.getText().toString().trim();

                // Check if all required fields are filled
                if (name.isEmpty() || email.isEmpty() || areaOfInterest.isEmpty() || remarks.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
// Validate email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

// Validate mobile number format
                if (!android.util.Patterns.PHONE.matcher(mobile).matches()) {
                    Toast.makeText(getContext(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get current time and format it as a string
                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String dateTime = dateFormat.format(currentTime);

                // Insert data into the database
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
                db.close();

                // Show success layout if data is successfully inserted into the database
                if (newRowId != -1) {
                    // Inflate the success layout and show it in a dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View successLayout = inflater.inflate(R.layout.fragment_submit_ok, null);
                    builder.setView(successLayout);

// Create the dialog instance
                    final AlertDialog successDialog = builder.create();

// Set the dismiss listener for the dialog
                    successDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            successDialog.dismiss(); // dismiss the dialog when it's dismissed
                        }
                    });

// Show the dialog
                    successDialog.show();

// Find the button within the inflated success layout
                    btnHomepage = successLayout.findViewById(R.id.btnHomepage);

// Set the onClickListener for the button
                    btnHomepage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Clear the EditText fields after successful submission
                            nameEditText.setText("");
                            designationEditText.setText("");
                            organisationEditText.setText("");
                            countryEditText.setText("");
                            mobileEditText.setText("");
                            emailEditText.setText("");
                            areaOfInterestEditText.setText("");
                            remarksEditText.setText("");

                            // Redirect to MainActivity
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            // Dismiss the dialog when the button is clicked
                            successDialog.dismiss();
                        }
                    });

                } else {
                    // Show toast message if data insertion fails
                    Toast.makeText(getContext(), "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Initialize back button
        view.findViewById(R.id.backButton).setOnClickListener(v -> {
            // Create an intent to go back to MainActivity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            // Start the activity
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}

