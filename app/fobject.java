package com.adfinder;

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

public class fobject extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    TextView car,bana,toma,bread,ice,plate,oven;
    TextView t1,t2;
    //ProgressBar progressBar;
    int ccount=0,wcount=0;
    int count=0;
    int f1=0,f2=0,f3=0,f4=0,f5=0;
    Button H,tim;
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fobject);
        checkInternetConnection();
        bana=findViewById(R.id.textView11);
        car=findViewById(R.id.textView12);
        toma=findViewById(R.id.textView13);
        plate=findViewById(R.id.textView15);
        ice=findViewById(R.id.textView16);
        bread=findViewById(R.id.textView17);
        t1=findViewById(R.id.textView2);
        t2=findViewById(R.id.ans);
        H=findViewById(R.id.h);
        tim=findViewById(R.id.time);
        startTimer();
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),fhome.class);
                i.putExtra("level",9);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f1==0&&count<5)
                {
                    f1=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")Carrot\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }

            }
        });
        bana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f2==0&&f5!=1)
                {
                    f2=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")Banana\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }


            }
        });
       toma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f3==0)
                {
                    f3=1;
                    ccount++;
                    count++;
                    String s=t2.getText().toString();
                    t2.setText(s+count+")Tomato\n");
                    if(count==5||ccount==3){
                        gonext();
                    }
                }
            }
        });


        ice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
            }
        });


        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrong();
            }
        });
        plate.setOnClickListener(new View.OnClickListener() {
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
        i.putExtra("activity","fobject");
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
        final DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Food");
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
        i.putExtra("theme",2);
        i.putExtra("total","Food");
        startActivity(i);unregisterReceiver(connectivityRecevier);finish();
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
                Intent i=new Intent(getApplicationContext(),  Score.class);
                i.putExtra("theme",2);
                startActivity(i);
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
