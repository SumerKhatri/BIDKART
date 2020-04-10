package com.example.bidkart;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private ImageButton navBtn;
    private GoogleSignInClient signInClient;

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
        else if(title.equals("Logout")){
            /*SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
            sp.edit().putBoolean("logged",false).apply();*/
            FirebaseAuth.getInstance().signOut();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(Home.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }
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
