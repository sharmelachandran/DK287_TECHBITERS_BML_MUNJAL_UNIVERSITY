package com.ad_sih;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OfflineActivity extends AppCompatActivity {
    Button btncheck;
    String finalActivity="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_offline);
        final String activity=getIntent().getStringExtra("activity");
        btncheck=findViewById(R.id.btn);
        finalActivity = activity;
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected1=ConnectivityRecevier.isConnected();
                if(!isConnected1){
                    Toast.makeText(getApplicationContext(),"Check your internet Connection",Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = null;
                    try {
                        i = new Intent(getApplicationContext(), Class.forName(finalActivity));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);finish();
                }
            }
        });
    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (Exception ignored){}
    }

}
