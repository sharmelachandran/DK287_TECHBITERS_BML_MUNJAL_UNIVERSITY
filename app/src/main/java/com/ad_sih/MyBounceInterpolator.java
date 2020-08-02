package com.ad_sih;

import android.view.animation.Interpolator;

public class MyBounceInterpolator implements Interpolator {
    private double myAmp=1;
    private double myfreq=10;
    MyBounceInterpolator(double amp, double freq)
    {
        myAmp=amp;
        myfreq=freq;
    }
    @Override
    public float getInterpolation(float time)
    {
        return (float)(-1*Math.pow(Math.E, -time/myAmp)*Math.cos(myfreq+time)+1);
    }

}
