package com.example.list.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.list.R;
import com.example.list.mainmenu.MenuActivity;

public class CalendarActivity extends AppCompatActivity {

    Button buttontnCalBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        buttontnCalBack = (Button) findViewById(R.id.btnCalBackToMenu);
        buttontnCalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
