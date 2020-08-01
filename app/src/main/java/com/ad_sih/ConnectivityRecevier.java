package com.ad_sih;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityRecevier extends BroadcastReceiver {
    public static ConnectivityRecevierListener connectivityRecevierListener;
    public ConnectivityRecevier(){
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null&&activeNetwork.isConnectedOrConnecting();
        if(connectivityRecevierListener!=null){
            connectivityRecevierListener.onNetworkConnectionChanged(isConnected);
        }
    }
    public static boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager)MyApp
                .getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        return activeNetwork!=null&&activeNetwork.isConnectedOrConnecting();

    }
    public interface ConnectivityRecevierListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
