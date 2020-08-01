package com.ad_sih;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener,ConnectivityRecevier.ConnectivityRecevierListener {
    CircleImageView profileImageView;
    TextInputEditText displayName,age;
    RadioButton male,female,others;
    Spinner day;
    TextView date,time;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    Button btn;
    ProgressBar progressBar;
    String DISPLAY_NAME = null;
    String GENDER=null,AGE=null,DATE=null;
    String CURRENT_DAY=null,CUREENT_DATE=null,CURRENT_MONTH=null,CURRENT_YEAR=null,CURRENT_TIME=null;

    int TAKE_IMAGE_CODE = 1001,Profile_score=0;
    FirebaseUser firebaseUser;
    private static final String TAG = "profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_profile);
        checkInternetConnection();
        profileImageView=findViewById(R.id.profileimage);
        displayName=findViewById(R.id.name);
        btn=findViewById(R.id.profileupdate);
        male=findViewById(R.id.m);
        female=findViewById(R.id.f);
        others=findViewById(R.id.o);
        age=findViewById(R.id.age);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        day=findViewById(R.id.day);
        progressBar=findViewById(R.id.pb);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.days,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);
        day.setOnItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Log.d(TAG, "onCreate: " + user.getDisplayName());
            if (user.getDisplayName() != null) {
                displayName.setText(user.getDisplayName());
                displayName.setSelection(user.getDisplayName().length());
            }
        }
        progressBar.setVisibility(View.GONE);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GENDER="male";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GENDER="female";
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GENDER="others";
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=2000;
                int month=0;
                int day=1;
                DatePickerDialog dialog=new DatePickerDialog(profile.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String m= String.valueOf(month);
                if(month/10==0)
                    m="0"+m;
                String idate=dayOfMonth+"-"+m+"-"+year;
                CURRENT_YEAR= String.valueOf(year);
                CURRENT_MONTH=m;
                CUREENT_DATE=String.valueOf(dayOfMonth);
                date.setText(idate);
                Log.d(TAG,"OnDateSet: date:"+dayOfMonth+"/"+month+"/"+year);
            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(profile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //initialize hour and minute
                                int h=hourOfDay;
                                int m=minute;
                                String itime=h+":"+m;
                                try {
                                    Date date=new SimpleDateFormat("HH:mm").parse(itime);
                                    SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");
                                    time.setText(f12hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
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
        i.putExtra("activity","profile");
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
    public void updateProfile(final View view)
    {
        view.setEnabled(false);
        checkDateDayTime();
        progressBar.setVisibility(View.VISIBLE);
    }
    private void checkDateDayTime(){
        DISPLAY_NAME = displayName.getText().toString();
        AGE=age.getText().toString();
        CURRENT_TIME=time.getText().toString();

        if (TextUtils.isEmpty(DISPLAY_NAME) &&
                TextUtils.isEmpty(AGE) && TextUtils.isEmpty(GENDER) && TextUtils.isEmpty(date.getText().toString()) &&
                TextUtils.isEmpty(time.getText().toString())&& TextUtils.isEmpty(CURRENT_DAY)) {
            displayName.setError("Required");
            age.setError("Required");
            male.setError("Required");
            female.setError("Required");
            others.setError("Required");
            date.setError("Required");
            time.setError("Required");
        } else if (TextUtils.isEmpty(DISPLAY_NAME) ){
            displayName.setError("Enter Name");
        } else if (TextUtils.isEmpty(AGE)) {
            age.setError("Required");
        } else if (TextUtils.isEmpty(date.getText().toString())) {
            date.setError("Required");
        }else if (TextUtils.isEmpty(time.getText().toString())) {
            time.setError("Required");
        }else if(TextUtils.isEmpty(GENDER)){
            male.setError("Required");
            female.setError("Required");
            others.setError("Required");
        }else if(TextUtils.isEmpty(CURRENT_DAY)){
            Toast.makeText(getApplicationContext(),"Choose Today's day",Toast.LENGTH_SHORT).show();
        }else {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat m = new SimpleDateFormat("mm");
            SimpleDateFormat h = new SimpleDateFormat("hh");
            SimpleDateFormat a = new SimpleDateFormat("aa");
            int c1m = Integer.parseInt(m.format(calendar.getTime()));
            int range = c1m - 1;
            String ctime = h.format(calendar.getTime()) + ":" + c1m + " " + a.format(calendar.getTime());
            String c1time = h.format(calendar.getTime()) + ":" + range + " " + a.format(calendar.getTime());
            SimpleDateFormat dateformat1 = new SimpleDateFormat("dd");
            SimpleDateFormat monthformat1 = new SimpleDateFormat("MM");
            SimpleDateFormat yearformat1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat dayformat = new SimpleDateFormat("EEEE");
            String cday = dayformat.format(calendar.getTime());
            if (CURRENT_TIME.equals(ctime) || CURRENT_TIME.equals(c1time))
                Profile_score += 1;
            if (CUREENT_DATE.equals(dateformat1.format(calendar.getTime())))
                Profile_score += 1;
            if (CURRENT_MONTH.equals(monthformat1.format(calendar.getTime())))
                Profile_score += 1;
            if (CURRENT_YEAR.equals(yearformat1.format(calendar.getTime())))
                Profile_score += 1;
            if (CURRENT_DAY.equals(cday))
                Profile_score += 1;
            storedata();
        }
    }
    private void storedata()
    {
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid());
        //String score=String.valueOf(Profile_score);
        player reg = new player(DISPLAY_NAME,AGE,GENDER,Profile_score);
        register.setValue(reg);
        Intent i=new Intent(getApplicationContext(),theme.class);
        startActivity(i);unregisterReceiver(connectivityRecevier);finish();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Update your Profile",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CURRENT_DAY=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
