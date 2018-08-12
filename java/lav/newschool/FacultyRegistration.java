package lav.newschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Ashwin on 5/18/2018.
 */

public class FacultyRegistration extends AppCompatActivity {
    Spinner age_s;
    String[] age = new String[] {"Select","21","22","23","24","25","26","27","28","29","30"};
    Spinner employeetype_s;
    String[] employeetype = new String[] {"Select","Full time","Part time"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultyregistration);

        age_s=(Spinner)findViewById(R.id.s_age);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,age);
        age_s.setAdapter(adapter);

        age_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
            }
        });

        employeetype_s=(Spinner)findViewById(R.id.s_employeetype);
        ArrayAdapter<String> adapteremptype = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,employeetype);
        employeetype_s.setAdapter(adapteremptype);

        employeetype_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
