package com.example.bidkart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_History extends Fragment {

    Adapter_Bidding adapter_bidding;
    RecyclerView recyclerView;
    ArrayList<Bid_Data> bid_data_list;
    static boolean check;
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
        check = true;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("bids");
        get_latest_bid();
    }

    public void get_latest_bid(){
        databaseReference.child(((Place_Bid)context).product.getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Bid_Data bid_data = dataSnapshot.getValue(Bid_Data.class);
                    Integer inc_price = bid_data.bid_price;
                    inc_price += ((Place_Bid) context).base_price/10;
                    ((Place_Bid) context).current_price.setText(inc_price.toString());
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




    /*public ArrayList<Bid_Data> getData(DataSnapshot dataSnapshot) {

        ArrayList<Bid_Data> bid_info = new ArrayList<>();
        int pos = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Bid_Data bid_data = snapshot.getValue(Bid_Data.class);
            bid_info.add(pos,bid_data);
            pos++;
            Log.d("Bid_Data", bid_data.toString());

        }
        return  bid_info;
    }*/

    public void setTimer(Long timer){

        if(((Place_Bid)context).timer!=null)
        ((Place_Bid)context).timer.cancel();
        ((Place_Bid)context).timer = new CountDownTimer(30000-timer,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished/1000;
                int p1 = seconds % 60;
                int p2 = seconds / 60;
                int p3 = p2 % 60;
                p2 = p2 / 60;
                ((Place_Bid)context).countdown.setText(p2 + ":" + p3 + ":" + p1);
            }

            @Override
            public void onFinish() {

                DatabaseReference dbr = firebaseDatabase.getReference("bids");
                dbr.child(((Place_Bid)context).product.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Bid_Data> bid_info = new ArrayList<>();
                        Long tstamp = null;
                        int pos = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Bid_Data bid_data = snapshot.getValue(Bid_Data.class);
                            bid_info.add(pos,bid_data);
                            pos++;
                            Log.d("Bid_Data", bid_data.toString());
                            tstamp = (Long) bid_data.timestamp.get("timestamp");
                        }

                        if(Math.abs(System.currentTimeMillis() - tstamp ) < 30000)
                        {
                            return;
                        }
                        else {
                            Log.d("finished----","---------------------------");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            final DatabaseReference dR = firebaseDatabase.getReference("bids").child(((Place_Bid)context).product.getId());

                            final    Product p=Home.pdb.searchByID(dR.getKey());
                            Log.d("product","----------------------------"+p);
                            final int curr_price=p.getCurrent_price()-p.getBase_price()*10/100;
                            final String seller_id=p.getUser_id();
                            ((Place_Bid) context).place_bid.setText("SOLD OUT");

                            Query q= dR.orderByKey().limitToLast(1);
                            Log.d("q","---------------------------------------------------------------      "+q.getRef().getKey());
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                        Bid_Data last_bid=ds.getValue(Bid_Data.class);
                                        if(last_bid.getBid_price()==curr_price){
                                            Log.d("Last_bid","----------------------------------------------"+last_bid);
                                            String   buyer_id=last_bid.getUser_id();
                                            SoldProduct sp=new SoldProduct(p.getId(),p.getTitle(),p.getDescription()
                                                    ,p.getCategory(),p.getCondition(),p.getImageuri(),p.getLocation()
                                                    , p.getBase_price(),p.getQuantity(),p.getCurrent_price()-p.getBase_price()*10/100, p.getDuration(),p.getPos(),  seller_id,buyer_id);
                                            DatabaseReference dr_soldproduct=firebaseDatabase.getReference().child("Sold_Products").child(p.getId());
                                            dr_soldproduct.setValue(sp);
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            final DatabaseReference product=firebaseDatabase.getReference().child("orders").child(p.getId());
                            product.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent i=new Intent(getContext(),Wins.class);

                                    getActivity().finishAffinity();
                                    startActivity(new Intent(getContext(),Home.class));
                                    startActivity(i);
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        };
        ((Place_Bid)context).timer.start();
    }

    /*private ArrayList<Bid_Data> reverseArrayList(ArrayList<Bid_Data> alist)
    {
        // ArrayList for storing reversed elements
        ArrayList<Bid_Data> revArrayList = new ArrayList<Bid_Data>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }*/

    @Override
    public void onStop() {
        super.onStop();
        if(((Place_Bid)context).timer!=null)
            ((Place_Bid)context).timer.cancel();
        int size = bid_data_list.size();
        bid_data_list.clear();
        if(adapter_bidding != null)
        adapter_bidding.notifyItemRangeRemoved(0,size);
    }

}
