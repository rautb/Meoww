package com.goldenfuturecommunication.datinghunt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class photo extends AppCompatActivity {

    Button done,img_up;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    ImageView pesimg;
    List<List_Data_sl> list_data_sl;
    String url="";
    String phone,name,dobt,genderw,ingen,aboutus;
    ProgressBar pb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getSupportActionBar().hide();

        list_data_sl=new ArrayList<>();


        pb=(ProgressBar)findViewById(R.id.pb);
        phone = getIntent().getExtras().getString("phone");
        name = getIntent().getExtras().getString("name");
        dobt = getIntent().getExtras().getString("dob");
        genderw = getIntent().getExtras().getString("gender");
        ingen = getIntent().getExtras().getString("ingen");
        aboutus = getIntent().getExtras().getString("about");
        pesimg=(ImageView)findViewById(R.id.pesimg);

        done=(Button)findViewById(R.id.done);
        img_up=(Button) findViewById(R.id.img_up);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent i = new Intent(getApplicationContext(), home.class);
                        imgupload iu = new imgupload();
                        new Thread(iu).start();
                        startActivity(i);

            }
        });

        img_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                        showOptions();
                    }

        });

    }


    //select image .........................................................
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Camera
        try {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            pesimg.setImageBitmap(bitmap);
            // Uri uri = data.getData();
            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        }catch (Exception e){
            e.printStackTrace();
        }





        // Gallery

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                pesimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openCamera(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestCameraPermission();
        }else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
            startActivityForResult(intent, 1);
        }
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebytes= outputStream.toByteArray();
        //return Base64.encodeToString(imagebytes,Base64.DEFAULT);

        String encodedImage= Base64.encodeToString(imagebytes,Base64.DEFAULT);
        return  encodedImage;
    }

    public void showOptions(){
        new MaterialDialog.Builder(this)
                .title("Choose Image")
                .items(R.array.uploadImages)
                .itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                break;
                            case 1:
                                openCamera();
                                break;
                            case 2:
                                pesimg.setImageResource(R.drawable.ic_wallpaper_black_24dp);
                                break;
                        }
                    }
                })
                .show();
    }
    class imgupload implements Runnable{
        @Override
        public void run() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(photo.this, response+"", Toast.LENGTH_SHORT).show();

//                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(photo.this, "Success", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params=new HashMap<>();

                    String imageData=imageToString(bitmap);
                    params.put("image", imageData);

                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
//
