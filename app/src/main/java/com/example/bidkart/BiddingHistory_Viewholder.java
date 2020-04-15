package com.example.bidkart;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BiddingHistory_Viewholder extends RecyclerView.ViewHolder {

    TextView bidder_name;
    TextView bid_price;
    TextView bid_time;

    BiddingHistory_Viewholder(View itemView)
    {
        super(itemView);
        bidder_name = itemView.findViewById(R.id.textView27);
        bid_price = itemView.findViewById(R.id.textView28);
        bid_time = itemView.findViewById(R.id.textView29);
    }
}
