package com.ad_sih;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class theme extends AppCompatActivity implements FirebaseAuth.AuthStateListener,ConnectivityRecevier.ConnectivityRecevierListener {
    ImageView N,C,F,E;
    int theme;
    private static final String TAG = "theme";
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    Boolean pause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_theme);
        N=findViewById(R.id.nat);
        C=findViewById(R.id.cric);
        F=findViewById(R.id.food);
        E=findViewById(R.id.enter);
        theme=getIntent().getIntExtra("theme",0);
        checkInternetConnection();
        N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme!=1) {
                    Intent i = new Intent(getApplicationContext(), home.class);
                    i.putExtra("level", 1);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Theme completed",Toast.LENGTH_SHORT).show();
            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme!=2) {
                    Intent i = new Intent(getApplicationContext(), fhome.class);
                    i.putExtra("level", 1);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Theme completed",Toast.LENGTH_SHORT).show();

            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme!=3){
                    Intent i = new Intent(getApplicationContext(), chome.class);
                    i.putExtra("level", 1);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else
                    Toast.makeText(getApplicationContext(),"Theme completed",Toast.LENGTH_SHORT).show();
            }
        });
        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme!=4){
                    Intent i = new Intent(getApplicationContext(), mhome.class);
                    i.putExtra("level", 1);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else
                    Toast.makeText(getApplicationContext(),"Theme completed",Toast.LENGTH_SHORT).show();
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
        i.putExtra("activity","theme");
        startActivity(i);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected && (!pause) ){
            changeActivity();
        }else if(!isConnected && pause)
            Toast.makeText(getApplicationContext(),"offline",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityRecevier,intentFilter);
        MyApp.getInstance().setConnectivityListner(this);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.logout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(getApplicationContext(), register.class);
                        startActivity(i);unregisterReceiver(connectivityRecevier);
                        finish();//to prevent coming back
                    } else {
                        Log.e(TAG, "onComplete:", task.getException());
                    }
                }
            });
            //FirebaseAuth.getInstance().signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener((FirebaseAuth.AuthStateListener) this);
    }

    @Override
    protected void onPause() {
        //unregisterReceiver(connectivityRecevier);
        pause=true;
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener((FirebaseAuth.AuthStateListener) this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            Intent i=new Intent(getApplicationContext(),register.class);
            startActivity(i);unregisterReceiver(connectivityRecevier);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(theme.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
