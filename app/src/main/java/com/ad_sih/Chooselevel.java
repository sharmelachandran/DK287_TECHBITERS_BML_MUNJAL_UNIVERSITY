package com.ad_sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Chooselevel extends AppCompatActivity {
Button easy,med,hard,back;
MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chooselevel);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        easy=findViewById(R.id.button5);
        med=findViewById(R.id.button6);
        hard=findViewById(R.id.button7);
        back=findViewById(R.id.button8);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Chooselevel.this,Tictactoe.class);
                startActivity(i);
                finish();
            }
        });
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Chooselevel.this,Medium.class);m.stop();
                startActivity(i);
                finish();
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Chooselevel.this,GameActivity.class);m.stop();
                startActivity(i);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Chooselevel.this,Improve.class);m.stop();
                startActivity(i);
                finish();
            }
        });

    }
    public void onBackPressed(){
        // Intent intent = new Intent(this, MenuActivity.class);
        // startActivity(intent);
        Toast.makeText(getApplicationContext(),"Press the back button",Toast.LENGTH_LONG).show();
    }
}
