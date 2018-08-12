package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/16/2018.
 */

public class TeacherAttendance extends AppCompatActivity implements submit {
    Spinner class_s;
    String[] toclasses = new String[] {"Class","LKG","UKG","Ist","1","2","3","4","5","6","7","8","9","10"};
    String whichclass, updatedstudenturl, facultyid;
    long date;
    Button submit_b;
    String fromfullname, fromattendance, fromstudentid, fromfacultyid;
    long fromdate;

    String classurl = "http://139.59.18.46:8080/smsservices/services/secure/class/getallclassdetails";
    String studentsurl = "http://139.59.18.46:8080/smsservices/services/secure/faculty/getallstudentsbyclassid?classId=";


    RecyclerView students_rv;
    LinearLayoutManager lm;
    TeacherAttendanceAdapter studentsadapter;
    ArrayList<JSONObject> studentlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherattendance);

        Bundle bundle = getIntent().getExtras();

        Intent intent=getIntent();
       // date= Long.parseLong(intent.getStringExtra("Date"));
        //date = bundle.getLong("Date");
       date = intent.getLongExtra("D",1l);
      //  Log.e("DD", String.valueOf(date));
        facultyid=intent.getStringExtra("facultyid");
        Log.e("DD", facultyid);

        students_rv=(RecyclerView)findViewById(R.id.rv_students);
        lm=new LinearLayoutManager(this);
        students_rv.setLayoutManager(lm);

        submit_b=(Button)findViewById(R.id.b_submit);
        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TeacherAttendance.this, TeacherAttendanceOne.class);
                startActivity(intent2);
            }
        });

        class_s=(Spinner)findViewById(R.id.s_class);
        class_s.setPopupBackgroundResource(R.color.SchoolBlue);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,toclasses);
        class_s.setAdapter(adapter);

        class_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.SchoolDarkBlue));
                whichclass=class_s.getSelectedItem().toString();
                studentlist.clear();
                studentsadapter=new TeacherAttendanceAdapter(TeacherAttendance.this,studentlist, date, facultyid, TeacherAttendance.this);
                students_rv.setAdapter(studentsadapter);
                firstparsing(whichclass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void firstparsing(final String whichclass){
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, classurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("CLASS STATUS",status);
                    JSONArray allclassdetails = response.getJSONArray("allClassDetails");
                    for(int i=0;i<allclassdetails.length();i++){
                        JSONObject obj = allclassdetails.getJSONObject(i);
                        String cname = obj.getString("className");
                        if (cname.equals(whichclass)) {
                            String cid = obj.getString("id");
                            updatedstudenturl=studentsurl+cid;
                            parsing(updatedstudenturl);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");

                return map;
            }
        };

        obj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(obj, "tag");

    }

    public void parsing(String updatedstudenturl){
        Log.e("UURL",updatedstudenturl);
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, updatedstudenturl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("GET STUDENTS STATUS",status);
                    JSONArray allstudentdetails = response.getJSONArray("studentDetails");
                    for(int i=0;i<allstudentdetails.length();i++){
                        JSONObject obj = allstudentdetails.getJSONObject(i);
                        studentlist.add(obj);
                    }
                    studentsadapter=new TeacherAttendanceAdapter(TeacherAttendance.this,studentlist, date, facultyid, TeacherAttendance.this);
                    students_rv.setAdapter(studentsadapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");

                return map;
            }
        };

        obj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(obj, "tag");

    }

    @Override
    public void method(String fullname, String attendance, String studentid, String facultyid, long date) {
        fromfullname=fullname;
        fromattendance=attendance;
        fromstudentid=studentid;
        fromfacultyid=facultyid;
        fromdate=date;
    }
}
