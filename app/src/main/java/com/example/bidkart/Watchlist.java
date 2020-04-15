package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Watchlist extends AppCompatActivity {
String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        String s=getIntent().getStringExtra("position");
        int pos;
        if(s!=null){
             pos=Integer.parseInt(s);
            id=Home.pdb.getPdb().get(pos).getId();
        }

     //  Log.d("Product Clicked is:",Home.pdb.getPdb().get(pos)+"");




        String data= readFromFile(getApplicationContext());
        if(!data.contains(id))
        writeToFile(id+data,getApplicationContext());

         data= readFromFile(getApplicationContext());

        Log.d("Data is",data);
            showData(data);
    }

    private void writeToFile(String data,Context context) {
   // data="";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("p_id.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("p_id.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void showData(String data) {
        int index=0;
        ArrayList<CardItem_Selling> arrayList=new ArrayList<>();
        for(int i=0;i<data.length();){
             index=data.indexOf(',',i);
            String p_id=data.substring(i,i+20);
            i=i+20;
            Log.d("p_id--------",p_id);
            Product p=Home.pdb.searchByID(p_id);
            arrayList.add(new CardItem_Selling(p.getImageuri(), p.getTitle()));
        }

        Log.d("data length:",data.length()+"");


        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Watchlist);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager  mLayoutMAnager=new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter=new MyAdapter_Selling(arrayList);

        mRecyclerView.setLayoutManager(mLayoutMAnager);
        mRecyclerView.setAdapter(mAdapter);


    }
}
