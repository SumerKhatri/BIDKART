package com.example.bidkart;

import android.content.Intent;
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
    DatabaseReference dbref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_name);

        cont=findViewById(R.id.cont);
        name=findViewById(R.id.ETentername);
        skip = findViewById(R.id.btn_skip_login);

        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("users");

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



                add_user();
                Intent intent = new Intent(Profile.this,Category.class);
                startActivity(intent);

            }
        });

    }

    private void add_user()
    {
        String Fname = name.getText().toString();

        if(!TextUtils.isEmpty(Fname))
        {
            String id = dbref.push().getKey();

            User user = new User(id,Fname);
            dbref.child(id).setValue(user.name);
        }

        else{
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }
    }
}
