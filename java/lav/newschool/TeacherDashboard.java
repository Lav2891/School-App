package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ashwin on 5/16/2018.
 */

public class TeacherDashboard extends AppCompatActivity {
    ImageView attendance_iv, timetable_iv, materials_iv, marks_iv, homework_iv;
    TextView teachersname_tv, facultyid_tv;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherdashboard);

        Intent intent = getIntent();
        String firstname = intent.getStringExtra("fname");
        String lastname = intent.getStringExtra("lname");
        final String facultyid = intent.getStringExtra("facultyid");
        final String id = intent.getStringExtra("id");
        userid = intent.getStringExtra("userid");
       // Log.e("USERID 1",userid);

        Log.e("F",firstname);
        Log.e("L",lastname);
        Log.e("FID",facultyid);
        Log.e("ID",id);

        teachersname_tv=(TextView)findViewById(R.id.tv_teachersname);
        teachersname_tv.setText(firstname+" "+lastname);
        facultyid_tv=(TextView)findViewById(R.id.tv_facultyid);
        facultyid_tv.setText(facultyid);

        attendance_iv=(ImageView)findViewById(R.id.iv_attendance);
        timetable_iv=(ImageView)findViewById(R.id.iv_timetable);
        materials_iv=(ImageView)findViewById(R.id.iv_material);
        marks_iv=(ImageView)findViewById(R.id.iv_marks);
        homework_iv=(ImageView)findViewById(R.id.iv_homework);

        attendance_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboard.this,TeacherAttendanceOne.class);
                intent.putExtra("facultyid",id);
                startActivity(intent);
            }
        });

        timetable_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboard.this,TeacherTimetable.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        materials_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboard.this,TeacherMaterial.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        marks_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboard.this,TeacherMarks.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        homework_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherDashboard.this,TeacherHomework.class);
                intent.putExtra("userid",userid);
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
            Intent intent=new Intent(TeacherDashboard.this,MainActivity.class);
            startActivity(intent);
            return true;
        }else if (vid == R.id.action_changepassword) {
            Intent intent=new Intent(TeacherDashboard.this,TeacherChangePassword.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
