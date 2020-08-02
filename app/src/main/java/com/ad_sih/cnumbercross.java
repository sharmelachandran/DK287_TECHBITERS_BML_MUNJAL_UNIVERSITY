package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Locale;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static java.lang.Math.abs;

public class cnumbercross extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    Button b1,H,tim;
    // ProgressBar progressBar;
    int ccount=0;
    int wcount=0;
    int count=0;
    int[] a =new int [49];
    int[] flag = new int[49];
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    View V1;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cnumbercross);
        checkInternetConnection();
        //prog();
        Arrays.fill(a,0);
        Arrays.fill(flag,0);
        V1=findViewById(R.id.textView);
        b1=findViewById(R.id.button2);
        tim=findViewById(R.id.time);
        final Button v1 = findViewById(R.id.v1);
        final Button v2 = findViewById(R.id.v2);
        final Button v3 = findViewById(R.id.v3);
        final Button v4 = findViewById(R.id.v4);
        final Button v5 = findViewById(R.id.v5);
        final Button v6 = findViewById(R.id.v6);
        final Button v7 = findViewById(R.id.v7);
        final Button v8 = findViewById(R.id.v8);
        final Button v9 = findViewById(R.id.v9);
        final Button v10 = findViewById(R.id.v10);
        final Button v11 = findViewById(R.id.v11);
        final Button v12 = findViewById(R.id.v12);
        final Button v13 = findViewById(R.id.v13);
        final Button v14 = findViewById(R.id.v14);
        final Button v15 = findViewById(R.id.v15);
        final Button v16 = findViewById(R.id.v16);
        final Button v17 = findViewById(R.id.v17);
        final Button v18 = findViewById(R.id.v18);
        final Button v19 = findViewById(R.id.v19);
        final Button v20 = findViewById(R.id.v20);
        final Button v21 = findViewById(R.id.v21);
        final Button v22 = findViewById(R.id.v22);
        final Button v23 = findViewById(R.id.v23);
        final Button v24 = findViewById(R.id.v24);
        final Button v25 = findViewById(R.id.v25);
        final Button v26 = findViewById(R.id.v26);
        final Button v27 = findViewById(R.id.v27);
        final Button v28 = findViewById(R.id.v28);
        final Button v29 = findViewById(R.id.v29);
        final Button v30 = findViewById(R.id.v30);
        final Button v31 = findViewById(R.id.v31);
        final Button v32 = findViewById(R.id.v32);
        final Button v33 = findViewById(R.id.v33);
        final Button v34 = findViewById(R.id.v34);
        final Button v35 = findViewById(R.id.v35);
        final Button v36 = findViewById(R.id.v36);
        final Button v37 = findViewById(R.id.v37);
        final Button v38 = findViewById(R.id.v38);
        final Button v39 = findViewById(R.id.v39);
        final Button v40 = findViewById(R.id.v40);
        final Button v41 = findViewById(R.id.v41);
        final Button v42 = findViewById(R.id.v42);
        final Button v43 = findViewById(R.id.v43);
        final Button v44 = findViewById(R.id.v44);
        final Button v45 = findViewById(R.id.v45);
        final Button v46 = findViewById(R.id.v46);
        final Button v47 = findViewById(R.id.v47);
        final Button v48 = findViewById(R.id.v48);
        builder = new GuideView.Builder(this)
                .setTitle("Instruction")
                .setContentText("Follow the set of instructions given here\nwithin 60 seconds")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(V1)
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
        H=findViewById(R.id.h);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),chome.class);
                i.putExtra("level",7);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ccount==0 && wcount==0)
                    Toast.makeText(getApplicationContext(),"Press all 2's and 8's ",Toast.LENGTH_SHORT).show();
                else
                storedata();
            }
        });
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[1]==0){
                    flag[1]=1;
                    wcount++;
                    v1.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });

        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[3]==0){
                    flag[3]=1;
                    wcount++;
                    v3.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[4]==0){
                    flag[4]=1;
                    wcount++;
                    v4.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[6]==0){
                    flag[6]=1;
                    wcount++;
                    v6.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[7]==0){
                    flag[7]=1;
                    wcount++;
                    v7.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[9]==0){
                    flag[9]=1;
                    wcount++;
                    v9.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[10]==0){
                    flag[10]=1;
                    wcount++;
                    v10.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[11]==0){
                    flag[11]=1;
                    wcount++;
                    v11.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[13]==0){
                    flag[13]=1;
                    wcount++;
                    v13.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[14]==0){
                    flag[14]=1;
                    wcount++;
                    v14.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[15]==0){
                    flag[15]=1;
                    wcount++;
                    v15.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[16]==0){
                    flag[16]=1;
                    wcount++;
                    v16.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[18]==0){
                    flag[18]=1;
                    wcount++;
                    v18.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[20]==0){
                    flag[20]=1;
                    wcount++;
                    v20.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[22]==0){
                    flag[22]=1;
                    wcount++;
                    v22.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[23]==0){
                    flag[23]=1;
                    wcount++;
                    v23.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[24]==0){
                    flag[24]=1;
                    wcount++;
                    v24.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[25]==0){
                    flag[25]=1;
                    wcount++;
                    v25.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[28]==0){
                    flag[28]=1;
                    wcount++;
                    v28.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[29]==0){
                    flag[29]=1;
                    wcount++;
                    v29.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[30]==0){
                    flag[30]=1;
                    wcount++;
                    v30.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);

            }
        });
        v31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[31]==0){
                    flag[31]=1;
                    wcount++;
                    v31.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[33]==0){
                    flag[33]=1;
                    wcount++;
                    v33.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[34]==0){
                    flag[34]=1;
                    wcount++;
                    v34.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[35]==0){
                    flag[35]=1;
                    wcount++;
                    v35.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[36]==0){
                    flag[36]=1;
                    wcount++;
                    v36.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[38]==0){
                    flag[38]=1;
                    wcount++;
                    v38.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[40]==0){
                    flag[40]=1;
                    wcount++;
                    v40.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[42]==0){
                    flag[42]=1;
                    wcount++;
                    v42.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[43]==0){
                    flag[43]=1;
                    wcount++;
                    v43.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[44]==0){
                    flag[44]=1;
                    wcount++;
                    v44.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });

        v45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[45]==0){
                    flag[45]=1;
                    wcount++;
                    v45.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[46]==0){
                    flag[46]=1;
                    wcount++;
                    v46.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[48]==0){
                    flag[48]=1;
                    wcount++;
                    v48.setBackgroundResource(R.color.red);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });

        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[2]==0){
                    flag[2]=1;
                    ccount++;
                    v2.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[5]==0){
                    flag[5]=1;
                    ccount++;
                    v5.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[8]==0){
                    flag[8]=1;
                    ccount++;
                    v8.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[12]==0){
                    flag[12]=1;
                    ccount++;
                    v12.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[17]==0){
                    flag[17]=1;
                    ccount++;
                    v17.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[19]==0){
                    flag[19]=1;
                    ccount++;
                    v19.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[21]==0){
                    flag[21]=1;
                    ccount++;
                    v21.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[26]==0){
                    flag[26]=1;
                    ccount++;
                    v26.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[27]==0){
                    flag[27]=1;
                    ccount++;
                    v27.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[32]==0){
                    flag[32]=1;
                    ccount++;
                    v32.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[37]==0){
                    flag[37]=1;
                    ccount++;
                    v37.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[39]==0){
                    flag[39]=1;
                    ccount++;
                    v39.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[41]==0){
                    flag[41]=1;
                    ccount++;
                    v41.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


            }
        });
        v47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[47]==0){
                    flag[47]=1;
                    ccount++;
                    v47.setBackgroundResource(R.color.green);
                }if(ccount==14)
                    b1.setVisibility(View.VISIBLE);


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
        i.putExtra("activity","cnumbercross");
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

            int seconds = (int) (timeleftmilli / 1000) % 60;
            int Time_used_to_solve = 60 - seconds;
            countDownTimer.cancel();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Sports");
            int score=(ccount*5)/14;
            int w=abs((((ccount-(14-ccount))*5)/14)-((wcount*5)/34));
            register.child("AD_Finder").child("Number_crossing").child("score").setValue(score);
            register.child("AD_Finder").child("Number_crossing").child("Time_taken").setValue(Time_used_to_solve);
            register.child("ADAS").child("Number_crossing").child("score").setValue(w);
            register.child("ADAS").child("Number_crossing").child("Time_taken").setValue(Time_used_to_solve);
            if (ccount == 14 && wcount == 0)
                Toast.makeText(getApplicationContext(), "WOW!!! Done Good", Toast.LENGTH_LONG).show();
            else if (ccount < 14 && wcount != 0)
                Toast.makeText(getApplicationContext(), "BETTER LUCK NEXT TIME :(", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), chome.class);
            i.putExtra("level", 9);
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
               storedata();
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
