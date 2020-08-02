package com.ad_sih;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class cjumble extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener {

    Button S1,S2,S3,S4,S5,C1,C2,C3,C4,C5,H,tim,F;//S is vertical sequence C is horizontal sequence
    TextView t1,t2,t3,t4,t5;// sentence textviews
    private static final long START_TIME=60000;//in milliseconds 60000/60=1mins
    private CountDownTimer countDownTimer;
    private long timeleftmilli=START_TIME;
    int c=0;
    int i = 0;
    int ccount=0;
    int a[]={0,0,0,0,0};
    int f[] = {3,1,2,5,4};
    int f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0,fl1=0;
    int wcount =0;
    View v1,v2,v3,v4;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cjumble);
        checkInternetConnection();
        v1=findViewById(R.id.s1);
        v2=findViewById(R.id.c1);
        v3=findViewById(R.id.time);
        v4=findViewById(R.id.fin);
        S1=findViewById(R.id.s1);
        S2=findViewById(R.id.s2);
        S3=findViewById(R.id.s3);
        S4=findViewById(R.id.s4);
        S5=findViewById(R.id.s5);
        C1=findViewById(R.id.c1);
        C2=findViewById(R.id.c2);
        C3=findViewById(R.id.c3);
        C4=findViewById(R.id.c4);
        C5=findViewById(R.id.c5);
        t1=findViewById(R.id.textView1);
        t2=findViewById(R.id.textView2);
        t3=findViewById(R.id.textView3);
        t4=findViewById(R.id.textView4);
        t5=findViewById(R.id.textView5);
        tim=findViewById(R.id.time);
        H=findViewById(R.id.h);
        F=findViewById(R.id.fin);
        builder = new GuideView.Builder(this)
                .setTitle("Drag")
                .setContentText("Drag these images in correct sequence\n")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(v1)
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        switch (view.getId()) {
                            case R.id.s1:
                                builder.setTitle("Drop").setContentText("Drop those images in this row of numbers").setTargetView(v2).build();
                                break;
                            case R.id.c1:
                                builder.setTitle("Timer").setContentText("You have 60 seconds to finish this level").setTargetView(v3).build();
                                break;
                            case R.id.time:
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
                i.putExtra("level",4);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        S1.setOnLongClickListener(longClickListener);
        S2.setOnLongClickListener(longClickListener);
        S3.setOnLongClickListener(longClickListener);
        S4.setOnLongClickListener(longClickListener);
        S5.setOnLongClickListener(longClickListener);
        t1.setOnLongClickListener(longClickListener);
        t2.setOnLongClickListener(longClickListener);
        t3.setOnLongClickListener(longClickListener);
        t4.setOnLongClickListener(longClickListener);
        t5.setOnLongClickListener(longClickListener);
        C1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent=event.getAction();
                final View view=(View)event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        C1.setText(" ");
                        if(view.getId() ==R.id.s1 || view.getId()==R.id.textView1)
                        {
                            wcount++;
                            t1.setText("");
                            S1.setBackgroundResource(R.color.grass);
                            C1.setBackgroundResource(R.drawable.sj2);
                            view.animate().x(C1.getX()).y(C1.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView2 || view.getId()==R.id.s2)
                        {
                            wcount++;
                            t2.setText("");
                            S2.setBackgroundResource(R.color.grass);
                            C1.setBackgroundResource(R.drawable.sj3);
                            view.animate().x(C1.getX()).y(C1.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView3 || view.getId()==R.id.s3)
                        {
                            ccount++;
                            t3.setText("");
                            S3.setBackgroundResource(R.color.grass);
                            C1.setBackgroundResource(R.drawable.sj1);
                            view.animate().x(C1.getX()).y(C1.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView4 || view.getId()==R.id.s4)
                        {
                            wcount++;
                            t4.setText("");
                            S4.setBackgroundResource(R.color.grass);
                            C1.setBackgroundResource(R.drawable.sj5);
                            view.animate().x(C1.getX()).y(C1.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView5 || view.getId()==R.id.s5)
                        {
                            wcount++;
                            t5.setText("");
                            S5.setBackgroundResource(R.color.grass);
                            C1.setBackgroundResource(R.drawable.sj4);
                            view.animate().x(C1.getX()).y(C1.getY()).setDuration(700).start();
                        }
                        if(wcount+ccount==5) {
                            F.setVisibility(View.VISIBLE);
                            t1.setBackgroundResource(R.color.grass);
                            t2.setBackgroundResource(R.color.grass);
                            t3.setBackgroundResource(R.color.grass);
                            t4.setBackgroundResource(R.color.grass);
                            t5.setBackgroundResource(R.color.grass);
                        }
                        break;
                }
                return true;
            }
        });
        C2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent=event.getAction();
                final View view=(View)event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        C2.setText(" ");
                        if(view.getId() ==R.id.s1 || view.getId()==R.id.textView1)
                        {
                            t1.setText("");
                            ccount++;
                            S1.setBackgroundResource(R.color.grass);
                            C2.setBackgroundResource(R.drawable.sj2);
                            view.animate().x(C2.getX()).y(C2.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView2 || view.getId()==R.id.s2)
                        {
                            wcount++;
                            t2.setText("");
                            S2.setBackgroundResource(R.color.grass);
                            C2.setBackgroundResource(R.drawable.sj3);
                            view.animate().x(C2.getX()).y(C2.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView3 || view.getId()==R.id.s3)
                        {
                            wcount++;
                            t3.setText("");
                            S3.setBackgroundResource(R.color.grass);
                            C2.setBackgroundResource(R.drawable.sj1);
                            view.animate().x(C2.getX()).y(C2.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView4 || view.getId()==R.id.s4)
                        {
                            wcount++;
                            t4.setText("");
                            S4.setBackgroundResource(R.color.grass);
                            C2.setBackgroundResource(R.drawable.sj5);
                            view.animate().x(C2.getX()).y(C2.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView5 || view.getId()==R.id.s5)
                        {
                            wcount++;
                            t5.setText("");
                            S5.setBackgroundResource(R.color.grass);
                            C2.setBackgroundResource(R.drawable.sj4);
                            view.animate().x(C2.getX()).y(C2.getY()).setDuration(700).start();
                        }
                        if(wcount+ccount==5) {
                            F.setVisibility(View.VISIBLE);
                            t1.setBackgroundResource(R.color.grass);
                            t2.setBackgroundResource(R.color.grass);
                            t3.setBackgroundResource(R.color.grass);
                            t4.setBackgroundResource(R.color.grass);
                            t5.setBackgroundResource(R.color.grass);
                        }
                        break;
                }
                return true;
            }
        });
        C3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent=event.getAction();
                final View view=(View)event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        C3.setText(" ");
                        if(view.getId() ==R.id.s1 || view.getId()==R.id.textView1)
                        {
                            wcount++;
                            t1.setText("");
                            S1.setBackgroundResource(R.color.grass);
                            C3.setBackgroundResource(R.drawable.sj2);
                            view.animate().x(C3.getX()).y(C3.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView2 || view.getId()==R.id.s2)
                        {
                            ccount++;
                            t2.setText("");
                            S2.setBackgroundResource(R.color.grass);
                            C3.setBackgroundResource(R.drawable.sj3);
                            view.animate().x(C3.getX()).y(C3.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView3 || view.getId()==R.id.s3)
                        {
                            wcount++;
                            t3.setText("");
                            S3.setBackgroundResource(R.color.grass);
                            C3.setBackgroundResource(R.drawable.sj1);
                            view.animate().x(C3.getX()).y(C3.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView4 || view.getId()==R.id.s4)
                        {
                            wcount++;
                            t4.setText("");
                            S4.setBackgroundResource(R.color.grass);
                            C3.setBackgroundResource(R.drawable.sj5);
                            view.animate().x(C3.getX()).y(C3.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView5 || view.getId()==R.id.s5)
                        {
                            wcount++;
                            t5.setText("");
                            S5.setBackgroundResource(R.color.grass);
                            C3.setBackgroundResource(R.drawable.sj4);
                            view.animate().x(C3.getX()).y(C3.getY()).setDuration(700).start();
                        }
                        if(wcount+ccount==5) {
                            F.setVisibility(View.VISIBLE);
                            t1.setBackgroundResource(R.color.grass);
                            t2.setBackgroundResource(R.color.grass);
                            t3.setBackgroundResource(R.color.grass);
                            t4.setBackgroundResource(R.color.grass);
                            t5.setBackgroundResource(R.color.grass);
                        }
                        break;
                }
                return true;
            }
        });
        C4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent=event.getAction();
                final View view=(View)event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        C4.setText(" ");
                        if(view.getId() ==R.id.s1 || view.getId()==R.id.textView1)
                        {
                            wcount++;
                            t1.setText("");
                            S1.setBackgroundResource(R.color.grass);
                            C4.setBackgroundResource(R.drawable.sj2);
                            view.animate().x(C4.getX()).y(C4.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView2 || view.getId()==R.id.s2)
                        {
                            wcount++;
                            t2.setText("");
                            S2.setBackgroundResource(R.color.grass);
                            C4.setBackgroundResource(R.drawable.sj3);
                            view.animate().x(C4.getX()).y(C4.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView3 || view.getId()==R.id.s3)
                        {
                            wcount++;
                            t3.setText("");
                            S3.setBackgroundResource(R.color.grass);
                            C4.setBackgroundResource(R.drawable.sj1);
                            view.animate().x(C4.getX()).y(C4.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView4 || view.getId()==R.id.s4)
                        {
                            wcount++;
                            t4.setText("");
                            S4.setBackgroundResource(R.color.grass);
                            C4.setBackgroundResource(R.drawable.sj5);
                            view.animate().x(C4.getX()).y(C4.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView5 || view.getId()==R.id.s5)
                        {
                            ccount++;
                            t5.setText("");
                            S5.setBackgroundResource(R.color.grass);
                            C4.setBackgroundResource(R.drawable.sj4);
                            view.animate().x(C4.getX()).y(C4.getY()).setDuration(700).start();
                        }
                        if(wcount+ccount==5) {
                            F.setVisibility(View.VISIBLE);
                            t1.setBackgroundResource(R.color.grass);
                            t2.setBackgroundResource(R.color.grass);
                            t3.setBackgroundResource(R.color.grass);
                            t4.setBackgroundResource(R.color.grass);
                            t5.setBackgroundResource(R.color.grass);
                        }
                        break;
                }
                return true;
            }
        });
        C5.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent=event.getAction();
                final View view=(View)event.getLocalState();
                switch (dragEvent){
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        C5.setText(" ");
                        if(view.getId() ==R.id.s1 || view.getId()==R.id.textView1)
                        {
                            wcount++;
                            t1.setText("");
                            S1.setBackgroundResource(R.color.grass);
                            C5.setBackgroundResource(R.drawable.sj2);
                            view.animate().x(C5.getX()).y(C5.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView2 || view.getId()==R.id.s2)
                        {
                            wcount++;
                            t2.setText("");
                            S2.setBackgroundResource(R.color.grass);
                            C5.setBackgroundResource(R.drawable.sj3);
                            view.animate().x(C5.getX()).y(C5.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView3 || view.getId()==R.id.s3)
                        {
                            wcount++;
                            t3.setText("");
                            S3.setBackgroundResource(R.color.grass);
                            C5.setBackgroundResource(R.drawable.sj1);
                            view.animate().x(C5.getX()).y(C5.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView4 || view.getId()==R.id.s4)
                        {
                            ccount++;
                            t4.setText("");
                            S4.setBackgroundResource(R.color.grass);
                            C5.setBackgroundResource(R.drawable.sj5);
                            view.animate().x(C5.getX()).y(C5.getY()).setDuration(700).start();
                        }
                        else if(view.getId()==R.id.textView5 || view.getId()==R.id.s5)
                        {
                            wcount++;
                            t5.setText("");
                            S5.setBackgroundResource(R.color.grass);
                            C5.setBackgroundResource(R.drawable.sj4);
                            view.animate().x(C5.getX()).y(C5.getY()).setDuration(700).start();
                        }
                        if(wcount+ccount==5) {
                            F.setVisibility(View.VISIBLE);
                            t1.setBackgroundResource(R.color.grass);
                            t2.setBackgroundResource(R.color.grass);
                            t3.setBackgroundResource(R.color.grass);
                            t4.setBackgroundResource(R.color.grass);
                            t5.setBackgroundResource(R.color.grass);
                        }
                        break;
                }
                return true;
            }
        });

        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storedata();
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
        i.putExtra("activity","cjumble");
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


    private void updatingForDynamicLocationViews() {
        v4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }
    View.OnLongClickListener longClickListener=new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data=ClipData.newPlainText("","");
            View.DragShadowBuilder myshadowbuilder=new View.DragShadowBuilder(v);
            v.startDrag(data,myshadowbuilder,v,0);
            return true;
        }
    };
    private void storedata(){
        if (ccount+wcount == 5) {
            int seconds=(int)(timeleftmilli/1000)%60;
            int Time_used_to_solve=60-seconds;
            countDownTimer.cancel();
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child("Sports");
            register.child("AD_Finder").child("Sequence_of_activity").child("score").setValue(ccount);
            register.child("AD_Finder").child("Sequence_of_activity").child("Time_taken").setValue(Time_used_to_solve);
            register.child("ADAS").child("Sequence_of_activity").child("score").setValue(wcount);
            register.child("ADAS").child("Sequence_of_activity").child("Time_taken").setValue(Time_used_to_solve);
            if (ccount == 5)
                Toast.makeText(getApplicationContext(), "Correct Answer!!!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), chome.class);
            i.putExtra("level",5);
            startActivity(i);unregisterReceiver(connectivityRecevier);finish();
        }
        else
        {
            Toast.makeText( getApplicationContext(),"Click the sequence of Morning Routine",Toast.LENGTH_LONG).show();
        }
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
