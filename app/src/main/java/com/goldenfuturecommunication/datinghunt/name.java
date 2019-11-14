package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class name extends AppCompatActivity {

    Button name;
    EditText fname,lname;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        getSupportActionBar().hide();
        name=(Button)findViewById(R.id.name);
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);

        phone=getIntent().getExtras().getString("phone");

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (fname.getText().toString().trim().equals("")){
                    fname.setError("Please enter your First name");
                    fname.requestFocus();
                }else if (lname.getText().toString().trim().equals("")){
                    lname.setError("Please enter your Least name");
                    lname.requestFocus();
                }else {
                    Intent i=new Intent(getApplicationContext(),dob.class);
                    i.putExtra("phone",phone);
                    i.putExtra("name",fname.getText().toString().trim()+" "+lname.getText().toString().trim());
                    startActivity(i);
                }
            }
        });
    }

}