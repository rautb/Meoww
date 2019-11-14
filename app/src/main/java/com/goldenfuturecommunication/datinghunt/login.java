package com.goldenfuturecommunication.datinghunt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class login extends AppCompatActivity {
    Button nextstep;
    EditText editText;
    String login_status="No";
    String login_phone="Null";
    String url="/check_ph.php";
    List<List_Data_cp> list_data_cp;
    boolean f=false;
    ProgressDialog progressDialog;
    String[] phcheck=new String[500];
    int ln;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        list_data_cp=new ArrayList<>();

        editText=(EditText)findViewById(R.id.editText);
        nextstep=(Button)findViewById(R.id.nextstep);
        nextstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (editText.getText().toString().trim().equals("")){
//                    editText.setError("Please enter your mobile number");
//                }else if (editText.getText().toString().length()!=10){
//                    editText.setError("Please enter a valid mobile number");
//                }else {
//                   progressDialog.show();
//                    check_ph cp=new check_ph();
//                    new Thread(cp).start();
//
//                }
                Intent i=new Intent(getApplicationContext(),verify_otp.class);
               i.putExtra("ph",editText.getText().toString().trim());
                startActivity(i);
            }
        });

//        SharedPreferences b=getSharedPreferences(login_phone, Context.MODE_PRIVATE);
//        String p=b.getString("loginPhone","No");



//        SharedPreferences a=getSharedPreferences(login_status, Context.MODE_PRIVATE);
//        String v=a.getString("loginStatus","No");
//        if (v.equals("Yes")){
//            Intent i=new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(i);
//            i.putExtra("ph",p);
//            finish();
//       }

    }

    class check_ph implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray array=jsonObject.getJSONArray("data");
                        for (int i=0; i<array.length(); i++ ){
                            JSONObject ob=array.getJSONObject(i);
                            List_Data_cp listData=new List_Data_cp(ob.getString("phone_no"));
                            list_data_cp.add(listData);
                            phcheck[i]=listData.getPhone();
                            ln=array.length();
                        }
                        f=false;
                        for (int j=0;j<ln;j++){
                            if (phcheck[j].equals(editText.getText().toString().trim())){
                                f=true;
                            }

                        }

                        if (f){
//                            progressDialog.dismiss();
//
                            Intent intent=new Intent(getApplicationContext(),verify_otp.class);
                            intent.putExtra("ph",editText.getText().toString().trim());
                            intent.putExtra("purpose","login");
                            startActivity(intent);

                        }else{
                            progressDialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),verify_otp.class);
                            intent.putExtra("ph",editText.getText().toString().trim());
                            intent.putExtra("purpose","register");
                            startActivity(intent);
                            showdialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Some error occurred!",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("phone",editText.getText().toString().trim());
                    return params;

                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void showdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("This number is not registered!");
        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(),verify_otp.class);
                intent.putExtra("ph",editText.getText().toString().trim());
                startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }



}
