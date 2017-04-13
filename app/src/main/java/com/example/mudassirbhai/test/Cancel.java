package com.example.mudassirbhai.test;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Cancel extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int notificationId = intent.getIntExtra("nId", 123456);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
        //Toast.makeText(this,"Successfully Unblocked your Application",Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Successfully Unblocked your application",Toast.LENGTH_SHORT).show();
	}

}
