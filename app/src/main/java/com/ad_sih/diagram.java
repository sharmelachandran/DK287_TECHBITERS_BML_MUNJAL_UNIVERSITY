package com.ad_sih;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class diagram extends AppCompatActivity implements ConnectivityRecevier.ConnectivityRecevierListener{
    RelativeLayout relativeLayout;
    Paint paint;
    View view;
    Path path2;
    upload file2;
    Bitmap bitmap;
    Canvas canvas;
    Button button,btn,H;
    private  String theme,store;
    private int pentagon_score;
    private static final int PICK_FILE=1023;
    ConnectivityRecevier connectivityRecevier=new ConnectivityRecevier();
    //private DatabaseReference ref;
    MyCanvas myCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_diagram);
        checkInternetConnection();
        theme=getIntent().getStringExtra("theme");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        if(theme.equals("nature"))
            store="Nature";
        else if(theme.equals("food"))
            store="Food";
        else if(theme.equals("cricket"))
            store="Sports";
        else if(theme.equals("entertainment"))
            store="Entertainment";
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);
        //ref= FirebaseDatabase.getInstance().getReference().child("IMAGES");
        button = (Button)findViewById(R.id.button);
        btn = (Button)findViewById(R.id.button1);
        H=findViewById(R.id.h);
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(theme.equals("nature")) {
                    Intent i = new Intent(getApplicationContext(), home.class);
                    i.putExtra("level", 8);
                    startActivity(i);
                    unregisterReceiver(connectivityRecevier);finish();
                }
                else if(theme.equals("food")){
                    Intent i = new Intent(getApplicationContext(), fhome.class);
                    i.putExtra("level", 8);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(theme.equals("cricket")){
                    Intent i = new Intent(getApplicationContext(), chome.class);
                    i.putExtra("level", 8);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }else if(theme.equals("entertainment")){
                    Intent i = new Intent(getApplicationContext(), mhome.class);
                    i.putExtra("level", 8);
                    startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                }
            }
        });
        view = new diagram.SketchSheetView(diagram.this);

        paint = new Paint();

        path2 = new Path();

        relativeLayout.addView(view, new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        paint.setDither(true);

        paint.setColor(Color.parseColor("#000000"));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myCanvas.onDraw();
                Intent i=new Intent(getApplicationContext(),diagram.class);
                i.putExtra("theme",theme);
                startActivity(i);unregisterReceiver(connectivityRecevier);finish();
                // this.setBackgroundColo(Color.WHITE);
                //SketchSheetView sketchSheetView=new SketchSheetView();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                screenshot(v);


            }
        });

    }
    //to check internet connection
    private void checkInternetConnection(){
        boolean isConnected=ConnectivityRecevier.isConnected();
        if(!isConnected){
            changeActivity();
        }
    }
    private void changeActivity(){
        Intent i=new Intent(this,OfflineActivity.class);
        this.onPause();
        i.putExtra("activity","diagram");
        startActivity(i);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            changeActivity();
        }
    }
    //to resume when it is pause due to no internet
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityRecevier,intentFilter);
        MyApp.getInstance().setConnectivityListner(this);
    }
    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);

            bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            this.setBackgroundColor(Color.WHITE);
        }

        private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            diagram.DrawingClass pathWithPaint = new diagram.DrawingClass();

            canvas.drawPath(path2, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                path2.moveTo(event.getX(), event.getY());

                path2.lineTo(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                path2.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(path2);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {

                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }
    }

    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }
    private void screenshot(View view){

        final String mfile=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filename= Environment.getExternalStorageDirectory()+"/sih/"+ mfile+".jpg";
        View root=getWindow().getDecorView().getRootView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(true);
        File file=new File(filename);
        file.getParentFile().mkdir();
        String postUrl = "http://ec2-54-67-30-22.us-west-1.compute.amazonaws.com:5000/predict";//aws instance url
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri=Uri.fromFile(file);
            intent.setDataAndType(uri,"image/jpeg");
            Uri uri1=intent.getData();
            //uploading to aws instance
            AndroidNetworking.upload(postUrl)
                    .addMultipartFile("file",file)
                    .addMultipartParameter("key","value")
                    .setTag("uploadTest")
                    .setPriority(Priority.HIGH)
                    .build() // setting an executor to get response or completion on that executor thread
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            //Toast.makeText(getApplicationContext(),"Processing in upload method",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getApplicationContext(),"Response in upload method",Toast.LENGTH_SHORT).show();
                            StringBuilder s;
                            JSONArray arr = null;
                            JSONArray arr1 = null;

                            try {
                                arr = response.getJSONArray("labels");
                                arr1 = response.getJSONArray("scores");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String[] label=new String[arr.length()];
                            String[] score=new String[arr1.length()];
                            s = new StringBuilder("Response:");
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    label[i]= arr.get(i).toString();
                                    score[i]= arr1.get(i).toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                s.append("\n").append(label[i]).append("-").append(score[i]).append(".");

                            }
                            if (Arrays.asList(label).contains("pentagon"))
                            {
                                int i=Arrays.asList(label).indexOf("pentagon");
                                Double d=Double.valueOf(score[i]);
                                if(d>=0.9790000000000000){
                                    pentagon_score=1;
                                }else{
                                    pentagon_score=0;
                                }

                            }
                            else{
                                pentagon_score=0;
                            }
                            storedata(pentagon_score);
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            //Toast.makeText(getApplicationContext(),"Error in upload method",Toast.LENGTH_SHORT).show();

                        }
                    });

           /* StorageReference Folder= FirebaseStorage.getInstance().getReference().child("nature").child("clockdrawing");
            final StorageReference file_name=Folder.child("image"+uri1.getLastPathSegment());
            file_name.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            String file_name1=mfile;
                            file2=new upload(file_name1,url);
                            ref.push().setValue(file2);
                            Toast.makeText(getApplicationContext(),"Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });*/
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*//image upload in firebase
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri=Uri.fromFile(file);
            intent.setDataAndType(uri,"image/jpeg");
            Uri uri1=intent.getData();
            StorageReference Folder= FirebaseStorage.getInstance().getReference().child(theme).child("pentagon");
            final StorageReference file_name=Folder.child("image"+uri1.getLastPathSegment());
            file_name.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=String.valueOf(uri);
                            String file_name1=mfile;
                            file2=new upload(file_name1,url);
                            Toast.makeText(getApplicationContext(),"Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/

    }
    private void storedata(int score) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child(FirebaseAuth.getInstance().getUid()).child(store);
        register.child("MMSE").child("Pentagon").setValue(score);
        score=score*2;
        register.child("AD_Finder").child("Pentagon").setValue(score);
        if (theme.equals("nature")) {
            Intent i = new Intent(getApplicationContext(), home.class);
            i.putExtra("level", 9);
            startActivity(i);
            finish();
        } else if (theme.equals("food")) {
            Intent i = new Intent(getApplicationContext(), fhome.class);
            i.putExtra("level", 9);
            startActivity(i);
            finish();
        } else if (theme.equals("cricket")) {
            Intent i = new Intent(getApplicationContext(), chome.class);
            i.putExtra("level", 9);
            startActivity(i);
            finish();
        } else if (theme.equals("entertainment")) {
            Intent i = new Intent(getApplicationContext(), mhome.class);
            i.putExtra("level", 9);
            startActivity(i);
            finish();

        }
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press Home to go back",Toast.LENGTH_SHORT).show();
    }
}

