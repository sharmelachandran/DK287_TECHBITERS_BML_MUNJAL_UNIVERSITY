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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Locale;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class cmaze extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    Button p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,H,tim;
    Button w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13,w14,w15,w16,w17,w18,w19,w20,w21,w22,w23,w24,w25,w26,w27,w28,w29,w30,w31,w32,w33;
    ImageView c,m,c1;
    private static final long START_TIME=120000;//in milliseconds 120000/60=2mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    //ProgressBar progressBar;
    //chomeWatcher mchomeWatcher;
    TextView tv,tvm;
    int count=0;
    int flag[]=new int[29];
    int wflag[]=new int [34];
    int wcount=0;
    View v1,v2;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    MediaPlayer M;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cmaze);
        M= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        M.start();
        checkInternetConnection();
        v1=findViewById(R.id.time);
        v2=findViewById(R.id.imgc);
        hint();
        Arrays.fill(flag,0);
        Arrays.fill(wflag,0);
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startFlash();
            }
        },300);

        tv=findViewById(R.id.textView);
        tvm=findViewById(R.id.textView2);
        tim=findViewById(R.id.time);
        c=findViewById(R.id.imgc);
        m=findViewById(R.id.imgm);
        c1=findViewById(R.id.imggc);
        p0=findViewById(R.id.v7);
        p1=findViewById(R.id.v17);
        p2=findViewById(R.id.v16);
        p3=findViewById(R.id.v15);
        p4=findViewById(R.id.v25);
        p5=findViewById(R.id.v35);
        p6=findViewById(R.id.v45);
        p7=findViewById(R.id.v44);
        p8=findViewById(R.id.v43);
        p9=findViewById(R.id.v33);
        p10=findViewById(R.id.v32);
        p11=findViewById(R.id.v31);
        p12=findViewById(R.id.v41);
        p13=findViewById(R.id.v51);
        p14=findViewById(R.id.v61);
        p15=findViewById(R.id.v62);
        p16=findViewById(R.id.v63);
        p17=findViewById(R.id.v64);
        p18=findViewById(R.id.v65);
        p19=findViewById(R.id.v66);
        p20=findViewById(R.id.v67);
        p21=findViewById(R.id.v77);
        p22=findViewById(R.id.v87);
        p23=findViewById(R.id.v86);
        p24=findViewById(R.id.v96);
        p25=findViewById(R.id.v106);
        p26=findViewById(R.id.v116);
        p27=findViewById(R.id.v126);
        w1=findViewById(R.id.v46);
        w2=findViewById(R.id.v47);
        w3=findViewById(R.id.v37);
        w4=findViewById(R.id.v38);
        w5=findViewById(R.id.v39);
        w6=findViewById(R.id.v29);
        w7=findViewById(R.id.v19);
        w8=findViewById(R.id.v23);
        w9=findViewById(R.id.v13);
        w10=findViewById(R.id.v12);
        w11=findViewById(R.id.v11);
        w12=findViewById(R.id.v71);
        w13=findViewById(R.id.v81);
        w14=findViewById(R.id.v82);
        w15=findViewById(R.id.v92);
        w16=findViewById(R.id.v74);
        w17=findViewById(R.id.v84);
        w18=findViewById(R.id.v68);
        w19=findViewById(R.id.v69);
        w20=findViewById(R.id.v59);
        w21=findViewById(R.id.v88);
        w22=findViewById(R.id.v89);
        w23=findViewById(R.id.v99);
        w24=findViewById(R.id.v109);
        w25=findViewById(R.id.v108);
        w26=findViewById(R.id.v118);
        w27=findViewById(R.id.v105);
        w28=findViewById(R.id.v104);
        w29=findViewById(R.id.v114);
        w30=findViewById(R.id.v113);
        w31=findViewById(R.id.v112);
        w32=findViewById(R.id.v111);
        w33=findViewById(R.id.v101);
        H=findViewById(R.id.h);
        builder = new GuideView.Builder(this)
                .setTitle("Timer")
                .setContentText("You have 2:00 mins to\nfinish this level")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(v1)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        switch (view.getId()) {
                            case R.id.time:
                                builder.setTitle("Task").setContentText("Help me to reach FINISH line\nclear maze by block by block").setTargetView(v2).build();
                                break;
                            case R.id.imgc:
                                startTimer();
                                return;
                        }
                        mGuideView = builder.build();
                        mGuideView.show();
                    }
                });

        mGuideView = builder.build();
        mGuideView.show();
        updatingForDynamicLocationViews();
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent i=new Intent(getApplicationContext(),chome.class);
                i.putExtra("level",2);M.stop();
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        tv.setText("Guide me to reach FINISH Line");
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        p0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[0]==0)
                {
                    flag[0]=1;
                    p0.setBackgroundResource(R.drawable.gdown);
                }
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[0]==1) {
                    flag[1] = 1;
                    p1.setBackgroundResource(R.drawable.gdown);
                }

            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[1]==1) {
                    flag[2] = 1;
                    p2.setBackgroundResource(R.drawable.gleft);
                    p1.setBackgroundResource(R.drawable.gleft);
                }

                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[2]==1)
                {
                    flag[3]=1;
                    p3.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[3]==1)
                {
                    flag[4]=1;
                    p4.setBackgroundResource(R.drawable.gdown);
                    p3.setBackgroundResource(R.drawable.gdown);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[4]==1)
                {
                    flag[5]=1;
                    p5.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[5]==1)
                {
                    flag[6]=1;
                    p6.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[6]==1)
                {
                    flag[7]=1;
                    p7.setBackgroundResource(R.drawable.gleft);
                    p6.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[7]==1)
                {
                    flag[8]=1;
                    p8.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[8]==1)
                {
                    flag[9]=1;
                    p9.setBackgroundResource(R.drawable.gup);
                    p8.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[9]==1)
                {
                    flag[10]=1;
                    p10.setBackgroundResource(R.drawable.gleft);
                    p9.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[10]==1)
                {
                    flag[11]=1;
                    p11.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[11]==1)
                {
                    flag[12]=1;
                    p12.setBackgroundResource(R.drawable.gdown);
                    p11.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[12]==1)
                {
                    flag[13]=1;
                    p13.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[13]==1)
                {
                    flag[14]=1;
                    p14.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[14]==1)
                {
                    flag[15]=1;
                    p15.setBackgroundResource(R.drawable.gri8);
                    p14.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[15]==1)
                {
                    flag[16]=1;
                    p16.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[16]==1)
                {
                    flag[17]=1;
                    p17.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[17]==1)
                {
                    flag[18]=1;
                    p18.setBackgroundResource(R.drawable.gri8);
                    p17.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[18]==1)
                {
                    flag[19]=1;
                    p19.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[19]==1)
                {
                    flag[20]=1;
                    p20.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[20]==1)
                {
                    flag[21]=1;
                    p21.setBackgroundResource(R.drawable.gdown);
                    p20.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[21]==1)
                {
                    flag[22]=1;
                    p22.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[22]==1)
                {
                    flag[23]=1;
                    p23.setBackgroundResource(R.drawable.gleft);
                    p22.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[23]==1)
                {
                    flag[24]=1;
                    p24.setBackgroundResource(R.drawable.gdown);
                    p23.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[24]==1)
                {
                    flag[25]=1;
                    p25.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[25]==1)
                {
                    flag[26]=1;
                    p26.setBackgroundResource(R.drawable.gdown);
                    p25.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        p27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[26]==1)
                {
                    flag[27]=1;
                    p27.setBackgroundResource(R.drawable.gdown);
                    c1.setBackgroundResource(R.drawable.srunner);
                    c.setBackgroundResource(R.color.grass);
                    tvm.setText("Thankyou soo much!!!\n Click here for next level");
                    startanim();
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"HURRAY!!!!YOU WON",Toast.LENGTH_SHORT).show();
                storedata();
            }
        });
        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[6]==1)
                {
                    wcount++;
                    wflag[1]=1;
                    w1.setBackgroundResource(R.drawable.gri8);
                    p6.setBackgroundResource(R.drawable.gri8);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[1]==1)
                {
                    wflag[2]=1;
                    w2.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[2]==1)
                {
                    wflag[3]=1;
                    w3.setBackgroundResource(R.drawable.gup);
                    w2.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[3]==1)
                {

                    wflag[4]=1;
                    w4.setBackgroundResource(R.drawable.gri8);
                    w3.setBackgroundResource(R.drawable.gri8);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[4]==1)
                {
                    wflag[5]=1;
                    w5.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[5]==1)
                {
                    wflag[6]=1;
                    w6.setBackgroundResource(R.drawable.gup);
                    w5.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[6]==1)
                {
                    wflag[7]=1;
                    w7.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[9]==1)
                {
                    wcount++;
                    wflag[8]=1;
                    w8.setBackgroundResource(R.drawable.gup);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[8]==1)
                {
                    wflag[9]=1;
                    w9.setBackgroundResource(R.drawable.gup);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[9]==1)
                {
                    wflag[10]=1;
                    w10.setBackgroundResource(R.drawable.gleft);
                    w9.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[10]==1)
                {
                    wflag[11]=1;
                    w11.setBackgroundResource(R.drawable.gleft);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[14]==1)
                {
                    wcount++;
                    wflag[12]=1;
                    w12.setBackgroundResource(R.drawable.gdown);

                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[12]==1)
                {
                    wflag[13]=1;
                    w13.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[13]==1)
                {
                    wflag[14]=1;
                    w14.setBackgroundResource(R.drawable.gri8);
                    w13.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[14]==1)
                {
                    wflag[15]=1;
                    w15.setBackgroundResource(R.drawable.gdown);
                    w14.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[17]==1)
                {
                    wcount++;
                    wflag[16]=1;
                    w16.setBackgroundResource(R.drawable.gdown);
                    p17.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[16]==1)
                {
                    wflag[17]=1;
                    w17.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[20]==1)
                {
                    wcount++;
                    wflag[18]=1;
                    w18.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[18]==1)
                {
                    wflag[19]=1;
                    w19.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[19]==1)
                {
                    wflag[20]=1;
                    w20.setBackgroundResource(R.drawable.gup);
                    w19.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[22]==1)
                {
                    wcount++;
                    wflag[21]=1;
                    w21.setBackgroundResource(R.drawable.gri8);
                    p22.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[21]==1)
                {
                    wflag[22]=1;
                    w22.setBackgroundResource(R.drawable.gri8);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[22]==1)
                {
                    wflag[23]=1;
                    w23.setBackgroundResource(R.drawable.gdown);
                    w22.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[23]==1)
                {
                    wflag[24]=1;
                    w24.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[24]==1)
                {
                    wflag[25]=1;
                    w25.setBackgroundResource(R.drawable.gleft);
                    w24.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[25]==1)
                {
                    wflag[26]=1;
                    w26.setBackgroundResource(R.drawable.gdown);
                    w25.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[25]==1)
                {
                    wcount++;
                    wflag[27]=1;
                    w27.setBackgroundResource(R.drawable.gleft);
                    p25.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[27]==1)
                {
                    wflag[28]=1;
                    w28.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[28]==1)
                {
                    wflag[29]=1;
                    w29.setBackgroundResource(R.drawable.gdown);
                    w28.setBackgroundResource(R.drawable.gdown);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[29]==1)
                {
                    wflag[30]=1;
                    w30.setBackgroundResource(R.drawable.gleft);
                    w29.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[30]==1)
                {
                    wflag[31]=1;
                    w31.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[31]==1)
                {
                    wflag[32]=1;
                    w32.setBackgroundResource(R.drawable.gleft);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        w33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wflag[32]==1)
                {
                    wflag[33]=1;
                    w33.setBackgroundResource(R.drawable.gup);
                    w32.setBackgroundResource(R.drawable.gup);
                }
                else{
                    Toast.makeText(cmaze.this, "1st Clear the path before this!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updatingForDynamicLocationViews() {
        v2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }
    void hint(){
        new TTFancyGifDialog.Builder(cmaze.this)
                .setTitle("Instruction")
                .setMessage("Guide the runner to reach finish line\nClear the maze by clicking\nblock by block")
                .setPositiveBtnText("GOT IT")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.smazeinstruction)      //pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(),"Level 2 starts",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
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
        i.putExtra("activity","cmaze");
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

    private  void storedata(){
        int minutes=(int)(timeleftmilli/1000)/60;
        int seconds=(minutes*60)+(int)(timeleftmilli/1000)%60;
        int Time_used_to_solve=120-seconds;
        countDownTimer.cancel();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Sports");
        int adas_wcount=(wcount*5)/7;
        int adfinder_count=7-wcount;
        register.child("ADAS").child("Maze").child("Wrong_attempt").setValue(adas_wcount);
        register.child("ADAS").child("Maze").child("Time_taken").setValue(Time_used_to_solve);
        register.child("AD_Finder").child("Maze").child("Wrong_attempt_Ignored").setValue(adfinder_count);
        register.child("AD_Finder").child("Maze").child("Time_taken").setValue(Time_used_to_solve);
        Intent i=new Intent(getApplicationContext(),chome.class);M.stop();
        i.putExtra("level",3);
        startActivity(i);unregisterReceiver(connectivityRecevier);finish();
    }
    private  void startAnimation(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        tv.startAnimation(animation);

    }
    void startFlash()
    {
        Animation animation=new AlphaAnimation(1,0);
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.INFINITE);
        c.startAnimation(animation);

    }
    private  void startanim(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);

        tvm.startAnimation(animation);
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
                storedata();
            }
        }.start();
    }
    private void updateTimer(){
        int minutes=(int)(timeleftmilli/1000)/60;
        int seconds=(int)(timeleftmilli/1000)%60;
        String timeformat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tim.setText(timeformat);
    }
    /*public void prog()
    {
        progressBar=findViewById(R.id.progressBar);
        final Timer t=new Timer();

        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if(count==100)
                {
                    t.cancel();
                    Intent i=new Intent(getApplicationContext(),chome.class);
                    i.putExtra("level",3);
                    startActivity(i);
                }
            }
        };
        t.schedule(tt,0,700); //Time seconds(700)
    }*/
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press home to go back",Toast.LENGTH_SHORT).show();
    }
}
