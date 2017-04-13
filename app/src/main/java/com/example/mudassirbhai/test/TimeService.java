package com.example.mudassirbhai.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;







import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class TimeService extends Service {
    DBAdapter dbCon;
    //List<String> names=new ArrayList<String>();
    List<String> times=new ArrayList<String>();
    // constant
    public static final long NOTIFY_INTERVAL = 10 * 1000;
    private static int id=123456;
    // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        dbCon=new DBAdapter(this);
        dbCon.open();
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
        /*NotificationManager nm;
        NotificationCompat.Builder n= new NotificationCompat.Builder(this);
        nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i =new Intent(this,TimeExtension.class);
		PendingIntent pi=PendingIntent.getActivity(this,0, i,0);
        n.setContentTitle("Shutapp")
        .setContentText("Your app has been unblocked.Do you want to extend time for it?")
        .setTicker("new notification from Shutapp")
        .setWhen(System.currentTimeMillis())
        .setContentIntent(pi)
        .setDefaults(Notification.DEFAULT_ALL)
        .setSmallIcon(R.drawable.logo)
        .addAction(null);*/

    }

    class TimeDisplayTimerTask extends TimerTask {




        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {


                @Override
                public void run() {
                    times=dbCon.getAllTimes();

                    String y=getDateTime();
                    if(times.contains(y)){

                        String x=dbCon.getP(y);
                        NotificationManager nm;
                        NotificationCompat.Builder n= new NotificationCompat.Builder(getApplicationContext());
                        nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        Intent i =new Intent(getApplicationContext(),TimeExtension.class);
                        i.putExtra("pack", x);
                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0, i,0);
                        Intent i1 =new Intent(getApplicationContext(),Cancel.class);
                        i1.putExtra("nId",id);
                        PendingIntent pi1=PendingIntent.getActivity(getApplicationContext(),0, i1,0);
                        Intent i2 =new Intent(getApplicationContext(),MainPage.class);
                        PendingIntent pi2=PendingIntent.getActivity(getApplicationContext(),0, i2,0);
                			/*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                			stackBuilder.addParentStack(TimeService.class);
                			stackBuilder.addNextIntent(intent);

                			stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);*/

                        n.setContentTitle("Shutapp")
                                //.setVisibility(Notification.VISIBILITY_PUBLIC)
                                .setContentText("Your app"+x+" has been unblocked.Do you want to extend time for it?")
                                .setContentIntent(pi2)
                                .setTicker("New notification from Shutapp")
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setSmallIcon(R.drawable.logo_img)

                                .addAction(R.mipmap.ic_launcher,"Yes", pi)
                                .addAction(R.mipmap.ic_launcher, "NO", pi1);





                        dbCon.delete(y);
                        nm.notify(id,n.build());
                        id+=1;
                    }

                    // display toast
                    // Toast.makeText(getApplicationContext(), getDateTime(),Toast.LENGTH_SHORT).show();
                }

            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String a=sdf.format(new Date()).toString();
            String[] s=a.split(":");
            int h=Integer.valueOf(s[0]);
            int m=Integer.valueOf(s[1]);
            int se=Integer.valueOf(s[2]);
            //h+=3;
            String H=Integer.toString(h);
            String M=Integer.toString(m);
            String S=Integer.toString(se);
            return H+":"+M+";"+S;

        }

    }
}

