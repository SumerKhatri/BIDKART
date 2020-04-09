package com.example.bidkart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SellProduct extends AppCompatActivity {
private ImageButton btn;
private  static final int PICK_IMAGE=1;
ImageView iv;
private Uri imageUri;
private Button next;
private EditText title;
Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        btn=findViewById(R.id.imageButton);
        iv=findViewById(R.id.imageView2);
        next=findViewById(R.id.btnNext);
        title = findViewById(R.id.editText);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product(title.getText().toString(),imageUri.toString());
                Intent intent = new Intent(SellProduct.this,SellProductDetails.class);
                intent.putExtra("PRODUCT",product);
                startActivity(intent);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Image"),PICK_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case PICK_IMAGE:
                    //data.getData returns the content URI for the selected Image
                    imageUri = data.getData();
                    iv.setImageURI(imageUri);
                    break;
            }



    }
}
