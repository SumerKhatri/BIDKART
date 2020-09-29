package com.example.bidkart;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Login_Loading {
    Activity activity;
    AlertDialog alertDialog;
    public Login_Loading(Activity activity) {
        this.activity = activity;
    }
    void startLogin_Loading(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.login_loading,null));
        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }
    void dismissDialog(){
        alertDialog.dismiss();
    }
}
