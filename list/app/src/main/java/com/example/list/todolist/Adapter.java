package com.example.list.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.list.R;

import java.util.List;


public class Adapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Event> eventList;

    public Adapter(MainActivity context, int layout, List<Event> eventList) {
        this.context = context;
        this.layout = layout;
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txt;
        TextView date;
        ImageView del, edit;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txt = (TextView) convertView.findViewById(R.id.textviewName);
            viewHolder.date = (TextView) convertView.findViewById(R.id.textViewDate);
            viewHolder.del = (ImageView) convertView.findViewById(R.id.imageviewDel);
            viewHolder.edit = (ImageView) convertView.findViewById(R.id.imageviewEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Event event = eventList.get(position);

        viewHolder.txt.setText(event.getName());
        viewHolder.date.setText(event.getDate());
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.editEvent(event.getName(), event.getId(), event.getDate());
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.removeEvent(event.getName(), event.getId());
            }
        });

        return convertView;
    }
}
