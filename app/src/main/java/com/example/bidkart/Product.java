package com.example.bidkart;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
    private String id;
    private String title;
    private String description;
    private String category;
    private String condition;
    private String imageuri;
    private String location;
    private int price,quantity;
    private String duration;

    public Product(String title,String imageuri)
    {
        this.title = title;
        this.imageuri = imageuri;
    }

    protected Product(Parcel in){
        id = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        condition = in.readString();
        imageuri = in.readString();
        location = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        duration = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(imageuri);
        dest.writeString(location);
        dest.writeInt(price);
        dest.writeInt(quantity);
        dest.writeString(duration);

    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}