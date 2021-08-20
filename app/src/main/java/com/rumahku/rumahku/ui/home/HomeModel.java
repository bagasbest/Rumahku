package com.rumahku.rumahku.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeModel implements Parcelable {

    List<String> hospital;
    String latlng;
    String location;
    List<String> recreation;
    List<String> school;
    List<String> shopping;
    String title;

    public HomeModel(){}

    public List<String> getHospital() {
        return hospital;
    }

    public void setHospital(List<String> hospital) {
        this.hospital = hospital;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getRecreation() {
        return recreation;
    }

    public void setRecreation(List<String> recreation) {
        this.recreation = recreation;
    }

    public List<String> getSchool() {
        return school;
    }

    public void setSchool(List<String> school) {
        this.school = school;
    }

    public List<String> getShopping() {
        return shopping;
    }

    public void setShopping(List<String> shopping) {
        this.shopping = shopping;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected HomeModel(Parcel in) {
        hospital = in.createStringArrayList();
        latlng = in.readString();
        location = in.readString();
        recreation = in.createStringArrayList();
        school = in.createStringArrayList();
        shopping = in.createStringArrayList();
        title = in.readString();
    }

    public static final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(hospital);
        parcel.writeString(latlng);
        parcel.writeString(location);
        parcel.writeStringList(recreation);
        parcel.writeStringList(school);
        parcel.writeStringList(shopping);
        parcel.writeString(title);
    }
}
