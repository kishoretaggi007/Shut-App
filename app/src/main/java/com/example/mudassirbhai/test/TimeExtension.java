package com.example.mudassirbhai.test;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeExtension extends BroadcastReceiver  {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		int notificationId = intent.getIntExtra("nId", 123456);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
		
		String p=intent.getExtras().getString("pack");
		Intent i=new Intent(context,TimeExtended1.class);
		i.putExtra("package",p);
	}

	
	
}
