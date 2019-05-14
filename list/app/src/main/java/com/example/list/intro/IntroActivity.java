package com.example.list.intro;

import android.content.Intent;
import android.database.Cursor;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.list.R;
import com.example.list.mainmenu.MenuActivity;
import com.example.list.todolist.Database;

import java.util.Calendar;


public class IntroActivity extends AppCompatActivity {

    public Database database;
    private ViewPager viewPager;
    private SlideApater slideApater;
    private Button buttonStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        database = new Database(this, "ToDoList.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS ToDoList(Id INTEGER PRIMARY KEY AUTOINCREMENT, Event VARCHAR(100), Date TEXT, DateFormat TEXT)");

        String td_list = getList(getToday());
        String tm_list = getList(getTommorow());
        System.out.print(td_list);
        System.out.print(tm_list);

        slideApater = new SlideApater(this, td_list, tm_list);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(slideApater);

        System.out.print("3 " + slideApater.getToday_list());
        System.out.print("4" + slideApater.getTommorow_list());

        buttonStart = (Button) findViewById(R.id.btnStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getList(String d) {
        Cursor data = database.GetData("SELECT * FROM ToDoList WHERE Date = '" + d + "'");
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        while(data.moveToNext()) {
            String event = data.getString(1);
            count++;
            if(count>6) {
                stringBuilder.append("...");
                break;
            } else {
                stringBuilder.append(event);
                stringBuilder.append(" \n");
            }
        }
        if(count == 0) {
            return "Nothing to do....";
        } else  return stringBuilder.toString();
    }

    public String getToday() {
        Calendar today = Calendar.getInstance();
        int day_td = today.get(Calendar.DAY_OF_MONTH);
        int month_td = today.get(Calendar.MONTH) + 1;
        int year_td = today.get(Calendar.YEAR);
        return day_td + "/" + month_td + "/" + year_td;
    }

    public String getTommorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) +1;
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }
}
