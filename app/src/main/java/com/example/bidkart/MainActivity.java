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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener authStateListener;
    User user,validate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    boolean validated;
    String uid;
    Login_Loading login_loading;

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
        login_loading = new Login_Loading(MainActivity.this);
        login_loading.startLogin_Loading();
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    dbref = firebaseDatabase.getReference("users");
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                validate = snapshot.getValue(User.class);
                                if (validate.getUserId().equals(uid))
                                    validated = true;

                                if (validated) {
                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    if (authStateListener != null)
                                        firebaseAuth.removeAuthStateListener(authStateListener);
                                    startActivity(intent);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else {
                    login_loading.dismissDialog();
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signInButton);
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

        String check_mail;
        final AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        check_mail = acct.getEmail();
        firebaseAuth.fetchSignInMethodsForEmail(check_mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = !task.getResult().getSignInMethods().isEmpty();
                if(check){
                   already_Reg(authCredential);
                }
                else
                    Register(authCredential);
            }
        });
        /*firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    *//*SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                    sp.edit().putBoolean("logged",true).apply();*//*
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
        });*/
    }

    /*private void updateUI(FirebaseUser fuser){
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

    }*/

    private void already_Reg(AuthCredential authCredential){
                if(authStateListener!=null)
                firebaseAuth.removeAuthStateListener(authStateListener);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                SharedPreferences sharedPreferences = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                SharedPreferences.Editor myedit = sharedPreferences.edit();
                myedit.putString("user_id",firebaseUser.getUid());
                myedit.commit();
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);

            }
        });
    }

    private void Register(AuthCredential authCredential){
        if(authStateListener!=null)
        firebaseAuth.removeAuthStateListener(authStateListener);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();
                for (UserInfo profile : fuser.getProviderData()) {
                    // Id of the provider (ex: google.com)
                    //String providerId = profile.getProviderId();

                    // UID specific to the provider
                   // String uid = profile.getUid();
                    String uid=fuser.getUid();
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
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}


