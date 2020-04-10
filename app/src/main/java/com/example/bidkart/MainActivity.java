package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private Button Test_login;
    private String TAG = "MainActivity";
    private int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener authStateListener;
    User user;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        /*SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
        }*/
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Intent intent = new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signInButton = findViewById(R.id.signInButton);

        Test_login = findViewById(R.id.btn_test_login);
        firebaseAuth = FirebaseAuth.getInstance();


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

        String uid;
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    /*SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                    sp.edit().putBoolean("logged",true).apply();*/
                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    firebaseAuth.fetchSignInMethodsForEmail(firebaseUser.getEmail()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();

                            if(check)
                            {
                                SharedPreferences sharedPreferences = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                                SharedPreferences.Editor myedit = sharedPreferences.edit();
                                myedit.putString("user_id",firebaseUser.getUid());
                                myedit.commit();
                                already_Reg();
                            }
                            else
                                updateUI(firebaseUser);
                        }
                    });

                    //Toast.makeText(MainActivity.this,"Signed In Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Sign In Not Successful",Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fuser){
        for (UserInfo profile : fuser.getProviderData()) {
            // Id of the provider (ex: google.com)
            //String providerId = profile.getProviderId();

            // UID specific to the provider
            String uid = profile.getUid();

            // Name, email address, and profile photo Url
            String name = profile.getDisplayName();
            String email = profile.getEmail();
            user = new User(uid,name,email);
            if(profile.getPhotoUrl()!=null)
            {
                String photoUrl = profile.getPhotoUrl().toString();
                user.setProfilepic(photoUrl);
            }

            Intent intent = new Intent(MainActivity.this,VerifyMobile.class);
            intent.putExtra("USER",user);
            startActivity(intent);
        }

    }

    private void already_Reg(){


        Intent intent = new Intent(MainActivity.this,Home.class);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}


