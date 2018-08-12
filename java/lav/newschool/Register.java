package lav.newschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ashwin on 5/6/2018.
 */

public class Register extends AppCompatActivity {
    Spinner religion_s;
    String[] religion = new String[] {"Select","Hindu","Muslim","Christian","Jain"};
    TextView save_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        religion_s=(Spinner)findViewById(R.id.s_religion);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,religion);
        religion_s.setAdapter(adapter);

        religion_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
