package com.example.bidkart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton navBtn;

    private NavigationView navigationView;
    //private Menu menu;
    private DrawerLayout drawerLayout;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private  String userID;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutMAnager;
    private ArrayList<CardItem> arrayList=new ArrayList<CardItem>();
    private GoogleSignInClient signInClient;
    public static ProductDB pdb=new ProductDB();
    public static User currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   navBtn = findViewById(R.id.navigation_menu);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);




//        navBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(Home.this, v);
//             popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                 @Override
//                 public boolean onMenuItemClick(MenuItem item) {
//                     Toast.makeText(getApplicationContext(), "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                    switchToActivity(item.getTitle());
//                     return false;
//                 }
//             });
//                popup.inflate(R.menu.navigation_menu);
//
//
//                popup.show();
//            }
//        });
        ////////////////////////////////////////

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        userID=user.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("orders");
     DatabaseReference   dRef_Users=firebaseDatabase.getReference().child("users").child(userID);

    dRef_Users.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 currentUser=dataSnapshot.getValue(User.class);
                ImageView iv=findViewById(R.id.profile_pic);
            TextView tv=findViewById(R.id.profile_name);
            tv.setText("Welcome "+currentUser.getName());
            Picasso.get().load(Uri.parse(currentUser.getProfilepic())).into(iv);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        arrayList.add(new CardItem("","book","400","12:00:00"));
//        arrayList.add(new CardItem("","book","400","12:00:00"));
//        arrayList.add(new CardItem("","book","400","12:00:00"));
//        arrayList.add(new CardItem("","book","400","12:00:00"));






    }

    private void showData(DataSnapshot dataSnapshot) {
        arrayList.clear();
        pdb.clear();
        int pos=0;
        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
           for(DataSnapshot snapshot1:snapshot.getChildren()){
               Product p=snapshot1.getValue(Product.class);
               p.setPos(pos);
               pos++;
               pdb.add(p);
               Log.d("Product",p.toString());

               arrayList.add(new CardItem(p.getImageuri(),p.getTitle(),p.getCurrent_price()+"",p.getDuration()));

           }
        }


        loadData(arrayList);



    }
    public void loadData(ArrayList<CardItem> items){
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutMAnager=new LinearLayoutManager(this);
        mAdapter=new MyAdapter(items);

        mRecyclerView.setLayoutManager(mLayoutMAnager);
        mRecyclerView.setAdapter(mAdapter);
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
        else  if(title.equals("Share")){
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
        else if(title.equals("Home")){
            startActivity(new Intent(this,Home.class));

        }
        else if(title.equals("Search")){
            TextView tv=findViewById(R.id.searchInput);
            String input=tv.getText().toString();
            if(input.isEmpty()){
                loadData(arrayList);
                return;
            }else {
                ArrayList<Product> items=Home.pdb.searchByTitle(input);
                if(items==null)
                    return;
                else{
                    ArrayList<CardItem> ci=new ArrayList<>();
                    for(int i=0;i<items.size();i++){
                        ci.add(new CardItem(items.get(i).getImageuri(),items.get(i).getTitle(),items.get(i).getPrice()+"",items.get(i).getDuration()));
                    }
                    loadData(ci);
                }
            }
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //return true;
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
}
