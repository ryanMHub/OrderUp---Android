package com.practice.practiceorderup01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//This class extends Activity to handle the splash screen
@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView splashLogo = findViewById(R.id.splashImage); //main in image in the splash screen

        //detect if the device is in dark or light mode and switch splash image
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK){
            case Configuration.UI_MODE_NIGHT_YES:
                splashLogo.setImageResource(R.drawable.orderuplogodark);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                splashLogo.setImageResource(R.drawable.orderuplogolight);
                break;
            default:
                splashLogo.setImageResource(R.drawable.orderuplogotrans);

        }

        //loads the main activity after given milliseconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }, 4000);
    }
}