package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class arrow extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    ImageButton r,l,u,d;
    TextView tv,ins;
    GifImageView gf;
    Button F,H,tim;
    int wcount=0;
    int flag1=0,flag2=0,flag3=0,flag4=0;
    int a[]={0,0,0,0,0,0,0,0,0,0};
    int f[]={4,1,3,2,4,3,4,2,1,3};
    int i=0,j=0;
    int count=0;
    int fl=0;
    View v1;
    MediaPlayer m;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    String arr[]={"Click LEFT Arrow","Click UP Arrow","Click DOWN Arrow","Click RIGHT Arrow","Click LEFT Arrow","Click DOWN Arrow","Click LEFT Arrow","Click RIGHT Arrow","Click UP Arrow","Click DOWN Arrow"};
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_arrow);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        checkInternetConnection();
        v1=findViewById(R.id.textView);
        tv=(TextView)findViewById(R.id.textView);
        l=(ImageButton)findViewById(R.id.left);
        d=(ImageButton)findViewById(R.id.down);
        u=(ImageButton)findViewById(R.id.up);
        F=(Button)findViewById(R.id.button);
        r=(ImageButton)findViewById(R.id.right);
        ins=findViewById(R.id.ins);
        gf=findViewById(R.id.gifImageView);
        H=findViewById(R.id.h);
        tim=findViewById(R.id.time);
        startTimer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gf.setBackgroundResource(R.drawable.ni2);
                ins.setBackgroundResource(R.drawable.woodbg);
                ins.setText("Follow above shown\ninstructions");
            }
        },3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ins.setBackgroundResource(R.color.grass);
                ins.setText(" ");
                gf.setBackgroundResource(R.drawable.ni1);
            }
        },7000);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();

                Intent i=new Intent(getApplicationContext(),home.class);
                i.putExtra("level",6);
                startActivity(i);unregisterReceiver(connectivityRecevier);
                m.stop();
                finish();
            }
        });
        gf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gf.setBackgroundResource(R.drawable.ni2);
                ins.setBackgroundResource(R.drawable.woodbg);
                ins.setText("Follow above shown\ninstructions");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ins.setBackgroundResource(R.color.grass);
                        ins.setText(" ");
                        gf.setBackgroundResource(R.drawable.ni1);
                    }
                },5000);
            }
        });
        tv.setText(arr[j]);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((flag1 == 0 || flag1==1||flag1==2) && i<10) {
                    final Animation animation= AnimationUtils.loadAnimation(arrow.this,R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    l.startAnimation(animation);
                    flag1=1;
                    a[i]=4;
                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else{
                        tv.setText("FINISH");
                        F.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((flag2 == 0 || flag2==1) && i<10) {
                    final Animation animation=AnimationUtils.loadAnimation(arrow.this,R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    u.startAnimation(animation);
                    flag2=1;
                    a[i]=1;
                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else{
                        tv.setText("FINISH");
                        F.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((flag3 == 0 || flag3==1 ||flag3==2) && i<10) {
                    final Animation animation=AnimationUtils.loadAnimation(arrow.this,R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    d.startAnimation(animation);
                    flag3=1;
                    a[i]=3;
                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else{
                        tv.setText("FINISH");
                        F.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((flag4 == 0||flag4==1) && i<10) {
                    final Animation animation=AnimationUtils.loadAnimation(arrow.this,R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    r.startAnimation(animation);
                    flag4=1;
                    a[i]=2;
                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else{
                        tv.setText("FINISH");
                        F.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(j==10){
                    long time=timeleftmilli;
                    countDownTimer.cancel();
                    storedata(time);
                }
                else
                    Toast.makeText(getApplicationContext(),"Follow the above mentioned instruction",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void checkInternetConnection(){
        boolean isConnected=ConnectivityRecevier.isConnected();
        if(!isConnected){
            changeActivity();
        }
    }
    private void changeActivity(){
        Intent i=new Intent(this,OfflineActivity.class);
        this.onPause();
        i.putExtra("activity","arrow");m.stop();
        startActivity(i);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            changeActivity();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityRecevier,intentFilter);
        MyApp.getInstance().setConnectivityListner(this);
    }
    private void storedata(long time)
    {
        for(i=0;i<10;i++)
        {
            if(f[i]==a[i])
            {
                count++;
            }
            else
            {
                wcount++;
            }
        }
        int seconds=(int)(time/1000)%60;
        int Time_used_to_solve=60-seconds;
        if(count==10)
            Toast.makeText(getApplicationContext(),"CORRECT ANSWER !!! CONGRATULATIONS",Toast.LENGTH_LONG).show();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Nature");
        int MMSE_SCORE=(count*4)/10;
        int ADAS_SCORE=(wcount*5)/10;
        int AD_SCORE=(count*5)/10;
        register.child("MMSE").child("Instruction_following").child("score").setValue(MMSE_SCORE);
        register.child("MMSE").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);
        register.child("AD_Finder").child("Instruction_following").child("score").setValue(AD_SCORE);
        register.child("AD_Finder").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);
        register.child("ADAS").child("Instruction_following").child("score").setValue(ADAS_SCORE);
        register.child("ADAS").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);

        Intent e=new Intent(getApplicationContext(),home.class);
        e.putExtra("level",7);m.stop();
        startActivity(e);unregisterReceiver(connectivityRecevier);
        finish();
    }
    private void startTimer(){
        countDownTimer=new CountDownTimer(timeleftmilli,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftmilli=millisUntilFinished;//updating time left or reducing each second
                updateTimer();
            }

            @Override
            public void onFinish() {
                    F.setVisibility(View.VISIBLE);
               storedata(timeleftmilli);
            }
        }.start();
    }
    private void updateTimer(){
        int seconds=(int)(timeleftmilli/1000)%60;
        String timeformat=String.format(Locale.getDefault(),"%02d",seconds);
        tim.setText(timeformat);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}
