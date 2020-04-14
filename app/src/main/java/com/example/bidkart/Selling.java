package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Selling extends AppCompatActivity {
FirebaseAuth firebaseAuth;
String userID;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
ArrayList <CardItem_Selling> arrayList=new ArrayList<CardItem_Selling>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        int pos=Integer.parseInt(getIntent().getStringExtra("position"));
        Log.d("Product Clicked is:",Home.pdb.getPdb().get(pos)+"");
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        userID=user.getUid();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void showData(DataSnapshot dataSnapshot) {
        arrayList.clear();
        Log.d("current user_id:",userID);
        if(userID==null)
            return;
        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
            if(snapshot.getKey().equals(userID)) {
            Log.d("User_id  database is:",snapshot.getKey());
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Product p = snapshot1.getValue(Product.class);
                   // Log.d("Product", p.toString());

                    arrayList.add(new CardItem_Selling(p.getImageuri(), p.getTitle()));

                }
            }

        }

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Selling);
        mRecyclerView.setHasFixedSize(true);
      RecyclerView.LayoutManager  mLayoutMAnager=new LinearLayoutManager(this);
       Adapter mAdapter=new MyAdapter_Selling(arrayList);

        mRecyclerView.setLayoutManager(mLayoutMAnager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
