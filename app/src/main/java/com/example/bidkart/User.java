package com.example.bidkart;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    private String userId;
    private String name;
    private ArrayList<String> categories;
    private String number;
    private String email;
    private String profilepic;
public User(){

}
    public User(String userId, String name, ArrayList<String> categories, String number, String email, String profilepic) {
        this.userId = userId;
        this.name = name;
        this.categories = categories;
        this.number = number;
        this.email = email;
        this.profilepic = profilepic;
    }



    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    protected User(Parcel in){
        userId = in.readString();
        name = in.readString();
        categories = new ArrayList<String>();
        in.readStringList(categories);
        number = in.readString();
        email = in.readString();
        profilepic = in.readString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        dest.writeString(number);
        dest.writeString(email);
        dest.writeString(profilepic);
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
