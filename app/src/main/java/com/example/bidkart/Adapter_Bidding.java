package com.example.bidkart;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class Adapter_Bidding extends RecyclerView.Adapter<BiddingHistory_Viewholder> {

    ArrayList<Bid_Data> bid_data;
    Context context;
    String user_id;



    public Adapter_Bidding(ArrayList<Bid_Data> bid_data, Context context) {
        this.bid_data = bid_data;
        this.context = context;
    }

    @NonNull
    @Override
    public BiddingHistory_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bid_view = inflater.inflate(R.layout.recycler_bids,parent,false);
        SharedPreferences sharedPreferences = context.getSharedPreferences("My_Shared_Pref", MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");

        BiddingHistory_Viewholder biddingHistory_viewholder = new BiddingHistory_Viewholder(bid_view);

        return biddingHistory_viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull BiddingHistory_Viewholder holder, int position) {

        Bid_Data current_data = bid_data.get(position);
        int size = bid_data.size();

        if(size -1 == position) {
            if (current_data.user_id.equals(user_id))
                Place_Bid.place_bid.setEnabled(false);
            else
                Place_Bid.place_bid.setEnabled(true);
        }

        if(current_data.user_id.equals(user_id))
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ecffeb"));
            current_data.bidder_name = "You";
        }


        holder.bidder_name.setText(current_data.bidder_name);
        holder.bid_price.setText(current_data.bid_price.toString());
        holder.bid_time.setText(current_data.bid_time);


    }

    @Override
    public int getItemCount() {
        return bid_data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
