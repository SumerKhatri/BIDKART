package com.example.bidkart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import android.widget.PopupMenu;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity {
    private ImageButton navBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navBtn = findViewById(R.id.navigation_menu);

        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Home.this, v);
             popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem item) {
                     Toast.makeText(getApplicationContext(), "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    switchToActivity(item.getTitle());
                     return false;
                 }
             });
                popup.inflate(R.menu.navigation_menu);


                popup.show();
            }
        });
    }

    private void switchToActivity(CharSequence title) {
        if(title.equals("Sell Now"))
        startActivity(new Intent(this,SellProduct.class));
        else  if(title.equals("Share"))
            startActivity(new Intent(this,Share.class));
        else  if(title.equals("About"))
            startActivity(new Intent(this,About.class));
        else  if(title.equals("Exit"))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        else  if(title.equals("Selling"))
            startActivity(new Intent(this,Selling.class));
        else  if(title.equals("Sold"))
            startActivity(new Intent(this,Sold.class));
        else  if(title.equals("My Watch List"))
            startActivity(new Intent(this,Watchlist.class));
        else  if(title.equals("Bought"))
            startActivity(new Intent(this,Bought.class));
        else  if(title.equals("Bids"))
            startActivity(new Intent(this,Bids.class));
        else  if(title.equals("Wins"))
            startActivity(new Intent(this,Wins.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switchToActivity(item.getTitle());
        return false;
    }
}
