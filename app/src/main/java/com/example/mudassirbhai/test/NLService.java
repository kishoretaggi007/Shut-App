package com.example.mudassirbhai.test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    NotificationManager nManager;
    DBAdapter dbCon;
    List<String> names=new ArrayList<String>();
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.test1.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
        nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        dbCon=new DBAdapter(this);
        dbCon.open();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        names=dbCon.getAllNames();
        Log.i(TAG,"**********  onNotificationPosted");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new  Intent("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
        String s= sbn.getPackageName();
        nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //i.putExtra("notification_event","onNotificationPosted :" +s + "\n");
//        Intent intent=new Intent();
//    	PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0, i,0);
//    	this.nManager.notify(12321, new Notification.Builder(this)
//        .setContentTitle("").setContentText("")
//
//        .setPriority(Notification.PRIORITY_DEFAULT)
//        .setContentIntent(pi)
//        .setAutoCancel(true)
//        .build());


        nManager.cancel(12321);
        if(names.contains(s))

        {


            //cancelNotification(sbn.getPackageName(),sbn.getTag(),sbn.getId());
            //cancelNotification(sbn.getKey());
            //nManager.cancel(sbn.getKey(), sbn.getId());
            //cancelAllNotifications();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                cancelNotification(sbn.getPackageName(), sbn.getTag(), sbn.getId());
            }
            else {
                cancelNotification(sbn.getKey());
            }
            Intent intent=new Intent();
            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0, intent,0);
            this.nManager.notify(12321, new Notification.Builder(this)
                    .setContentTitle("").setContentText("")

                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build());


            nManager.cancel(12321);




            dbCon.updateRecord(s);}
        sendBroadcast(i);


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("com.example.test1.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}

