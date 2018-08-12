package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ashwin on 5/5/2018.
 */

public class Attendance extends AppCompatActivity {
    TextView fullname_tv;
    Button present_b, absent_b;
    String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullname");

        fullname_tv=(TextView)findViewById(R.id.tv_fullname);
        fullname_tv.setText(fullname);
        present_b=(Button)findViewById(R.id.b_present);
        absent_b=(Button)findViewById(R.id.b_absent);



    }
    }
