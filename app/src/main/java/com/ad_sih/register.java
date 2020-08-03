package com.ad_sih;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class register extends AppCompatActivity {
    private static final int AUTHUI_REQUEST_CODE = 1001;
    private static final String TAG = "register";
    TextView tv;
    Button btn;
    Animation top,bottom;
    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();
        tv=findViewById(R.id.textView);
        btn=findViewById(R.id.button);
        tv.setAnimation(top);
        btn.setAnimation(bottom);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {m.stop();
            startActivity(new Intent(this, theme.class));
            this.finish();
        }
    }

    public void handleLoginRegister(View view) {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .setLogo(R.drawable.clown)
                        .setAlwaysShowSignInMethodScreen(true)
                        .setIsSmartLockEnabled(false)
                        .build(),
                AUTHUI_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTHUI_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;m.stop();
                //This is a New User
                Toast.makeText(getApplicationContext(), "Welcome to AD-Finder!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, profile.class);
                startActivity(intent);
                this.finish();
                // ...
            } else {
                if (response == null) {
                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
                Intent i=new Intent(getApplicationContext(),MainActivity.class);m.stop();
                startActivity(i);
                this.finish();
            }
        }

    }
}
