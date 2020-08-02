package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class coject extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    TextView fball,dog,bat,w1,w2,w3,w4;//wrong w1 w2 w3
    TextView t1,t2;
    //ProgressBar progressBar;
    int ccount=0,wcount=0;
    int count=0;
    int f1=0,f2=0,f3=0,f4=0,f5=0,f6=0;
    Button H,tim;
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_coject);
        checkInternetConnection();
        fball=findViewById(R.id.textView6);
        dog=findViewById(R.id.textView7);
        bat=findViewById(R.id.textView8);
        w1=findViewById(R.id.textView10);
        w2=findViewById(R.id.textView12);
        w3=findViewById(R.id.textView9);
        w4=findViewById(R.id.textView13);
        t2=findViewById(R.id.ans);
        H=findViewById(R.id.h);
        tim=findViewById(R.id.time);
        startTimer();
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent i=new Intent(getApplicationContext(),chome.class);
                i.putExtra("level",9);
                startActivity(i);finish();unregisterReceiver(connectivityRecevier);
            }
        });
        t1=findViewById(R.id.textView2);
        fball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f1==0&&count<5)
                {
                    f1=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")FootBall\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }

            }
        });
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f2==0&&f5!=1)
                {
                    f2=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")Dog\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }
            }
        });
        bat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f3==0)
                {
                    f3=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")Bat\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }
            }
        });
        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
            }
        });
        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
            }
        });
        w4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
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
        i.putExtra("activity","coject");
        startActivity(i);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            changeActivity();
        }
    }
    //to resume when it is pause due to no internet
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityRecevier,intentFilter);
        MyApp.getInstance().setConnectivityListner(this);
    }

    private void wrong(){
        wcount++;
        count++;
        String s=t2.getText().toString();
        t2.setText(s+count+")Wrong\n");
        if(count==5||ccount==3)
        {
            gonext();
        }
    }
    private void gonext(){
        String s=t2.getText().toString();
        t2.setText(s);
        storedata();
    }
    private void storedata(){
        int seconds = (int) (timeleftmilli / 1000) % 60;
        int Time_used_to_solve = 60 - seconds;
        countDownTimer.cancel();
        final Long[] s = new Long[4];
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Sports");
        register.child("AD_Finder").child("Object_recognition").child("score").setValue(ccount);
        register.child("AD_Finder").child("Object_recognition").child("Time_taken").setValue(Time_used_to_solve);
        int w=((3-ccount)*5)/3;
        register.child("ADAS").child("Object_recognition").child("score").setValue(w);
        register.child("ADAS").child("Object_recognition").child("Time_taken").setValue(Time_used_to_solve);
        int x=(ccount*2)/3;
        register.child("MMSE").child("Object_recognition").child("score").setValue(x);
        register.child("MMSE").child("Object_recognition").child("Time_taken").setValue(Time_used_to_solve);
        //word recall mean calculation
        register.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s[0] =  (Long)snapshot.child("AD_Finder").child("Word_recall").getValue();
                s[1] = (Long) snapshot.child("ADAS").child("Word_recall").getValue();
                s[2] = (Long) snapshot.child("MMSE").child("Word_recall").getValue();
                s[3] = (Long) snapshot.child("MINI_COG").child("Word_recall").getValue();
                Long x=(s[0]+ccount)/2;
                Long y=((((3-ccount)*10)/3)+s[1])/2;
                register.child("AD_Finder").child("Word_recall").setValue(x);
                register.child("ADAS").child("Word_recall").setValue(y);
                register.child("MMSE").child("Word_recall").setValue(x);
                register.child("MINI_COG").child("Word_recall").setValue(x);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (ccount == 3 && wcount == 0)
            Toast.makeText(getApplicationContext(), "WOW!!! Done Good", Toast.LENGTH_LONG).show();
        else if (ccount < 3 && wcount != 0)
            Toast.makeText(getApplicationContext(), "BETTER LUCK NEXT TIME :(", Toast.LENGTH_LONG).show();
        Intent i=new Intent(getApplicationContext(),  Score.class);
        i.putExtra("theme",3);
        i.putExtra("total","Sports");
        startActivity(i);finish();unregisterReceiver(connectivityRecevier);
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
                storedata();}
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

