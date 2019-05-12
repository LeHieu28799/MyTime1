package com.example.list.mainmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.list.R;
import com.example.list.calendar.CalendarActivity;
import com.example.list.todolist.MainActivity;

public class MenuActivity extends AppCompatActivity {

    private Button buttonList, buttonCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonList = (Button) findViewById(R.id.btn_menu_list);
        buttonCal = (Button) findViewById(R.id.btn_menu_cal);

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
