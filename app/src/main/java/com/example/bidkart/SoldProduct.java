package com.example.bidkart;

public class SoldProduct {
    private String product_id;
    private String title;
    private String description;
    private String category;
    private String condition;
    private String imageuri;
    private String location;
    private int base_price,quantity,final_price;
    private String duration;
    private int pos;
    private String seller_id,buyer_id;
public SoldProduct(){

}
    public SoldProduct(String product_id, String title, String description, String category, String condition, String imageuri, String location, int base_price, int quantity, int final_price, String duration, int pos, String seller_id,String buyer_id) {
        this.product_id = product_id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.condition = condition;
        this.imageuri = imageuri;
        this.location = location;
        this.base_price = base_price;
        this.quantity = quantity;
        this.final_price = final_price;
        this.duration = duration;
        this.pos = pos;
        this.seller_id = seller_id;
        this.buyer_id=buyer_id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    @Override
    public String toString() {
        return "SoldProduct{" +
                "product_id='" + product_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", condition='" + condition + '\'' +
                ", imageuri='" + imageuri + '\'' +
                ", location='" + location + '\'' +
                ", base_price=" + base_price +
                ", quantity=" + quantity +
                ", current_price=" + final_price +
                ", duration='" + duration + '\'' +
                ", pos=" + pos +
                ", seller_id='" + seller_id + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                '}';
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int current_price) {
        this.final_price = current_price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
