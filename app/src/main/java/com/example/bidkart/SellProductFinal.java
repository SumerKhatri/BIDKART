package com.example.bidkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SellProductFinal extends AppCompatActivity {
    Spinner sp;
    EditText quantity,price;
    String days;
    Button sell_final;
    String user_id;
    DatabaseReference dbref;
    FirebaseDatabase database;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product_final);
        sp=findViewById(R.id.spinnerDays);
        sell_final = findViewById(R.id.post);
        price = findViewById(R.id.editText5);
        quantity = findViewById(R.id.editText6);

        ArrayList<String> arr=new ArrayList<>();
        for(int i=3;i<=100;i++){
            arr.add(""+i);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);

        Intent i = getIntent();
        product = i.getParcelableExtra("PRODUCT");

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("orders");

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                days=parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sell_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setPrice(Integer.parseInt(price.getText().toString()));
                product.setQuantity(Integer.parseInt(quantity.getText().toString()));
                product.setDuration(days);
                SharedPreferences sh = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                user_id = sh.getString("user_id","");
                add_product();
                startActivity(new Intent(SellProductFinal.this,Selling.class));
            }
        });
    }

    private void  add_product() {

        String id = dbref.push().getKey();
        product.setId(id);
        Log.d("Username",user_id);
        dbref.child(user_id).setValue(product);
    }
}
