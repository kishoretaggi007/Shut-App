package com.example.mudassirbhai.test;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainPage extends AppCompatActivity {

    NotificationReceiver nReceiver;
    DBAdapter dbCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Boolean blah;

        super.onCreate(savedInstanceState);
        blah = displayStatus();
        if(blah==false){
            Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }

        setContentView(R.layout.activity_main_page);

    /*    // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        final ImageView iv1 = (ImageView) findViewById(R.id.imageView4);//settings
        final ImageView iv2 = (ImageView) findViewById(R.id.imageView5);//unhide
        final ImageView iv3 = (ImageView) findViewById(R.id.imageView6);//hide
        final ImageView iv4 = (ImageView) findViewById(R.id.imageView7);//timer
        //final ImageView iv5 = (ImageView) findViewById(R.id.imageView8);//center

//        Bitmap bm1 =e BitmapFactory.decodeResource(getResources(),R.drawable.logoact);
//        RoundFigure ri1  = new RoundFigure(bm1);
//        iv5.setImageDrawable(ri1);

        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.s);
        RoundFigure ri2  = new RoundFigure(bm2);
        iv1.setImageDrawable(ri2);

        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.u);
        RoundFigure ri3  = new RoundFigure(bm3);
        iv2.setImageDrawable(ri3);

        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.h);
        RoundFigure ri4  = new RoundFigure(bm4);
        iv3.setImageDrawable(ri4);

        Bitmap bm5 = BitmapFactory.decodeResource(getResources(),R.drawable.t);
        RoundFigure ri5  = new RoundFigure(bm5);
        iv4.setImageDrawable(ri5);

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
        startService(new Intent(this, TimeService.class));
        registerReceiver(nReceiver,filter);
        dbCon=new DBAdapter(this);
        dbCon.open();


        final Animation a1 = AnimationUtils.loadAnimation(MainPage.this, R.anim.animation);
        /* final Timer t1 = new Timer();
        final TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                iv5.setImageResource(R.drawable.shuts);
            }
        };
        */
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.startAnimation(a1);
                Intent i1 = new Intent(MainPage.this, SettingsActivity.class);
                startActivity(i1);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv2.startAnimation(a1);
                Intent i1 = new Intent(MainPage.this, BlockedApps.class);
                startActivity(i1);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv3.startAnimation(a1);
                Intent i1 = new Intent(MainPage.this, AllAppsActivity.class);
                startActivity(i1);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv4.startAnimation(a1);
                Intent i1 = new Intent(MainPage.this, BlockedApps1.class);
                startActivity(i1);
            }
        });

    }
    public boolean displayStatus(){
        ComponentName cn = new ComponentName(this, NLService.class);
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        final boolean y = flat != null && flat.contains(cn.flattenToString());
        return y;
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }


    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();

        }
    }
}
