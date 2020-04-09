package com.example.bidkart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Category extends AppCompatActivity {

    private Button brw;
    private String id;
    private ArrayList<String> mResult = new ArrayList<String>();
    User user;
    DatabaseReference dbref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        brw=findViewById(R.id.button_browse);
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("users");

        Intent intent = getIntent();
        user = intent.getParcelableExtra("USER");

        brw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_user();
                Intent intent=new Intent(Category.this,Home.class);
                startActivity(intent);
            }
        });

    }



    public void SelectItem(View view)
    {
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()){
            case R.id.check_mobile:
                if(checked)
                    mResult.add("Mobiles");
                else
                    mResult.remove("Mobiles");
                break;
            case R.id.check_laptop:
                if(checked)
                    mResult.add("Laptops");
                else
                    mResult.remove("Laptops");
                break;
            case R.id.check_book:
                if(checked)
                    mResult.add("Books");
                else
                    mResult.remove("Books");
                break;
            case R.id.check_Fashion:
                if(checked)
                    mResult.add("Fashion");
                else
                    mResult.remove("Fashion");
                break;
            case R.id.check_Gaming:
                if(checked)
                    mResult.add("Gaming");
                else
                    mResult.remove("Gaming");
                break;
            case R.id.check_watches:
                if(checked)
                    mResult.add("Watches");
                else
                    mResult.remove("Watches");
                break;
            case R.id.check_toys:
                if(checked)
                    mResult.add("Toys");
                else
                    mResult.remove("Toys");
                break;
        }
    }

    private void add_user()
    {
        if(!TextUtils.isEmpty(mResult.get(0)))
        {
            user.setCategories(mResult);
            String id = dbref.push().getKey();
            user.setUserId(id);
            SharedPreferences sharedPreferences = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
            SharedPreferences.Editor myedit = sharedPreferences.edit();
            myedit.putString("user_id",id);
            myedit.commit();
            dbref.child(id).setValue(user);

        }

        else{
            Toast.makeText(this,"You should select atleast one category",Toast.LENGTH_LONG).show();
        }
    }
}
