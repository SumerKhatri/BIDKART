package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Wins extends AppCompatActivity {
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wins);
        DatabaseReference dRef_sold_products= FirebaseDatabase.getInstance().getReference().child("Sold_Products");
        dRef_sold_products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    private void showData(DataSnapshot dataSnapshot) {
        ArrayList<CardItem_Selling> arrayList=new ArrayList<>();

        Log.d("current user_id:", userID);
        if (userID == null)
            return;


        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
            SoldProduct sp=snapshot.getValue(SoldProduct.class);
            if(sp.getBuyer_id().equals(userID)){
                arrayList.add(new CardItem_Selling(sp.getImageuri(), sp.getTitle()));
            }



        }

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_wins);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager  mLayoutMAnager=new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter=new MyAdapter_Selling(arrayList);

        mRecyclerView.setLayoutManager(mLayoutMAnager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
