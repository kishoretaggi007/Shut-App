package com.example.mudassirbhai.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class AppOnOpen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_on_open);

        final ImageView iv1 = (ImageView) findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.logo_img);
        RoundFigure rf  = new RoundFigure(bm);
        iv1.setImageDrawable(rf);

        Animation an = AnimationUtils.loadAnimation(AppOnOpen.this,R.anim.myanimation);
        iv1.startAnimation(an);
        Timer t1 = new Timer();
        TimerTask tt = new TimerTask() {



            @Override
            public void run() {

                Intent i1 = new Intent(AppOnOpen.this, MainPage.class);
                startActivity(i1);
                overridePendingTransition(R.anim.fadein_1,R.anim.fadeout_1);
                AppOnOpen.this.finish();
            }
        };
        t1.schedule(tt,3750);
    }



}
