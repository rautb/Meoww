package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showme extends AppCompatActivity implements View.OnClickListener {
    Button showme;
    TextView inwo,inman,inoth;
    String phone,name,dobt,genderw;
    String ingen="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showme);
        getSupportActionBar().hide();
        showme=(Button)findViewById(R.id.showme);
        inwo=(TextView)findViewById(R.id.inwo);
        inman=(TextView)findViewById(R.id.inman);
        inoth=(TextView)findViewById(R.id.inoth);


        phone = getIntent().getExtras().getString("phone");
        name = getIntent().getExtras().getString("name");
        dobt = getIntent().getExtras().getString("dob");
        genderw = getIntent().getExtras().getString("gender");


        inwo.setOnClickListener(this);
        inman.setOnClickListener(this);
        inoth.setOnClickListener(this);


        showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),about.class);
                i.putExtra("phone", phone);
                i.putExtra("name", name);
                i.putExtra("dob", dobt);
                i.putExtra("gender",genderw);
                i.putExtra("ingen",ingen);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.inwo:
                inwo.getText().toString().trim();
                inwo.setTextColor(Color.BLUE);
                inman.setTextColor(Color.BLACK);
                inoth.setTextColor(Color.BLACK);
                ingen="Woman";
                break;
            case R.id.inman:
                inman.getText().toString().trim();
                inman.setTextColor(Color.BLUE);
                inwo.setTextColor(Color.BLACK);
                inoth.setTextColor(Color.BLACK);
                ingen="Man";
                break;
            case R.id.inoth:
                inoth.getText().toString().trim();
                inoth.setTextColor(Color.BLUE);
                inman.setTextColor(Color.BLACK);
                inwo.setTextColor(Color.BLACK);
                ingen="Others";
                break;
        }
    }

}