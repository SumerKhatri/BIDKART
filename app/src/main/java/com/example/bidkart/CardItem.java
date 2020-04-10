package com.example.bidkart;

public class CardItem {
private String imageUri,title,price,time;

    public CardItem(String imageUri, String title, String price, String time) {
        this.imageUri = imageUri;
        this.title = title;
        this.price = price;
        this.time = time;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
