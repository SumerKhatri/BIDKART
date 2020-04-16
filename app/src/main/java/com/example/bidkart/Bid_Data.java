package com.example.bidkart;

public class Bid_Data {

    public String bidder_name;
    public Integer bid_price;
    public String bid_time;
    public String user_id;

    public Bid_Data(String bidder_name, Integer bid_price, String bid_time, String user_id) {
        this.bidder_name = bidder_name;
        this.bid_price = bid_price;
        this.bid_time = bid_time;
        this.user_id = user_id;
    }

    public Bid_Data() {
    }
}
