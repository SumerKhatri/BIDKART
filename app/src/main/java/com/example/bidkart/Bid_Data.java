package com.example.bidkart;

import java.util.HashMap;
import java.util.Map;

public class Bid_Data {

    public String bidder_name;
    public Integer bid_price;
    public String bid_time;
    public String user_id;
    public HashMap<String,Object> timestamp;

    public Bid_Data(String bidder_name, Integer bid_price, String bid_time, String user_id,HashMap<String,Object> timestamp) {
        this.bidder_name = bidder_name;
        this.bid_price = bid_price;
        this.bid_time = bid_time;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Bid_Data{" +
                "bidder_name='" + bidder_name + '\'' +
                ", bid_price=" + bid_price +
                ", bid_time='" + bid_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getBidder_name() {
        return bidder_name;
    }

    public void setBidder_name(String bidder_name) {
        this.bidder_name = bidder_name;
    }

    public Integer getBid_price() {
        return bid_price;
    }

    public void setBid_price(Integer bid_price) {
        this.bid_price = bid_price;
    }

    public String getBid_time() {
        return bid_time;
    }

    public void setBid_time(String bid_time) {
        this.bid_time = bid_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public HashMap<String, Object> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(HashMap<String, Object> timestamp) {
        this.timestamp = timestamp;
    }

    public Bid_Data() {
    }
}
