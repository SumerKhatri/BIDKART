package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

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
    StorageReference storageReference;
    private StorageTask upload;
    String image_uri;
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
                product.setPrice(Integer.parseInt(price.getText().toString()));
                product.setQuantity(Integer.parseInt(quantity.getText().toString()));


                uploader(product.getImageuri());


                product.setDuration(days);
                SharedPreferences sh = getSharedPreferences("My_Shared_Pref", MODE_PRIVATE);
                user_id = sh.getString("user_id", "");




            }
        });
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
        dbref.child(user_id).child(id).setValue(product);
    }
}
