package com.example.bidkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SellProductDetails extends AppCompatActivity {
Spinner cat;
SeekBar sb;
TextView tv;
EditText et;
String description;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product_details);
        Intent i=getIntent();
        Uri imageUri=Uri.parse(i.getExtras().getString("image_uri"));
        cat=findViewById(R.id.spinnerCategory);
        ArrayList<String> arr=new ArrayList<String>();
        arr.add("Mobiles");
        arr.add("Laptops");
        arr.add("Books");
        arr.add("Fashion");
        arr.add("Gaming");
        arr.add("Watches");
        arr.add("Toys");
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(arrayAdapter);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Selected Item is:"+category,Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn=findViewById(R.id.descBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=et.getText().toString();
                
            }
        });
        sb=findViewById(R.id.seekBar);
        et=findViewById(R.id.editText2);
        tv=findViewById(R.id.display_condition);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress==0){
                 tv.setText("Used");
                }else if(progress==1){
                    tv.setText("Reconditioned");
                }else if(progress==2){
                    tv.setText("Open Box/Like New");
                }else if(progress==3){
                    tv.setText("New");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
