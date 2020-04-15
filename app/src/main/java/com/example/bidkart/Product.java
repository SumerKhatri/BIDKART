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
    private int base_price,quantity,current_price;
    private String duration;
    private
    int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public  Product(){

    }
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", condition='" + condition + '\'' +
                ", imageuri='" + imageuri + '\'' +
                ", location='" + location + '\'' +
                ", base_price=" + base_price +
                ", current_price=" + current_price +
                ", quantity=" + quantity +
                ", duration='" + duration + '\'' +
                '}';
    }



    public Product(String category,String condition,String description,String duration,String id,String imageuri,String location,int base_price,int quantity,String title,int current_price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.condition = condition;
        this.imageuri = imageuri;
        this.location = location;
        this.base_price = base_price;
        this.quantity = quantity;
        this.duration = duration;
        this.current_price = current_price;
    }
    public Product(String duration,String condition,String imageuri,int quantity,int base_price,String description,String location,String id,String category,String title,int current_price){

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.condition = condition;
        this.imageuri = imageuri;
        this.location = location;
        this.base_price = base_price;
        this.quantity = quantity;
        this.duration = duration;
        this.current_price = current_price;

    }
    public Product(String title, String imageuri)
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
        base_price = in.readInt();
        quantity = in.readInt();
        duration = in.readString();
        current_price = in.readInt();
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
        dest.writeInt(base_price);
        dest.writeInt(quantity);
        dest.writeString(duration);
        dest.writeInt(current_price);

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

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int price) {
        this.base_price = price;
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

    public int getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(int current_price) {
        this.current_price = current_price;
    }
}
