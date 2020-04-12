package com.example.bidkart;

public class CardItem_Selling {
    String imageUri,title;

    public CardItem_Selling(String imageUri, String title) {
        this.imageUri = imageUri;
        this.title = title;
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
}
