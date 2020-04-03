package com.example.bidkart;

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
                     return false;
                 }
             });
                popup.inflate(R.menu.navigation_menu);

               
                popup.show();
            }
        });
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
//        switch (item.getItemId()) {
//            case R.id.search_item:
//                // do your code
//                return true;
//            case R.id.upload_item:
//                // do your code
//                return true;
//            case R.id.copy_item:
//                // do your code
//                return true;
//            case R.id.print_item:
//                // do your code
//                return true;
//            case R.id.share_item:
//                // do your code
//                return true;
//            case R.id.bookmark_item:
//                // do your code
//                return true;
//            default:
//                return false;
//    }
        return false;
    }
}
