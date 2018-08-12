package lav.newschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashwin on 5/22/2018.
 */

public class StudentTimeTable extends AppCompatActivity {
    Spinner timetable_s;
    String[] days = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    RecyclerView timetable_rv;
    LinearLayoutManager lm;
    StudentTimeTableAdapter timingadapter;
    ArrayList<String> timing = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        timetable_s=(Spinner)findViewById(R.id.s_tietable);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,days);
        timetable_s.setAdapter(adapter);
        timetable_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timetable_rv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Nothing",Toast.LENGTH_SHORT).show();
            }
        });

        timetable_rv=(RecyclerView)findViewById(R.id.rv_timetble);
        lm=new LinearLayoutManager(this);
        timetable_rv.setLayoutManager(lm);
        timing.add("9Am-10AM");
        timing.add("10Am-11AM");
        timing.add("11Am-12AM");
        timing.add("12Am-1AM");
        timingadapter = new StudentTimeTableAdapter(StudentTimeTable.this,timing);

    }
    }
