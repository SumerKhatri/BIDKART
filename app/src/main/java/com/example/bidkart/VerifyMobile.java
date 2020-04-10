package com.example.bidkart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyMobile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button VerifyOTP;
    private String mVerificationId;
    private EditText OTP;
    private String Number;
    EditText PhoneNumber;
    private Button SendOTP;
    User user;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile);

        VerifyOTP = findViewById(R.id.btn_verify_otp);
        OTP = findViewById(R.id.ETEnterOTP);
        mAuth = FirebaseAuth.getInstance();
        SendOTP = findViewById(R.id.btn_verify_phone);
        PhoneNumber = findViewById(R.id.ETLoginPhone);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("USER");

        //Toast.makeText(getApplicationContext(),Number, Toast.LENGTH_LONG).show();



        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = PhoneNumber.getText().toString();
                validNo(Number);
                sendVerificationCode(Number);
                //Toast.makeText(VerifyMobile.this,Number,Toast.LENGTH_LONG).show();

            }
        });

        VerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = OTP.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    OTP.setError("Enter valid code");
                    OTP.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });

    }

    private void validNo(String no){
        if(no.isEmpty() || no.length() < 10){
            PhoneNumber.setError("Enter a valid mobile");
            PhoneNumber.requestFocus();
            return;
        }
    }

    private void sendVerificationCode(String Number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + Number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                OTP.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyMobile.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            /*SharedPreferences sp = getSharedPreferences("My_Shared_Pref",MODE_PRIVATE);
                            sp.edit().putBoolean("logged",true).apply();*/
                            user.setNumber(Number);
                            Intent intent = new Intent(VerifyMobile.this, Profile.class);
                            intent.putExtra("USER",user);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }


                        }
                    }
                });
    }
}
