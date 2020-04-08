package com.example.bidkart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    private EditText name;
    private Button cont,skip;
    User user;
    String Fname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_name);

        cont=findViewById(R.id.cont);
        name=findViewById(R.id.ETentername);
        skip = findViewById(R.id.btn_skip_login);



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Home.class);
                startActivity(intent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fname = name.getText().toString();
                user = new User(Fname);
                Intent intent = new Intent(Profile.this,Category.class);
                intent.putExtra("USER",user);
                startActivity(intent);

            }
        });

    }
}
