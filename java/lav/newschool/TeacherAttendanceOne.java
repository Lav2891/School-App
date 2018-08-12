package lav.newschool;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;
import com.stacktips.view.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ashwin on 6/2/2018.
 */

public class TeacherAttendanceOne extends AppCompatActivity {
    CalendarView calander_cv;
    String facultyid;

    DisabledColorDecorator d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherattendanceone);

        d = new DisabledColorDecorator();

        Intent intent = getIntent();
        facultyid=intent.getStringExtra("facultyid");

        calander_cv=(CalendarView)findViewById(R.id.cv_calander);

        calander_cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                int monthofyr = month + 1;

                final String selectedDate = (day < 10 ? ("0" + day) : (day)) + "/" + (monthofyr < 10 ? ("0" + monthofyr) : (monthofyr)) + "/" + year;
                Log.e("SD", selectedDate);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(selectedDate);
                 Boolean check = d.isPastDay(date);
                    if (check==true){
                        Toast.makeText(getApplicationContext(),"Attendance Marked. To make changes go to Update",Toast.LENGTH_SHORT).show();
                    } else if(check==false) {
                        Log.e("PAST ONE", date.toString());
                        long getdate = date.getTime();
                        Log.e("GD", String.valueOf(getdate));

                        Intent intent = new Intent(TeacherAttendanceOne.this, TeacherAttendance.class);
                        //intent.putExtra("Date",getdate);
                        Bundle bundle = new Bundle();
                        bundle.putLong("Date", getdate);

                        intent.putExtra("D", (long) getdate);
                        intent.putExtra("facultyid", facultyid);
                        startActivity(intent);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            });
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(TeacherAttendanceOne.this, TeacherDashboard.class);
        startActivity(intent3);
    }

    class DisabledColorDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {
            if (CalendarUtils.isPastDay(dayView.getDate())) {
                int color = Color.parseColor("#a9afb9");
                dayView.setBackgroundColor(color);
            }

        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        boolean isPastDay(Date date) {
            android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();

            // set the calendar to start of today
            c.set(android.icu.util.Calendar.HOUR_OF_DAY, 0);
            c.set(android.icu.util.Calendar.MINUTE, 0);
            c.set(android.icu.util.Calendar.SECOND, 0);
            c.set(android.icu.util.Calendar.MILLISECOND, 0);

            // and get that as a Date
            android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
            cal.add(android.icu.util.Calendar.DATE, -1);
            Date today = cal.getTime();

            // test your condition, if Date specified is before today
            if (date.before(today)) {
                Log.e("PAST", String.valueOf(date));

                return true;
            }
            return false;
        }
    }

}


