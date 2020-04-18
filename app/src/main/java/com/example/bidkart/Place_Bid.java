package com.example.bidkart;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Place_Bid extends AppCompatActivity {

    private ImageView product_image;
    private TextView title,desc,category,condition;
     Button place_bid;
    TextView countdown,current_price;
    CountDownTimer timer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Product product;
    String user_id,user_name;
    Integer price,base_price;
    Long previous_bid_timestamp = 10L ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pos = Integer.parseInt(getIntent().getStringExtra("position"));
        product = Home.pdb.getProduct(pos);
        price = product.getCurrent_price();
        base_price = product.getBase_price();
        setContentView(R.layout.activity_place_bid);

        product_image = findViewById(R.id.imageView3);
        title = findViewById(R.id.textView20);
        place_bid = findViewById(R.id.button);
        current_price = findViewById(R.id.textView22);
        countdown = findViewById(R.id.timer);
        desc = findViewById(R.id.textView24);
        category = findViewById(R.id.textView25);
        condition = findViewById(R.id.textView26);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("bids");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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



         Picasso.get().load(Uri.parse(product.getImageuri())).into(product_image);
         title.setText(product.getTitle());
         price = product.getCurrent_price();
         current_price.setText(price.toString());
         desc.setText(product.getDescription());
         category.setText(product.getCategory());
         condition.setText(product.getCondition());

        SharedPreferences sh = getSharedPreferences("My_Shared_Pref", MODE_PRIVATE);
        user_id = sh.getString("user_id", "");
        user_name = Home.currentUser.getName();


        place_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bid_id = databaseReference.child(product.getId()).push().getKey();
                int cur_price = Integer.parseInt(current_price.getText().toString());
                HashMap<String,Object> map = new HashMap<>();
                map.put("timestamp",ServerValue.TIMESTAMP);
                Long bid_time;
                if(previous_bid_timestamp == 10L)
                    bid_time = 0L;
                else
                bid_time = Math.abs((System.currentTimeMillis()-previous_bid_timestamp)/1000);
                Bid_Data bid_data = new Bid_Data(user_name,cur_price,String.valueOf(bid_time),user_id,map);
                databaseReference.child(product.getId()).child(bid_id).setValue(bid_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        DatabaseReference product_reference = firebaseDatabase.getReference("orders");
                        product_reference.child(product.getId()).child("current_price").setValue(price += base_price/10).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Fragment_History fragment_history = (Fragment_History)getSupportFragmentManager().findFragmentById(R.id.fragment);
                                current_price.setText((price).toString());
                            }
                        });

                    }


                });


            }
        });


    }
}
