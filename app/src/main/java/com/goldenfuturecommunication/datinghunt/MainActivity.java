package com.goldenfuturecommunication.datinghunt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView=(TextView)findViewById(R.id.textView);
        imageView2=(ImageView)findViewById(R.id.imageView2);

        Typeface cf=Typeface.createFromAsset(getAssets(),"fonts/Lobster_1.3.otf");
        textView.setTypeface(cf);

        setAnimation();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(),activitymain2.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        },5000);



    }
    public  void setAnimation(){
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        imageView2.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
