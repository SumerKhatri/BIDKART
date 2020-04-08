package com.example.bidkart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    private String userId;
    private String name;
    private ArrayList<String> categories;

    public User(String name) {
        this.name = name;
    }

    protected User(Parcel in){
        userId = in.readString();
        name = in.readString();
        categories = new ArrayList<String>();
        in.readStringList(categories);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeStringList(categories);
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
