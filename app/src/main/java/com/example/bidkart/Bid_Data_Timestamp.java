package com.example.bidkart;

import java.util.Map;

public class Bid_Data_Timestamp {
    public String bidder_name;
    public Integer bid_price;
    public String bid_time;
    public String user_id;
    public Map<String,String> timestamp;

    public Bid_Data_Timestamp(String bidder_name, Integer bid_price, String bid_time, String user_id,Map<String,String> timestamp) {
        this.bidder_name = bidder_name;
        this.bid_price = bid_price;
        this.bid_time = bid_time;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public Bid_Data_Timestamp() {
    }
}
