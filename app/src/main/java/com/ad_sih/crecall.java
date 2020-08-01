package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class crecall extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener{
    TextView tv,W1,W2,W3;
    Button S1,F,T,H;
    private String[] str ={"FootBall","Stump","Bat"};
    int i=0,count=0,flag0=0,flag1=0,flag2=0,l=5,ccout=0;
    String s;
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    View v1,v2;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_crecall);
        checkInternetConnection();
        v1=findViewById(R.id.textView3);
        v2=findViewById(R.id.speech);
        S1=findViewById(R.id.speech);
        W1=findViewById(R.id.w1);
        W2=findViewById(R.id.w2);
        W3=findViewById(R.id.w3);
        //F=findViewById(R.id.next);
        T=findViewById(R.id.trails);
        builder = new GuideView.Builder(this)
                .setTitle("Recall")
                .setContentText("Recall words you said in level 1")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(v1)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        switch (view.getId()) {
                            case R.id.textView3:
                                builder.setTitle("Mic").setContentText("Click this each time for each word\nYou have totally 5 trails for 3 words").setTargetView(v2).build();
                                break;
                            case R.id.speech:
                                return;
                        }
                        mGuideView = builder.build();
                        mGuideView.show();
                    }
                });

        mGuideView = builder.build();
        mGuideView.show();
        updatingForDynamicLocationViews();

        H=findViewById(R.id.h);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),chome.class);
                i.putExtra("level",5);
                startActivity(i);finish();unregisterReceiver(connectivityRecevier);
            }
        });
        T.setText(l+" Trials");
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startFlash();
            }
        },300);
        S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();

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
    private void checkInternetConnection(){
        boolean isConnected=ConnectivityRecevier.isConnected();
        if(!isConnected){
            changeActivity();
        }
    }
    private void changeActivity(){
        Intent i=new Intent(this,OfflineActivity.class);
        this.onPause();
        i.putExtra("activity","crecall");
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
    void startFlash()
    {
        Animation animation=new AlphaAnimation(1,0);
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.INFINITE);
        S1.startAnimation(animation);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert result1 != null;
                    String s1 = result1.get(0);
                    s1 = s1.toLowerCase();
                    count++;
                    l=5-count;
                    T.setText(l+" Trials");
                    if (s1.equals(str[0].toLowerCase()) && flag0 == 0) {ccout++;
                        Toast.makeText(getApplicationContext(), "SUPER!!!"+s1+" is correct", Toast.LENGTH_LONG).show();
                        flag0 = 1;
                        W1.setText("Football");

                    } else if (s1.equals(str[1].toLowerCase()) && flag1 == 0) {ccout++;
                        Toast.makeText(getApplicationContext(), "SUPER!!!"+s1+" is correct", Toast.LENGTH_LONG).show();
                        flag1 = 1;
                        W2.setText("Stump");


                    } else if (s1.equals(str[2].toLowerCase()) && flag2 == 0) {ccout++;
                        Toast.makeText(getApplicationContext(), "SUPER!!!\n"+s1+" is correct", Toast.LENGTH_LONG).show();
                        flag2 = 1;
                        W2.setText("Bat");
                    } else {
                        Toast.makeText(getApplicationContext(),"Wrong:("+s1+"\nTry to recollect the words\nStill you have "+l+" Trails",Toast.LENGTH_LONG).show();
                    }
                    if ((flag0 == 1 && flag1 == 1 && flag2 == 1) || l==0) {
                        String s ="";
                        if(flag0!=1)
                            s=s+"Football\n";
                        if(flag1!=1)
                            s=s+"Stump\n";
                        if(flag2!=1)
                            s=s+"Bat\n";
                        if(s!="")
                        {
                            s="Words you forget are:\n"+s+"Keep these words in memory till game ends";
                        }else
                            s="SUPER!! You recalled very well";

                        storedata(s);
                    }


                }
                break;
            }
        }
    }

    private  void storedata(String s){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Sports");
        register.child("MMSE").child("Word_recall").setValue(ccout);
        register.child("AD_Finder").child("Word_recall").setValue(ccout);
        register.child("MINI_COG").child("Word_recall").setValue(ccout);
        int wcout=((3-ccout)*10)/3;
        register.child("ADAS").child("Word_recall").setValue(wcout);
        new TTFancyGifDialog.Builder(crecall.this)
                .setTitle("Results")
                .setMessage(s)
                .setPositiveBtnText("GOT IT")
                .setPositiveBtnBackground("#22b573")//pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent i = new Intent(getApplicationContext(), Score.class);//need to change
                        i.putExtra("level",6);
                        startActivity(i);finish();unregisterReceiver(connectivityRecevier);

                    }
                })
                .build();
    }
    private void speak(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");
        try{
            startActivityForResult(i,REQUEST_CODE_SPEECH_INPUT);
        }catch(Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}

