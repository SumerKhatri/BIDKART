package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        navigationView = findViewById(R.id.nav_view_selling_about);
        drawerLayout = findViewById(R.id.drawer_layout_about);



        Toolbar toolbar = findViewById(R.id.toolbar_selling_about);
        setSupportActionBar(toolbar);
        //   navBtn = findViewById(R.id.navigation_menu);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


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
                    Intent intent = new Intent(About.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else  if(title.equals("Share")) {
            startActivity(new Intent(this,Share.class));
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = "https://github.com/SumerKhatri/BIDKART";
            String sub = "Get our app from following link";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        }
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
        else if(title.equals("Home"))
            startActivity(new Intent(this,Home.class));

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


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switchToActivity(item.getTitle());
        return false;
    }
}
