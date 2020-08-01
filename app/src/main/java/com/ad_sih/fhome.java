package com.ad_sih;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fhome extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    int level,f1=0,f2=0,f3=0,f4=0,f5=0,f6=0,f7=0,f8=0,f9=0;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,back,settings;
    Handler h=new Handler();
    Context context;
    MediaPlayer ring;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fhome);
        checkInternetConnection();
        context=getApplicationContext();
       ring= MediaPlayer.create(context, R.raw.level);
        level=getIntent().getIntExtra("level",1);
        b1=findViewById(R.id.l1);
        b2=findViewById(R.id.l2);
        b3=findViewById(R.id.l3);
        b4=findViewById(R.id.l4);
        b5=findViewById(R.id.l5);
        b6=findViewById(R.id.l6);
        b7=findViewById(R.id.l7);
        b8=findViewById(R.id.l8);
        b9=findViewById(R.id.l9);
        back=findViewById(R.id.theme);
        settings=findViewById(R.id.set);
        storedata();
        //imagesetting
        if(level==1)
        {
            ring.start();
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b1.setBackgroundResource(R.drawable.f1);
                    f1=1;
                    startanim(b1);
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==2){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;

            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b2.setBackgroundResource(R.drawable.f2);
                    startanim(b2);
                    f2=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==3){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b3.setBackgroundResource(R.drawable.f3);
                    startanim(b3);
                    f3=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==4){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            b3.setBackgroundResource(R.drawable.f3);f3=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b4.setBackgroundResource(R.drawable.f4);
                    startanim(b4);
                    f4=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==5){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
           // b3.setBackgroundResource(R.drawable.f3);f3=2;
            //b4.setBackgroundResource(R.drawable.f4);f4=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b5.setBackgroundResource(R.drawable.f5);
                    startanim(b5);
                    f5=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==6){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            b3.setBackgroundResource(R.drawable.f3);f3=2;
            b4.setBackgroundResource(R.drawable.f4);f4=2;
            b5.setBackgroundResource(R.drawable.f5);f5=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b6.setBackgroundResource(R.drawable.f6);
                    startanim(b6);
                    f6=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==7){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            b3.setBackgroundResource(R.drawable.f3);f3=2;
            b4.setBackgroundResource(R.drawable.f4);f4=2;
            b5.setBackgroundResource(R.drawable.f5);f5=2;
            b6.setBackgroundResource(R.drawable.f6);f6=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b7.setBackgroundResource(R.drawable.f7);
                    startanim(b7);
                    f7=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==8){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            b3.setBackgroundResource(R.drawable.f3);f3=2;
            b4.setBackgroundResource(R.drawable.f4);f4=2;
            b5.setBackgroundResource(R.drawable.f5);f5=2;
            b6.setBackgroundResource(R.drawable.f6);f6=2;
            b7.setBackgroundResource(R.drawable.f7);f7=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b8.setBackgroundResource(R.drawable.f8);
                    startanim(b8);
                    f8=1;
                }
            };
            h.postDelayed(r1,2000);
        }
        else if(level==9){
            ring.start();
            b1.setBackgroundResource(R.drawable.f1);f1=2;
            b2.setBackgroundResource(R.drawable.f2);f2=2;
            b3.setBackgroundResource(R.drawable.f3);f3=2;
            b4.setBackgroundResource(R.drawable.f4);f4=2;
            b5.setBackgroundResource(R.drawable.f5);f5=2;
            b6.setBackgroundResource(R.drawable.f6);f6=2;
            b7.setBackgroundResource(R.drawable.f7);f7=2;
            b8.setBackgroundResource(R.drawable.f8);f8=2;
            Runnable r1=new Runnable() {
                @Override
                public void run() {
                    b9.setBackgroundResource(R.drawable.f9);
                    startanim(b9);
                    f9=1;
                }
            };
            h.postDelayed(r1,2000);
        }


        //setonclicklisteners
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), theme.class);
                startActivity(i);unregisterReceiver(connectivityRecevier);
                finish();
                ring.pause();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f1==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), Food.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }
                if(f1==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f2==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), fmaze.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f2==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f3==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), clockdrawing.class);
                    i.putExtra("theme","food");
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f3==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f4==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), jumble1.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f4==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f5==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), frecall.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f5==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });/*
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f6==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(),farrow.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f6==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f7==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(),fnumbercross.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f7==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f8==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), diagram.class);
                    i.putExtra("theme","food");
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f8==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f9==1) {
                    ring.pause();
                    Intent i = new Intent(getApplicationContext(), fobject.class);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(f9==2){
                    Toast.makeText(getApplicationContext(),"Level Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

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
        i.putExtra("activity","fhome");
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
    private void storedata(){
        final Long[] s = new Long[1];
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid());
        register.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s[0] = (Long) snapshot.child("score").getValue();
                register.child("Food").child("MMSE").child("Orientation").setValue(s[0]);
                register.child("Food").child("AD_Finder").child("Orientation").setValue(s[0]);
                s[0]=((5-s[0])*8)/5;
                register.child("Food").child("ADAS").child("Orientation").setValue(s[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        ring.pause();
        Toast.makeText(getApplicationContext(),"Press Back to theme",Toast.LENGTH_SHORT).show();
    }

    private  void startanim(Button b){
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.pluse);
        b.startAnimation(animation);
    }

}
