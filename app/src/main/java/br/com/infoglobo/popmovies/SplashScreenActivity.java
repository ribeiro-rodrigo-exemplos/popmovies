package br.com.infoglobo.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by rodrigo on 29/10/16.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        ImageView logoLoad = (ImageView) findViewById(R.id.logo_app_load);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.move_up);
        logoLoad.setAnimation(animation);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

            }
        },4000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
            getWindow().
                    getDecorView().
                    setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
    }

}
