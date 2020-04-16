package com.example.bidkart;

import android.os.Bundle;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history,container,false);

        bid_data_list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewbid);

        if(((Place_Bid)getActivity()).price!=((Place_Bid)getActivity()).base_price) {

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("bids");
            databaseReference.child(((Place_Bid)getActivity()).product.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bid_data_list = getData(dataSnapshot);
                    adapter_bidding = new Adapter_Bidding(bid_data_list,getActivity());
                    recyclerView.setAdapter(adapter_bidding);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            /*databaseReference.child(((Place_Bid)getActivity()).product.getId()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    bid_data_list = getData(dataSnapshot);
                    adapter_bidding = new Adapter_Bidding(bid_data_list,getActivity());
                    recyclerView.setAdapter(adapter_bidding);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
*/
        }
        return view;
    }

    /*public void add_bidder(){

        String temp_timer = "10000";
        Bid_Data data = new Bid_Data(((Place_Bid) getActivity()).user_id,((Place_Bid) getActivity()).price,temp_timer,((Place_Bid)getActivity()).user_id);
        ArrayList<Bid_Data> bid_info = new ArrayList<Bid_Data>();
        bid_info.set(0,data);
        adapter_bidding = new Adapter_Bidding(bid_data_list,getActivity());
        recyclerView.setAdapter(adapter_bidding);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }*/

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


}
