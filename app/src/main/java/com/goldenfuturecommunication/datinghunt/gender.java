package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gender extends AppCompatActivity implements View.OnClickListener {

    Button gender;
    TextView woman,man,other;
    String phone,name,dobt;
    String genderw="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        getSupportActionBar().hide();

        phone = getIntent().getExtras().getString("phone");
        name = getIntent().getExtras().getString("name");
        dobt = getIntent().getExtras().getString("dob");


        woman=(TextView)findViewById(R.id.woman);
        man=(TextView)findViewById(R.id.man);
        other=(TextView)findViewById(R.id.other);

        gender=(Button)findViewById(R.id.gender);


        woman.setOnClickListener(this);
        man.setOnClickListener(this);
        other.setOnClickListener(this);


        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),showme.class);
                i.putExtra("phone", phone);
                i.putExtra("name", name);
                i.putExtra("dob", dobt);
                i.putExtra("gender",genderw);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.woman:
                woman.getText().toString().trim();
                woman.setTextColor(Color.BLUE);
                man.setTextColor(Color.BLACK);
                other.setTextColor(Color.BLACK);
                genderw="Woman";
                break;
            case R.id.man:
                man.getText().toString().trim();
                man.setTextColor(Color.BLUE);
                woman.setTextColor(Color.BLACK);
                other.setTextColor(Color.BLACK);
                genderw="Man";
                break;
            case R.id.other:
                other.getText().toString().trim();
                other.setTextColor(Color.BLUE);
                man.setTextColor(Color.BLACK);
                woman.setTextColor(Color.BLACK);
                genderw="Others";
                break;
        }
    }
}