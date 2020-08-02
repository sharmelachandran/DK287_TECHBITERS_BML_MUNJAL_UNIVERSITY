package com.ad_sih;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
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

public class Score extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener{
    Button H;
    TextView tv;
    int flag;
    String Theme;
    int f1=0,f2=0,f3=0,f4=0;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);
        checkInternetConnection();
        flag=getIntent().getIntExtra("theme",1);
        Theme=getIntent().getStringExtra("total");
        H=findViewById(R.id.h);
        tv=findViewById(R.id.textView1);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i=new Intent(getApplicationContext(),theme.class);
                 i.putExtra("theme",flag);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        calculate();
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
        i.putExtra("activity","Score");
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
    private void calculate()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference register0 = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(Theme).child("AD_Finder");
        register0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long[] L = new Long[10];
                L[0] =(Long)snapshot.child("Orientation").getValue();
                L[1] =(Long)snapshot.child("Word_registration").getValue();
                L[2] =(Long)snapshot.child("Maze").child("Wrong_attempt_Ignored").getValue();
                L[3] =(Long)snapshot.child("Instruction_following").child("score").getValue();
                L[4] =(Long)snapshot.child("Sequence_of_activity").child("score").getValue();
                L[5] =(Long)snapshot.child("Word_recall").getValue();
                L[6] =(Long)snapshot.child("Number_crossing").child("score").getValue();
                L[7] =(Long)snapshot.child("Object_recognition").child("score").getValue();
                L[8] =(Long)snapshot.child("Clock").getValue();
                L[9] =(Long)snapshot.child("Pentagon").getValue();
                Long x =L[0]+L[1]+L[2]+L[3]+L[4]+L[5]+L[6]+L[7]+L[8]+L[9];
                register0.child("Total").setValue(x);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference register1 = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(Theme).child("MMSE");
        register1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long[] L = new Long[6];
                L[0] =(Long)snapshot.child("Orientation").getValue();
                L[1] =(Long)snapshot.child("Word_registration").getValue();
                L[2] =(Long)snapshot.child("Instruction_following").child("score").getValue();
                L[3] =(Long)snapshot.child("Word_recall").getValue();
                L[5] =(Long)snapshot.child("Pentagon").getValue();
                L[4] =(Long)snapshot.child("Object_recognition").child("score").getValue();

                Long x=((L[0]+L[1]+L[2]+L[3]+L[4]+L[5])*30)/18;
                register1.child("Total").setValue(x);
                if(x<=24)
                    f1=1;
                   // register1.child("Range").setValue("yes");
                else
                    f1=0;
                    //register1.child("Range").setValue("no");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference register2 = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(Theme).child("MINI_COG");
        register2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long[] L = new Long[2];
                L[0] =(Long)snapshot.child("Word_recall").getValue();
                L[1] =(Long)snapshot.child("Clock").getValue();
                Long x=L[0]+L[1];
                if(x<4)
                    f2=1;
                else
                    f2=0;
                register2.child("Total").setValue(x);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference register3= firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(Theme).child("ADAS");
        register3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long[] L = new Long[7];
                L[0] =(Long)snapshot.child("Orientation").getValue();
                L[1]=(Long)snapshot.child("Maze").child("Wrong_attempt").getValue();
                L[2] =(Long)snapshot.child("Instruction_following").child("score").getValue();
                L[3] =(Long)snapshot.child("Sequence_of_activity").child("score").getValue();
                L[4] =(Long)snapshot.child("Word_recall").getValue();
                L[5] =(Long)snapshot.child("Number_crossing").child("score").getValue();
                L[6] =(Long)snapshot.child("Object_recognition").child("score").getValue();
                Long x = 1L;
                if(L[0]!=null)
                    x=L[0];
                if(L[1]!=null)
                    x+=L[1];
                if(L[2]!=null)
                    x+=L[2];
                if(L[3]!=null)
                    x+=L[3];
                if(L[4]!=null)
                    x+=L[4];
                if(L[5]!=null)
                    x+=L[5];
                if(L[6]!=null)
                    x+=L[6];
                if(x!=null){
                    x=(x*70)/43;
                register3.child("Total").setValue(x);
                }
                else
                    register3.child("Total").setValue(0);

                if(x>=18)
                    f3=1;
                else
                    f3=0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        compare();
        /*if(f1==1&&f2==1&&f3==1)
            f4=1;
        if(f4==1)
            Toast.makeText(getApplicationContext(),"You have symptoms of AD go and have medical checkup as soon as possible",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"You are perfect..\n you don't have dementia",Toast.LENGTH_SHORT).show();*/
    }
    private void compare(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference total= firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(Theme);
        total.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long adas=(Long)snapshot.child("ADAS").child("Total").getValue();
                Long mmse=(Long)snapshot.child("MMSE").child("Total").getValue();
                Long mini=(Long)snapshot.child("MINI_COG").child("Total").getValue();
                Long adfinder=(Long)snapshot.child("AD_Finder").child("Total").getValue();
                if(adas!=null && mmse !=null && mini!=null) {
                    if (adas >= 18 && mmse <= 24 && mini <= 3)
                        tv.setText("You have symptoms of AD go and have medical checkup as soon as possible");
                    else
                        tv.setText("You are perfect..\n you don't have dementia");
                        //Toast.makeText(getApplicationContext(),"You have symptoms of AD go and have medical checkup as soon as possible",Toast.LENGTH_SHORT).show();
                }
                else{
                    tv.setText("Try some other theme for better results");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}
