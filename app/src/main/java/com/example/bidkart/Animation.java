package com.example.bidkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Animation extends AppCompatActivity {
android.view.animation.Animation top,bottom;
ImageView iv;
TextView name,tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animation);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        iv=findViewById(R.id.imageView);
        name=findViewById(R.id.textView18);
        tag=findViewById(R.id.textView19);

        iv.setAnimation(top);
        name.setAnimation(bottom);
        tag.setAnimation(bottom);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(Animation.this,MainActivity.class));
               finish();
           }
       },2500);
    }
}
