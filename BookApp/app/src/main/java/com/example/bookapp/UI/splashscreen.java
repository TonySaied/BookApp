package com.example.bookapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookapp.R;

public class splashscreen extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    Animation topAnim,bottomAnim;
    TextView logo;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.logoImg);
        logo=findViewById(R.id.logoText);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

       new Handler().postDelayed(new Runnable()
       {
           @Override
           public void run() {
               Intent i=new Intent(splashscreen.this, MainActivity.class);
               startActivity(i);
               finish();
           }
       },SPLASH_SCREEN);
    }
}