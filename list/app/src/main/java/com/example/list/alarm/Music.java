package com.example.list.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.list.R;


public class Music extends Service {
    MediaPlayer mediaPlayer;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String keyReceiver = intent.getExtras().getString("extra");

        if(keyReceiver.equals("on")){
            id = 1;
        } else if (keyReceiver.equals("off")){
            id = 0;
        }

        if(id == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
            mediaPlayer.start();
            id = 0;
            Toast.makeText(Music.this,"Alarm is being played...!",Toast.LENGTH_LONG).show();
        } else if (id == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }


        return START_NOT_STICKY;
    }
}
