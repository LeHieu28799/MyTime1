package com.example.list.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi_string = intent.getExtras().getString("extra");
        Intent myIntent = new Intent(context,Music.class);
        myIntent.putExtra("extra", chuoi_string);
        context.startService(myIntent);
    }
}
