package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class SellProductFinal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Interpreter interpreter;
    Spinner sp;
    EditText quantity,price;
    String days;
    Button sell_final;
    String user_id;
    DatabaseReference dbref;
    FirebaseDatabase database;
    Product product;
    StorageReference storageReference;
    private StorageTask upload;
    String image_uri;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product_final);
        sp=findViewById(R.id.spinnerDays);
        sell_final = findViewById(R.id.post);
        price = findViewById(R.id.editText5);
        quantity = findViewById(R.id.editText6);

        navigationView = findViewById(R.id.nav_view_selling_final);
        drawerLayout = findViewById(R.id.drawer_layout_final);
    loadingDialog=new LoadingDialog(SellProductFinal.this);


        Toolbar toolbar = findViewById(R.id.toolbar_selling_final);
        setSupportActionBar(toolbar);
        //   navBtn = findViewById(R.id.navigation_menu);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);




        ArrayList<String> arr=new ArrayList<>();
        for(int i=3;i<=100;i++){
            arr.add(""+i);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);

        Intent i = getIntent();
        product = i.getParcelableExtra("PRODUCT");

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("orders");
        storageReference= FirebaseStorage.getInstance().getReference("Images");
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
                loadingDialog.startLoadingDialog();
                product.setBase_price(Integer.parseInt(price.getText().toString()));
                product.setCurrent_price(Integer.parseInt(price.getText().toString()));
                product.setQuantity(Integer.parseInt(quantity.getText().toString()));
                product.setDuration(days);

                SharedPreferences sh = getSharedPreferences("My_Shared_Pref", MODE_PRIVATE);
                user_id = sh.getString("user_id", "");
                product.setUser_id(user_id);

                uploader(product.getImageuri());

            }
        });
        try {
            interpreter = new Interpreter(loadModelFile(), null);
        }catch (IOException e){
            e.printStackTrace();
        }
        final EditText editText=findViewById(R.id.editText3);
        //price
        Button predict=findViewById(R.id.bidding_calculate);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float f=doInference(editText.getText().toString());
                int x=(int)f;
                price.setText(x+"");
            }
        });
    }

    private MappedByteBuffer loadModelFile() throws IOException{
        AssetFileDescriptor assetFileDescriptor=this.getAssets().openFd("model.tflite");
        FileInputStream fileInputStream=new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel=fileInputStream.getChannel();
        long startOffset=assetFileDescriptor.getStartOffset();
        long length=assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,length);
    }
    public float doInference(String val){
        float[]input=new float[1];
        input[0]=Float.parseFloat(val);
        float[][]output=new float[1][1];
        interpreter.run(input,output);
        return output[0][0];

    }

    private void uploader(String uri){
      Uri  imageUri=Uri.parse(uri);
   final   StorageReference Ref=storageReference.child(System.currentTimeMillis()+"."+getExtension(imageUri));
       upload= Ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                              Log.d("before",product.getImageuri());
                               product.setImageuri(uri.toString());
                                Log.d("After:",product.getImageuri());
                               image_uri=uri.toString();
                                add_product();
                                loadingDialog.dismissDialog();
                                startActivity(new Intent(SellProductFinal.this, Selling.class));
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


    }
    private  String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void  add_product() {
        //This is product id
        String id = dbref.push().getKey();
        product.setId(id);
       // product.setImageuri(image_uri);
       // Log.d("url",image_uri+"");
        dbref.child(id).setValue(product);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switchToActivity(item.getTitle());
        return false;
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
                    Intent intent = new Intent(SellProductFinal.this,MainActivity.class);
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
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
