package com.ad_sih;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
//import android.support.v7.widget.AppCompatButton;
import android.os.Build;
import android.widget.GridLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

//import com.nirfinz.memorygame.R;

@SuppressLint("ViewConstructor")
public class MemoryButton extends AppCompatButton {

    protected int row;
    protected int col;
    protected int frontImageID;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MemoryButton(Context context, int row, int col, int frontImageID ){
        super(context);

        this.row = row;
        this.col = col;
        this.frontImageID = frontImageID;

        front = context.getDrawable(frontImageID);
        back = context.getDrawable(R.drawable.button_question);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(row),GridLayout.spec(col));

        tempParams.width = (int) getResources().getDisplayMetrics().density * 80;
        tempParams.height = (int) getResources().getDisplayMetrics().density * 80;

        setLayoutParams(tempParams);
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontImageID() {
        return frontImageID;
    }

    public void flip(){
        if(isMatched){
            return;
        }

        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        }else{
            setBackground(front);
            isFlipped = true;
        }
    }
}
