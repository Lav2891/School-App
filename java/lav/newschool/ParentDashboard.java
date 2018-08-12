package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ashwin on 5/28/2018.
 */

public class ParentDashboard extends AppCompatActivity {
    TextView studentname_tv, rollno_tv;
    ImageView attendance_iv, marklist_iv, calendar_iv, eventsnws_iv, homework_iv, notification_iv, transport_iv;
    String firstname, lastname, studentid, id, puserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parentdashboard);

        Intent intent=getIntent();
        firstname=intent.getStringExtra("fname");
        lastname=intent.getStringExtra("lname");
        studentid=intent.getStringExtra("studentid");
        id=intent.getStringExtra("id");
        puserid = intent.getStringExtra("puserid");

        studentname_tv=(TextView)findViewById(R.id.tv_studentname);
        studentname_tv.setText(firstname+" "+lastname);
        rollno_tv=(TextView)findViewById(R.id.tv_rollno);
        rollno_tv.setText(studentid);

        attendance_iv=(ImageView)findViewById(R.id.iv_attendance);
        attendance_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,Attendance.class);
                startActivity(intent);
            }
        });

        marklist_iv=(ImageView)findViewById(R.id.iv_marklist);
        marklist_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,MarkList.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        calendar_iv=(ImageView)findViewById(R.id.iv_calendar);
        calendar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,Calendar.class);
                startActivity(intent);
            }
        });

        eventsnws_iv=(ImageView)findViewById(R.id.iv_eventsnews);
        eventsnws_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,EventsNews.class);
                intent.putExtra("studentid",studentid);
                startActivity(intent);
            }
        });

        homework_iv=(ImageView)findViewById(R.id.iv_homework);
        homework_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,HomeWork.class);
                startActivity(intent);
            }
        });

        notification_iv=(ImageView)findViewById(R.id.iv_notification);
        notification_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,Notification.class);
                intent.putExtra("studentid",studentid);
                startActivity(intent);
            }
        });

        transport_iv=(ImageView)findViewById(R.id.iv_transport);
        transport_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentDashboard.this,Transport.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(),"pressed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_teachersdashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int vid = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (vid == R.id.action_logout) {
            Intent intent=new Intent(ParentDashboard.this,MainActivity.class);
            startActivity(intent);
            return true;
        }else if (vid == R.id.action_changepassword) {
            Intent intent=new Intent(ParentDashboard.this,ParentChangePassword.class);
            intent.putExtra("userid",puserid);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

