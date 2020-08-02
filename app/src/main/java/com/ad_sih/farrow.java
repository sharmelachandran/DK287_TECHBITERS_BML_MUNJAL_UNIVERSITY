package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class farrow extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    ImageButton r,l,u,d;
    TextView tv;
    Button F,H,tim;
    int c=0;
    int wcount=0;
    //ProgressBar progressBar;
    int flag1=0,flag2=0,flag3=0,flag4=0;
    int a[]={0,0,0,0,0,0,0,0,0,0};
    int f[]={4,2,3,1,2,4,3,1,4,3};
    int i=0,j=0;
    int count=0;
    int fl=0;
    View v1;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    String arr[]={"Click LEFT Arrow","Click RIGHT Arrow","Click DOWN Arrow","Click UP Arrow","Click RIGHT Arrow","Click LEFT Arrow","Click DOWN Arrow","Click UP Arrow","Click LEFT Arrow","Click DOWN Arrow"};

    //String arr[]={"Click LEFT Arrow","Click UP Arrow","Click DOWN Arrow","Click RIGHT Arrow","Click LEFT Arrow","Click DOWN Arrow","Click LEFT Arrow","Click RIGHT Arrow","Click UP Arrow","Click DOWN Arrow"};
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_farrow);
        checkInternetConnection();
        v1=findViewById(R.id.textView);
        tv=(TextView)findViewById(R.id.textView);
        l=(ImageButton)findViewById(R.id.left);
        d=(ImageButton)findViewById(R.id.down);
        u=(ImageButton)findViewById(R.id.up);
        F=(Button)findViewById(R.id.button);
        r=(ImageButton)findViewById(R.id.right);
        H=findViewById(R.id.h);
        tim=findViewById(R.id.time);
        builder = new GuideView.Builder(this)
                .setTitle("Instruction")
                .setContentText("Follow the set of instructions given here\nwithin 1minute")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(v1)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (view.getId() == R.id.textView) {
                            startTimer();
                            return;
                        }
                    }
                });
        mGuideView = builder.build();
        mGuideView.show();
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), fhome.class);
                i.putExtra("level",6);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        tv.setText(arr[j]);
        //prog();
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((flag1 == 0 || flag1==1||flag1==2) && i<10) {
                    final Animation animation= AnimationUtils.loadAnimation(farrow.this, R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    l.startAnimation(animation);
                    flag1=1;
                    a[i]=4;
                    //Toast.makeText(getApplicationContext(),+a[i]+"left",Toast.LENGTH_LONG).show();
                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else
                    {
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
                    final Animation animation=AnimationUtils.loadAnimation(farrow.this, R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    u.startAnimation(animation);
                    flag2=1;
                    a[i]=1;
                    //Toast.makeText(getApplicationContext(),+a[i]+"up",Toast.LENGTH_LONG).show();

                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else
                    {
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
                    final Animation animation=AnimationUtils.loadAnimation(farrow.this, R.anim.pluse);
                    MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
                    animation.setInterpolator(interpolator);
                    d.startAnimation(animation);
                    flag3=1;
                    a[i]=3;
                    // Toast.makeText(getApplicationContext(),+a[i]+"down",Toast.LENGTH_LONG).show();

                    i++;
                    j++;
                    fl++;
                    if(j<10)
                        tv.setText(arr[j]);
                    else
                    {
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
                    final Animation animation=AnimationUtils.loadAnimation(farrow.this, R.anim.pluse);
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
                    else
                    {
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
        i.putExtra("activity","farrow");
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
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Food");
        int MMSE_SCORE=(count*4)/10;
        int ADAS_SCORE=(wcount*5)/10;
        int AD_SCORE=(count*5)/10;
        register.child("MMSE").child("Instruction_following").child("score").setValue(MMSE_SCORE);
        register.child("MMSE").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);
        register.child("AD_Finder").child("Instruction_following").child("score").setValue(AD_SCORE);
        register.child("AD_Finder").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);
        register.child("ADAS").child("Instruction_following").child("score").setValue(ADAS_SCORE);
        register.child("ADAS").child("Instruction_following").child("Time_taken").setValue(Time_used_to_solve);
        Intent e=new Intent(getApplicationContext(),fhome.class);
        e.putExtra("level",7);
        startActivity(e);unregisterReceiver(connectivityRecevier);finish();
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
                storedata(timeleftmilli);
            }
        }.start();
    }
    private void updateTimer(){
        //int minutes=(int)(timeleftmilli/1000)/60;
        int seconds=(int)(timeleftmilli/1000)%60;
        String timeformat=String.format(Locale.getDefault(),"%02d",seconds);
        tim.setText(timeformat);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}
