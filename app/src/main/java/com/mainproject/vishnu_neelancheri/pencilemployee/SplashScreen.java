package com.mainproject.vishnu_neelancheri.pencilemployee;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mainproject.vishnu_neelancheri.pencilemployee.home.HomeActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.login.LoginActivity;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.GetPrefs;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PrefModel;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                PrefModel prefModel = GetPrefs.getInstance().getSharedPref( SplashScreen.this );
                if ( prefModel.getToken().isEmpty() || prefModel.getUserId().isEmpty() ){
                    intent = new Intent( SplashScreen.this, LoginActivity.class );
                    startActivity( intent );
                    finish();
                }else {
                    intent = new Intent( SplashScreen.this, HomeActivity.class );
                    startActivity( intent );
                    finish();
                }

            }
        }, 1000);
    }
}
