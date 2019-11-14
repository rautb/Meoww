package com.goldenfuturecommunication.datinghunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_otp extends AppCompatActivity {

    String phone,purpose;
    private FirebaseAuth mAuth;
    EditText otp;
    Button submit;
    TextView resend,tv1;
    private Spinner spinner;
    private String verificationid;
    String login_status="No";
    String login_phone="Null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();
        phone = getIntent().getExtras().getString("ph");
        purpose ="register";// getIntent().getExtras().getString("purpose");

        tv1=(TextView)findViewById(R.id.tv1);

        tv1.setText("Enter the OTP that was sent to\n"+phone);

        otp=(EditText)findViewById(R.id.otp);
        resend=(TextView) findViewById(R.id.resend);
        submit=(Button)findViewById(R.id.sentotp);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        sendVerificationCode("+91"+phone);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    otp.setError("Enter code...");
                    otp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode("+91"+phone);
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
        //overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            SharedPreferences sp = getSharedPreferences(login_status, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("loginStatus", "Yes");
                            editor.apply();

                            SharedPreferences sp2 = getSharedPreferences(login_phone, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sp2.edit();
                            editor2.putString("loginPhone", phone);
                            editor2.apply();





                            if (purpose.equals("register")) {
                                Intent intent = new Intent(getApplicationContext(), rules.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                            }else if (purpose.equals("login")) {
                                Intent intent = new Intent(getApplicationContext(), home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                            }
                            // overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                            // progressDialog.hide();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }
    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                //  progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
                // progressDialog.hide();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            // progressDialog.hide();
        }
    };



}