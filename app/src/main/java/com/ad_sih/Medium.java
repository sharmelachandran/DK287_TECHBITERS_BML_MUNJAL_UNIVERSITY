package com.ad_sih;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Medium extends AppCompatActivity implements View.OnClickListener {
    private TextView wordtv;
    private EditText wordenteredtv;
    private Button val, newg,score;
    private String wordtofind;
    int c=0;
    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_medium);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        wordtv=(TextView)findViewById(R.id.wordTv);
        wordenteredtv=(EditText)findViewById(R.id.wordEnteredEt);
        val=(Button)findViewById(R.id.validate);
        score=findViewById(R.id.count);
        val.setOnClickListener(this);
        //newg=(Button)findViewById(R.id.newGame);
        //newg.setOnClickListener(this);
        newg();

    }
    @Override
    public void onClick(View view) {
        if(view==val){
            val();
            c++;
            if(c==4)
            {m.stop();
                Intent i=new Intent(getApplicationContext(), Chooselevel.class);
                startActivity(i);
                finish();
            }
        }
        else if(view==newg){
            newg();
            c++;
            if(c==4)
            {m.stop();
                Intent i=new Intent(getApplicationContext(), Chooselevel.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void val() {
        String w=wordenteredtv.getText().toString();
        if(wordtofind.equals(w)){
            int i=c+1;
            score.setText("Score\n"+i);
            Toast.makeText(this,"CORRECT",Toast.LENGTH_LONG).show();
            newg();
        }
        else
        {
            Toast.makeText(this,"WRONG",Toast.LENGTH_LONG).show();

        }
    }
    private void newg() {
        wordtofind=Anagram.randomword();
        String wordshuffled=Anagram.shufflewords(wordtofind);
        wordtv.setText(wordshuffled);
        wordenteredtv.setText("");
    }
    public void onBackPressed(){
        // Intent intent = new Intent(this, MenuActivity.class);
        // startActivity(intent);
        Toast.makeText(getApplicationContext(),"Complete level",Toast.LENGTH_LONG).show();
    }
}
