package com.example.list.todolist;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.list.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView listView;
    ArrayList<Event> eventArrayList;
    Adapter adapter;

    int order_status = 0;
    int filter_status = 0;
    String filter_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listviewEvent);
        eventArrayList = new ArrayList<>();
        adapter = new Adapter(this, R.layout.event_list, eventArrayList);
        listView.setAdapter(adapter);

        database = new Database(this, "ToDoList.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS ToDoList(Id INTEGER PRIMARY KEY AUTOINCREMENT, Event VARCHAR(100), Date TEXT, DateFormat TEXT)");

        ShowList();
    }

    /*
     Hien thi tat ca event
     */
    private void ShowList() {
        Cursor data = database.GetData("SELECT * FROM ToDoList");
        eventArrayList.clear();
        eventArrayList.add(new Event(0, "default", "default", "default"));
        while(data.moveToNext()) {
            int id = data.getInt(0);
            String event = data.getString(1);
            String date = data.getString(2);
            String date_format = data.getString(3);
            eventArrayList.add(new Event(id, event, date, date_format));
        }

        adapter.notifyDataSetChanged();
        filter_status = 0;
        order_status = 0;
    }

    /*
     Xoa event
     */
    public void removeEvent(String evName, int id) {
        database.QueryData("DELETE FROM ToDoList WHERE Id = '"+ id +"' AND Event = '" + evName + "'");
        Toast.makeText(MainActivity.this, "Đã xóa " + evName, Toast.LENGTH_SHORT).show();
        if(filter_status == 1) {
            ShowListDayFilter(filter_day);
        } else ShowList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    /*
     Phan chon menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menuAdd:
                DialogAdd();
                return true;

            case R.id.seeAll:
                ShowList();
                return true;

            case R.id.dayFilter:
                filter_status = 1;
                DialogDayFilter();
                return true;

            case R.id.dateASC:
                filter_status = 0;
                order_status = 1;
                ShowListDayOrder(order_status);
                return true;

            case  R.id.dateDESC:
                filter_status = 0;
                order_status = 2;
                ShowListDayOrder(order_status);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
     Hop thoai loc ngay
     */
    private void DialogDayFilter() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_day_filter);

        Button btnOk = (Button) dialog.findViewById(R.id.btnConfirmFilter);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePickerFilter);
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String dateFilter = day + "/" + month + "/" + year;

                ShowListDayFilter(dateFilter);

                dialog.cancel();

                filter_day = dateFilter;
            }
        });

    }

    /*
     Sap xep
     */
    private void ShowListDayOrder(int status) {

        Cursor data = database.GetData("SELECT * FROM ToDoList");;

        if(status == 1) {
            data = database.GetData("SELECT * FROM ToDoList ORDER BY date(DateFormat) ASC");

        } else if(status == 2) {
            data = database.GetData("SELECT * FROM ToDoList ORDER BY date(DateFormat) DESC");
        }

        eventArrayList.clear();
        eventArrayList.add(new Event(0, "default", "default", "default"));
        while(data.moveToNext()) {
            int id = data.getInt(0);
            String event = data.getString(1);
            String date = data.getString(2);
            String date_format = data.getString(3);
            eventArrayList.add(new Event(id, event, date, date_format));
        }

        adapter.notifyDataSetChanged();
    }

    /*
      Hien thi danh sach co bo loc
     */
    private void ShowListDayFilter(String f_day) {
        Cursor data = database.GetData("SELECT * FROM ToDoList WHERE Date = '" + f_day + "'" );
        eventArrayList.clear();
        eventArrayList.add(new Event(0, "default", "default", "default"));
        int count = 0;
        while(data.moveToNext()) {
            int id = data.getInt(0);
            String event = data.getString(1);
            String date = data.getString(2);
            String date_format = data.getString(3);
            eventArrayList.add(new Event(id, event, date, date_format));
            count++;
        }

        adapter.notifyDataSetChanged();
        if(count == 0) {
            Toast.makeText(MainActivity.this, "Không có mục nào ngày " + f_day, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(MainActivity.this, "Hiển thị " + count + " mục ngày " + f_day, Toast.LENGTH_SHORT).show();
    }

    /*
      Hop thoai them event
     */
    private void DialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);

        final EditText editText = (EditText) dialog.findViewById(R.id.editTextEName);
        Button btnOk = (Button) dialog.findViewById(R.id.btnConfirmAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelAdd);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String evName = editText.getText().toString();

                int evDay = datePicker.getDayOfMonth();
                int evMonth = datePicker.getMonth() + 1;
                int evYear = datePicker.getYear();
                String evDate = evDay + "/" + evMonth + "/" + evYear;

                String dateFormated = formatDay(evDay, evMonth, evYear);

                if(evName != "" && !evName.isEmpty()) {
                    database.QueryData("INSERT INTO ToDoList VALUES(null, '" + evName + "', '" + evDate + "', '" + dateFormated + "' )");
                    Toast.makeText(MainActivity.this, "Đã thêm" + evName + " " + dateFormated, Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                    if(filter_status == 1) {
                        ShowListDayFilter(filter_day);
                    } else if(order_status == 1 || order_status == 2) {
                        ShowListDayOrder(order_status);
                    } else ShowList();

                } else dialog.cancel();
            }
        });
    }

    private String formatDay(int d, int m, int yyyy) {
        String dd, mm;

        if(d/10 == 0) {
            dd = "0" + d;
        } else dd = String.valueOf(d);

        if(m/10 == 0) {
            mm = "0" + m;
        } else mm = String.valueOf(m);

        return new StringBuilder().append(yyyy).append("-").append(mm).append("-").append(dd).toString();
    }
}
