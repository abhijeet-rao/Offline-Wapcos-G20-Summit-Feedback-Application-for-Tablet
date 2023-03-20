package com.example.wapcosfeedback.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class Feedback implements Parcelable {
    private long id;
    private String name;
    private String designation;
    private String organisation;
    private String country;
    private String mobile;
    private String email;
    private String areaOfInterest;
    private String remarks;
    private String dateTime;

    public Feedback(long id, String name, String designation, String organisation, String country,
                    String mobile, String email, String areaOfInterest, String remarks, String dateTime) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.organisation = organisation;
        this.country = country;
        this.mobile = mobile;
        this.email = email;
        this.areaOfInterest = areaOfInterest;
        this.remarks = remarks;
        this.dateTime = dateTime;
    }


    protected Feedback(Parcel in) {
        id = in.readLong();
        name = in.readString();
        designation = in.readString();
        organisation = in.readString();
        country = in.readString();
        mobile = in.readString();
        email = in.readString();
        areaOfInterest = in.readString();
        remarks = in.readString();
        dateTime= in.readString();
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(designation);
        dest.writeString(organisation);
        dest.writeString(country);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(areaOfInterest);
        dest.writeString(remarks);
        dest.writeString(dateTime);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAreaOfInterest() {
        return areaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateTime() {
        return dateTime;
    }
}
