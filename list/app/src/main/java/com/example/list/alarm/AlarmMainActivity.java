package com.example.list.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.list.R;

import java.util.Calendar;
import java.util.Date;

public class AlarmMainActivity extends AppCompatActivity {


    Button btnHenGio, btnCancel, btnTime;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        btnHenGio = (Button) findViewById(R.id.btnHenGio);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnTime = (Button) findViewById(R.id.btnTime);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(AlarmMainActivity.this,AlarmReceiver.class);

        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                Calendar rightNow = Calendar.getInstance();
                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
                int currentMinute = rightNow.get(Calendar.MINUTE);

                int timeLeft = (gio*60 + phut) - (currentHour*60 + currentMinute);
                int timeLeftInHour = timeLeft/60;
                int timeLeftInMinute = timeLeft%60;

                intent.putExtra("extra", "on");

                pendingIntent = PendingIntent.getBroadcast(
                        AlarmMainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.setExact(alarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                Toast.makeText(AlarmMainActivity.this, "Alarm set done! " + timeLeftInHour + " hour(s) and " + timeLeftInMinute + " minute(s) from now!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AlarmMainActivity.this, "Alarm has been cancelled!", Toast.LENGTH_LONG).show();
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = Calendar.getInstance().getTime();
                String date = String.valueOf(currentTime);
                Toast.makeText(AlarmMainActivity.this, date, Toast.LENGTH_LONG).show();
            }
        });
    }
}
