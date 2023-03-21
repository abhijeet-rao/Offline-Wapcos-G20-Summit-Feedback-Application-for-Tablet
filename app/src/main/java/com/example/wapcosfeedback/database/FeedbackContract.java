package com.example.wapcosfeedback.database;

import android.provider.BaseColumns;

public final class FeedbackContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedbackContract() {
    }

    /* Inner class that defines the table contents */
    public static class FeedbackEntry implements BaseColumns {
        public static final String TABLE_NAME = "feedback";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESIGNATION = "designation";
        public static final String COLUMN_ORGANISATION = "organisation";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_AREA_OF_INTEREST = "area_of_interest";
        public static final String COLUMN_REMARKS = "remarks";
        public static final String COLUMN_DATE_TIME = "date_time";
    }
}
