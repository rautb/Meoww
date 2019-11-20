package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class about extends AppCompatActivity {

    Button about;
    String phone,name,dobt,genderw,ingen;
    EditText aboutu;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        aboutu=(EditText)findViewById(R.id.aboutu);

        pb=(ProgressBar)findViewById(R.id.pb);

        phone = getIntent().getExtras().getString("phone");
        name = getIntent().getExtras().getString("name");
        dobt = getIntent().getExtras().getString("dob");
        genderw = getIntent().getExtras().getString("gender");
        ingen = getIntent().getExtras().getString("ingen");



        about=(Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.success(getApplicationContext(), "Registration Success!", Toast.LENGTH_SHORT, true).show();

                        Intent i=new Intent(getApplicationContext(),photo.class);
                        i.putExtra("phone", phone);
                        i.putExtra("name", name);
                        i.putExtra("dob", dobt);
                        i.putExtra("gender",genderw);
                        i.putExtra("ingen",ingen);
                        i.putExtra("about",aboutu.getText().toString().trim());
                        startActivity(i);

                    }
                },3000);

            }
        });
    }
}
