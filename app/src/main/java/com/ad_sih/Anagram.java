package com.ad_sih;

import android.app.Application;

import java.util.Random;

public class Anagram extends Application {
    public static final Random RANDOM=new Random();
    public static final String[] WORDS={"BEAR","PEN","BALL","ANT"};
    public static  String randomword()
    {
        return WORDS[RANDOM.nextInt(WORDS.length)];

    }
    public static String shufflewords(String word){
        if(word!=null && !"".equals(word)){
            char a[]=word.toCharArray();
            for(int i=0;i<a.length;i++)
            {
                int j=RANDOM.nextInt(a.length);
                char tmp=a[i];
                a[i]=a[j];
                a[j]=tmp;
            }
            return new String(a);
        }
        return word;
    }
}
