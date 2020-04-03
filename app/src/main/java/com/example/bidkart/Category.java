package com.example.bidkart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Category extends AppCompatActivity {

    private TextView nm;
    private Button brw;
    private CheckBox mobile,laptop,book,fashion,gaming,watches,toys;
    private ArrayList<String> mResult;
    private TextView mResulttext;
   // private String[] listItems;
    //boolean[] checkedItems;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.category);


        brw=findViewById(R.id.button_browse);
        nm=findViewById(R.id.textView2);

        mobile=findViewById(R.id.check_mobile);
        laptop=findViewById(R.id.check_laptop);
        book=findViewById(R.id.check_book);
        fashion=findViewById(R.id.check_Fashion);
        gaming=findViewById(R.id.check_Gaming);
        watches=findViewById(R.id.check_watches);
        toys=findViewById(R.id.check_toys);

        mResulttext=findViewById(R.id.resultt);
        mResult=new ArrayList<>();
        mResulttext.setEnabled(false);


        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Mobile");
                else
                    mResult.remove("Mobile");

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book.isChecked())
                    mResult.add("Book");
                else
                    mResult.remove("Book");

            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Laptop");
                else
                    mResult.remove("Laptop");

            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Fashion and Apparel");
                else
                    mResult.remove("Fashion and Apparel");

            }
        });

        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Gaming");
                else
                    mResult.remove("Gaming");

            }
        });



        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Watches");
                else
                    mResult.remove("Watches");

            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.isChecked())
                    mResult.add("Toys");
                else
                    mResult.remove("Toys");

            }
        });


        brw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                for(String s : mResult)
                    stringBuilder.append(s).append("\n");


                mResulttext.setText(stringBuilder.toString());
                mResulttext.setEnabled(false);


                Intent intent=new Intent(Category.this,Home.class);
                startActivity(intent);


            }
        });







    }
}
