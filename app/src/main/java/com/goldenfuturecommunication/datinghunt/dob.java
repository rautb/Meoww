package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class dob extends AppCompatActivity {

    Button dob;
    String phone,name;
    EditText dobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob);
        getSupportActionBar().hide();

        dob=(Button)findViewById(R.id.dob);
        dobe=(EditText)findViewById(R.id.dobt);

        phone = getIntent().getExtras().getString("phone");
        name = getIntent().getExtras().getString("name");
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dobe.getText().toString().trim().equals("")) {
                    dobe.setError("Please enter your Date of Birth");
                    dobe.requestFocus();
                } else {
                    Intent i = new Intent(getApplicationContext(), gender.class);
                    i.putExtra("phone", phone);
                    i.putExtra("name", name);
                    i.putExtra("dob", dobe.getText().toString().trim());
                    startActivity(i);
                }
            }
        });
    }
}
