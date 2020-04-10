package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button VerifyPhone;
    private Button Test_login;
    private String TAG = "MainActivity";
    EditText PhoneNumber;
    private String Number;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signInButton = findViewById(R.id.signInButton);
        mAuth = FirebaseAuth.getInstance();
        VerifyPhone = findViewById(R.id.btn_verify_phone);
        PhoneNumber = findViewById(R.id.ETLoginPhone);
        Test_login = findViewById(R.id.btn_test_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        VerifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = PhoneNumber.getText().toString();
                validNo(Number);
                Intent intent = new Intent(MainActivity.this,VerifyMobile.class);
                intent.putExtra("PhoneNumber",Number);
                startActivity(intent);
                Toast.makeText(MainActivity.this,Number,Toast.LENGTH_LONG).show();

            }
        });

        Test_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        });



    }

        private void signIn(){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent,RC_SIGN_IN);

        }

    private void validNo(String no){
        if(no.isEmpty() || no.length() < 10){
            PhoneNumber.setError("Enter a valid mobile");
            PhoneNumber.requestFocus();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == RC_SIGN_IN){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
    }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Google Signed In Successful",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(MainActivity.this,"Signed In Not Successful",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                    sp.edit().putBoolean("logged",true).apply();
                    Intent intent = new Intent(MainActivity.this,Profile.class);
                    startActivity(intent);

                    //Toast.makeText(MainActivity.this,"Signed In Successful",Toast.LENGTH_SHORT).show();
                    //FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI(user);
                }
                else{
                    Toast.makeText(MainActivity.this,"Sign In Not Successful",Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    /*private void updateUI(FirebaseUser fuser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            String personName = account.getDisplayName();
            String personEmain = account.getEmail();

            Toast.makeText(MainActivity.this,personName +personName,Toast.LENGTH_SHORT).show();
        }

    }*/
}


