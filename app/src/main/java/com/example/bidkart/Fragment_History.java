package com.example.bidkart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_History extends Fragment {

    Adapter_Bidding adapter_bidding;
    RecyclerView recyclerView;
    ArrayList<Bid_Data> bid_data_list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history,container,false);

        recyclerView = view.findViewById(R.id.recyclerViewbid);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bid_data_list = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("bids");
        get_latest_bid();
    }

    public void get_latest_bid(){
        databaseReference.child(((Place_Bid)context).product.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Bid_Data bid_data = dataSnapshot.getValue(Bid_Data.class);
                    ((Place_Bid) context).current_price.setText(((Place_Bid)context).price.toString());
                    ((Place_Bid) context).previous_bid_timestamp = (Long) bid_data.timestamp.get("timestamp");
                    setTimer(Math.abs(System.currentTimeMillis() - ((Place_Bid) context).previous_bid_timestamp));
                    bid_data_list.add(0, bid_data);
                    if(adapter_bidding==null){
                    adapter_bidding = new Adapter_Bidding(bid_data_list,getActivity());
                    recyclerView.setAdapter(adapter_bidding);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                    else
                        adapter_bidding.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }




    public ArrayList<Bid_Data> getData(DataSnapshot dataSnapshot) {

        ArrayList<Bid_Data> bid_info = new ArrayList<>();
        int pos = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Bid_Data bid_data = snapshot.getValue(Bid_Data.class);
            bid_info.add(pos,bid_data);
            pos++;
            Log.d("Bid_Data", bid_data.toString());

        }
        return  bid_info;
    }

    public void setTimer(Long timer){

        if(((Place_Bid)context).timer!=null)
        ((Place_Bid)context).timer.cancel();
        ((Place_Bid)context).timer = new CountDownTimer(5000000-timer,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((Place_Bid)context).countdown.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
        ((Place_Bid)context).timer.start();
    }

    private ArrayList<Bid_Data> reverseArrayList(ArrayList<Bid_Data> alist)
    {
        // ArrayList for storing reversed elements
        ArrayList<Bid_Data> revArrayList = new ArrayList<Bid_Data>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(((Place_Bid)context).timer!=null)
            ((Place_Bid)context).timer.cancel();
        int size = bid_data_list.size();
        bid_data_list.clear();
        adapter_bidding.notifyItemRangeRemoved(0,size);
    }

}
