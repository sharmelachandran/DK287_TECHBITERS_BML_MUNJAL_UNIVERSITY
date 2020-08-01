package com.ad_sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;//5seconds
    Animation top,bottom;
    ImageView img;
    TextView tv,tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        img=findViewById(R.id.imageView2);
        tv=findViewById(R.id.t1);
        tv1=findViewById(R.id.t2);
        img.setAnimation(top);
        tv.setAnimation(bottom);
        tv1.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Intent i=new Intent(getApplicationContext(),register.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(getApplicationContext(),theme.class);
                    startActivity(i);
                    finish();
                }
            }
        },SPLASH_SCREEN);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
