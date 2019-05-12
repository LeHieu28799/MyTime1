package com.example.list.intro;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.list.R;


import java.util.Calendar;


public class SlideApater extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    String today_list = "1";
    String tommorow_list = "2";


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

    public SlideApater(Context context, String today_list, String tommorow_list) {
        this.context = context;
        this.today_list = today_list;
        this.tommorow_list = tommorow_list;
    }

    public String getToday_list() {
        return today_list;
    }

    public void setToday_list(String today_list) {
        this.today_list = today_list;
    }

    public String getTommorow_list() {
        return tommorow_list;
    }

    public void setTommorow_list(String tommorow_list) {
        this.tommorow_list = tommorow_list;
    }

    public String[] intro_name = {
            "Hôm nay", "Ngày mai"
    };
    public String[] intro_date = {
            getToday(), getTommorow()
    }   ;




    public int[]  intro_backgroundcolor = {
            Color.rgb(55,55,55),
            Color.rgb(110,49,89)
    };

    @Override
    public int getCount() {
        return intro_date.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(ConstraintLayout)o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        String[] intro_list = {
                this.today_list,
                this.tommorow_list
        };

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.slide);

        TextView introName =(TextView) view.findViewById(R.id.introName);
        TextView introDate = (TextView) view.findViewById(R.id.introDate);
        TextView introList = (TextView) view.findViewById(R.id.introList);

        constraintLayout.setBackgroundColor(intro_backgroundcolor[position]);
        introName.setText(intro_name[position]);
        introDate.setText(intro_date[position]);
        introList.setText(intro_list[position]);
        container.addView(view);
        return view;
    }
}
