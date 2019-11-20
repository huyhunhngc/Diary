package com.huyproduct.diary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class pickDiary extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    private int mYear, mMonth, mDay, mHour, mMinute ;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private TextView dateEdit, timeEdit;
    private Map<Integer, String> dictMonth;
    private EditText titleEdit, contentEdit;
    private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_diary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateEdit = findViewById(R.id.date_picked);
        timeEdit = findViewById(R.id.time_picked);
        titleEdit = findViewById(R.id.title_edt);
        contentEdit = findViewById(R.id.content_edt);

        mReference = FirebaseDatabase.getInstance().getReference();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        dictMonth = new HashMap<>();
        createMonthName(dictMonth);

        dateEdit.setText(mDay+" "+dictMonth.get(mMonth)+", "+mYear);
        timeEdit.setText(mHour+":"+mMinute);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.set_date:
                        datePicker();
                        return true;
                    case R.id.set_time:
                        timePicker(true);
                        return true;

                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.postbutton, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.post_btn:
                postDiary();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        dateEdit.setText(dayOfMonth+" "+dictMonth.get(month)+", "+year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        timeEdit.setText(hourOfDay+":"+minute);
    }

    public void datePicker()
    {

        datePickerDialog = new DatePickerDialog(this, this,mYear,mMonth,mDay);
        datePickerDialog.show();
    }
    public void timePicker(boolean set24View)
    {

        timePickerDialog = new TimePickerDialog(this, this, mHour,mMinute, set24View);
        timePickerDialog.show();
    }
    public void createMonthName(Map dictMonth)
    {
        dictMonth.put(1,"Jan");
        dictMonth.put(2,"Feb");
        dictMonth.put(3,"Mar");
        dictMonth.put(4,"Apr");
        dictMonth.put(5,"May");
        dictMonth.put(6,"Jun");
        dictMonth.put(7,"Jul");
        dictMonth.put(8,"Aug");
        dictMonth.put(9,"Sep");
        dictMonth.put(10,"Oct");
        dictMonth.put(11,"Nov");
        dictMonth.put(12,"Dec");
    }
    public  void postDiary()
    {
        new AlertDialog.Builder(this)
                .setTitle("Post")
                .setMessage("Are you sure you want to post this diary?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String timeStr = timeEdit.getText().toString();
                        String dateStr = dateEdit.getText().toString();
                        String titleStr = titleEdit.getText().toString();
                        String contentStr = contentEdit.getText().toString();

                        postModule post = new postModule(dateStr,timeStr,titleStr,contentStr);

                        mReference.child("post").push().setValue(post);

                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();


    }
}
