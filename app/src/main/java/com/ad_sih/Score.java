package com.ad_sih;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class Score extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener{
    Button H,M,I;
    TextView tv;
    int flag;
    String Theme;
    String mfilepath,mailid,mailid1,currentdate,date,playerdetails,ADAS,MINI_COG,MMSE,ADFINDER;
    int f1=0,f2=0,f3=0,f4=0;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    private static final int STORAGE_CODE =1000;
    private StorageReference mStorageRef;
    MediaPlayer m;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        checkInternetConnection();
        flag=getIntent().getIntExtra("theme",1);
        Theme=getIntent().getStringExtra("total");
        H=findViewById(R.id.h);
        M=findViewById(R.id.map);
        I=findViewById(R.id.improve);
        tv=findViewById(R.id.textView1);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {m.stop();
                 Intent i=new Intent(getApplicationContext(),theme.class);
                 i.putExtra("theme",flag);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
            }
        });
        M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {m.stop();
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.google.co.in/maps/search/nearby+neurologist/"));
                startActivity(viewIntent);
            }
        });
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {m.stop();
                Intent i=new Intent(getApplicationContext(),Improve.class);
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
                ADFINDER="\nAD-FINDER SCORE:\nOrientation:"+L[0]+"\nWord Registration: "+L[1]+"\nMaze-Wrong attempt Ignored: "+L[2]
                        +"\nInstruction Following: "+L[3]+"\nSequence of Activity: "+L[4]+"\nWord Recall: "+L[5]+"\nNumber Crossing: "
                        +L[6]+"\nObject Recognition: "+L[7]+"\nClock Drawing: "+L[8]+"\nInterlocking Diagram: "+L[9]+"\nTotal: "+x;
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

                MMSE="\nMMSE SCORE:\nOrientation:"+L[0]+"\nWord Registration: "+L[1]+"\nInstruction Following: "+L[2]
                        +"\nWord Recall: "+L[3]+"\nObject Recognition: "+L[4]+"\nInterlocking Diagram: "+L[5]+"\nTotal: "+x;
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
                MMSE="\nMINI-COG SCORE:\nWord Recall: "+L[0]+"\nClock Drawing: "+L[1]+"\nTotal: "+x;

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
                    ADAS="\nADAS SCORE:\nOrientation:"+L[0]+"\nMaze-Wrong attempt: "+L[1]
                            +"\nInstruction Following: "+L[2]+"\nSequence of Activity: "+L[3]+"\nWord Recall: "+L[4]+"\nNumber Crossing: "
                            +L[5]+"\nObject Recognition: "+L[6]+"\nTotal: "+x;
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
                    if (adas >= 18 && mmse <= 24 && mini <= 3) {
                        tv.setText("You have symptoms of AD go and have medical checkup as soon as possible");
                        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                                String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            }
                            else{
                                savepdf();
                            }
                        }else{
                            savepdf();
                        }
                    }
                    else {
                        tv.setText("You are perfect..\n you don't have dementia");
                        I.setVisibility(View.VISIBLE);
                        M.setVisibility(View.VISIBLE);
                        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                                String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            }
                            else{
                                savepdf();
                            }
                        }else{
                            savepdf();
                        }

                    }
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
    private void savepdf() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference total1= firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid());
        total1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n=snapshot.child("name").getValue().toString();
                String a=snapshot.child("age").getValue().toString();
                String g=snapshot.child("gender").getValue().toString();
                String m1=snapshot.child("mail1").getValue().toString();
                String m2=snapshot.child("mail2").getValue().toString();
                mailid=m2;
                mailid1=m1;
                playerdetails="Person Details:\n"+n+",\n"+a+","+g+",\n"+m1+",\nCare taker: "+m2+".";
                Toast.makeText(getApplicationContext(),""+playerdetails,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final com.itextpdf.text.Document mDoc=new Document();
        String mfilename=new SimpleDateFormat("MMdd_HHmm", Locale.getDefault()).format(System.currentTimeMillis());
        final String p=""+Theme+":"+mfilename;//nn=pname+pid
        mfilepath= Environment.getExternalStorageDirectory()+"/"+p+".pdf";
        try {
            PdfWriter.getInstance(mDoc,new FileOutputStream(mfilepath));
            mDoc.addCreationDate();
            mDoc.setMargins(10,10,10,10);
            mDoc.setMarginMirroringTopBottom(true);
            mDoc.addTitle("AD-FINDER");
            mDoc.open();
            mDoc.left(100);
            mDoc.add(new Paragraph("\n Alzheimer's Disease Assessment"));
            mDoc.add(new Paragraph("\nAssessed on "+currentdate+"\n"));
            mDoc.add(new Paragraph("\n"+playerdetails+"\n"));
            mDoc.add(new Paragraph("\n"+ADFINDER+"\n"));
            mDoc.add(new Paragraph("\n"+MMSE+"\n"));
            mDoc.add(new Paragraph("\n"+MINI_COG+"\n"));
            mDoc.add(new Paragraph("\n"+ADAS+"\n"));
            mDoc.add(new Paragraph());
            mDoc.add(new Paragraph("\n\n"));
            String provider="Results Provided by AD-Finder.";
            mDoc.add(new Paragraph(provider));
            mDoc.close();
            final Uri file = Uri.fromFile(new File(mfilepath));
            final StorageReference riversRef = mStorageRef.child(FirebaseAuth.getInstance().getUid());
            riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            String downloadUrl=url;
                            String mail=mailid;
                            String msg=downloadUrl;
                            String sub="AD-Finder Assessment Report";
                            JavaMailAPI javaMailAPI=new JavaMailAPI(Score.this,mail,sub,msg);
                            javaMailAPI.execute();
                            mail=mailid1;
                            JavaMailAPI javaMailAPI1=new JavaMailAPI(Score.this,mail,sub,msg);
                            javaMailAPI1.execute();
                            M.setVisibility(View.VISIBLE);

                        }
                    });
                    Toast.makeText(getBaseContext(), "pdf stored", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        // sendMail();
        Toast.makeText(this,"you can now send email ",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}
