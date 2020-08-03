package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class Food extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {
    Button V1,S1,T,H;
    TextView ins,P,B,M;
    int count=0,ccount=0,i=0,l=5;
    int f[]={0,0,0};
    String str[]={"Carrot","Banana","Tomato"},str1=str[0];
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    TextToSpeech textToSpeech;
    View v1,v2,v3,v4,v5;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_food);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        checkInternetConnection();
        v1=findViewById(R.id.h);
        v3=findViewById(R.id.trails);
        v4=findViewById(R.id.voice);
        v5=findViewById(R.id.speech);
        V1=findViewById(R.id.voice);
        S1=findViewById(R.id.speech);
        P=findViewById(R.id.par);
        B=findViewById(R.id.butterfly);
        M=findViewById(R.id.monkey);
        T=findViewById(R.id.trails);
        T.setText(l+" Trials");
        H=findViewById(R.id.h);
        builder = new GuideView.Builder(this)
                .setTitle("Home")
                .setContentText("It helps you to go back to home page")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(v1)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        switch (view.getId()) {
                            case R.id.h:
                                builder.setTitle("Trials").setContentText("You have 5 trials totally\nfor 3 upcoming words").setTargetView(v3).build();
                                break;
                            case R.id.trails:
                                builder.setTitle("Speaker").setContentText("By clicking this,\nYou can hear pronunciation of upcoming words").setTargetView(v4).build();
                                break;
                            case R.id.voice:
                                builder.setTitle("Mic").setContentText("You have to say each upcoming word by clicking this mic").setTargetView(v5).build();
                                break;
                            case R.id.speech:
                                P.setBackgroundResource(R.drawable.rightss);
                                startFlash(P);
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
            public void onClick(View v) {m.stop();
                Intent i=new Intent(getApplicationContext(),fhome.class);
                i.putExtra("level",1);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int lang=textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speech =textToSpeech.speak(str1,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    void startFlash(TextView c)
    {
        Animation animation=new AlphaAnimation(1,0);
        animation.setDuration(200);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.INFINITE);
        c.startAnimation(animation);

    }
    private void updatingForDynamicLocationViews() {
        v4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        i.putExtra("activity","Food");
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
    /*void hint(){
        new TTFancyGifDialog.Builder(Food.this)
                .setTitle("Speaker")
                .setMessage("You can hear the pronunciation of shown word by clicking Speaker")
                .setPositiveBtnText("GOT IT")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.ic_speaker_foreground)      //pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        hint1();
                    }
                })
                .build();
    }
    void hint1(){
        new TTFancyGifDialog.Builder(Food.this)
                .setTitle("Mic")
                .setMessage("You have to say the shown word by clicking Mic for each word")
                .setPositiveBtnText("GOT IT")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.ic_mic_foreground)      //pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        hint2();
                        //Toast.makeText(getApplicationContext(),"Remember upcoming words till the game ends\n You have only 5 trails",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
    void hint2(){
        new TTFancyGifDialog.Builder(Food.this)
                .setTitle("Instruction")
                .setMessage("Remember upcoming words till the game ends\n You have only 5 trails for 3 words")
                .setPositiveBtnText("GOT IT")
                .setPositiveBtnBackground("#22b573")//pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(),"Level 1 starts",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    assert result1 != null;
                    final String s = result1.get(0);
                    String u = s.toLowerCase();
                    String m1=str1.toLowerCase();
                    count++;
                    l=5-count;
                    T.setText(l+" Trials");
                    if (u.equals(m1)) {
                        ccount++;
                        i++;
                        if(i<3) {
                            str1=str[i];
                            Toast.makeText(getApplicationContext(), "SUPER!!!\nTouch mic and say "+str[i], Toast.LENGTH_LONG).show();
                        }
                        else {
                            gotonext();
                        }
                    } else {
                        if(count>=5)
                        {
                            gotonext();
                        }
                        if(i<3)
                            f[i]+=1;
                        if(f[i]>=3&&i<2)
                        {
                            i++;
                            str1=str[i];
                        }
                        else if(l==0||i==3)
                        {
                            gotonext();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Touch mic and say "+m1+"\nYou have "+l+" Trails",Toast.LENGTH_LONG).show();

                        }

                    }
                    if(i==1){
                        P.setBackgroundResource(R.color.grass);
                        B.setBackgroundResource(R.drawable.rightss);
                        startFlash(B);
                    }
                    else if(i==2) {
                        B.setBackgroundResource(R.color.grass);
                        M.setBackgroundResource(R.drawable.rightss);
                        startFlash(M);
                    }
                    break;
                }
            }
        }
    }
    private void gotonext(){
        storedata();

    }
    private  void storedata(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Food");
        register.child("MMSE").child("Word_registration").setValue(ccount);
        register.child("AD_Finder").child("Word_registration").setValue(ccount);
        Intent i = new Intent(getApplicationContext(), fhome.class);
        i.putExtra("level",2);m.stop();
        startActivity(i);unregisterReceiver(connectivityRecevier);finish();

    }
    public void speak(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the word");
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

