package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/23/2018.
 */

public class TeacherMarks extends AppCompatActivity {
    TextView bulkpath_tv;
    EditText maxmarks_et,marksobtained_et;
    Spinner class_s,section_s,subject_s,exam_s,rollno_s;
    Button submit_b, bulkentry_b;
    String id;

    String[] cids;
    String[] cnames;

    String[] subids;
    String[] subnames;

    String[] rollids;
    String[] rollnames;

    String toclass, toclassid;
    String tosub, tosubid;
    String toroll, torollid;

    String mark;

    String sub = null;

    String URL = "http://139.59.18.46:8080/smsservices/services/secure/faculty/addstudentmarks";
    String URLclass = "http://139.59.18.46:8080/smsservices/services/secure/class/getallclassdetails";
    String URLsubject = "http://139.59.18.46:8080/smsservices/services/secure/subject/getallsubjectdetails";
    String URLrollno = "http://139.59.18.46:8080/smsservices/services/secure/student/getallstudentdetails";
    JSONObject params = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachermarks);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        bulkpath_tv=(TextView)findViewById(R.id.tv_bulkpath);

        class_s=(Spinner)findViewById(R.id.s_class);
        class_s.setPopupBackgroundResource(R.color.SchoolBlue);
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, URLclass, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("CLASS STATUS",status);

                    JSONArray classary = response.getJSONArray("allClassDetails");
                    cnames = new String[classary.length()];
                    cids = new String[classary.length()];
                    for(int j=0;j<=classary.length();j++){
                        JSONObject classobj = classary.getJSONObject(j);
                        String classid = classobj.getString("id");
                        Log.e("CLASS ID",classid);
                        String classname = classobj.getString("className");
                        Log.e("CLASS NAME",classname);
                       cnames[j]=classname;
                        cids[j]=classid;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("CLASS NAME",cnames.toString());
                //Log.e("CLASS CID",cids.toString());
                ArrayAdapter<String> cnameadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cnames);
                class_s.setAdapter(cnameadapter);
                class_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.SchoolDarkBlue));
                        toclass = class_s.getSelectedItem().toString();
                        int x = class_s.getSelectedItemPosition();
                        toclassid = cids[x];
                        Log.e("GET",toclassid);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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


        subject_s=(Spinner)findViewById(R.id.s_subject);
        subject_s.setPopupBackgroundResource(R.color.SchoolBlue);
        JsonObjectRequest subjectobj = new JsonObjectRequest(Request.Method.GET, URLsubject, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("SUBJECT STATUS",status);

                    JSONArray subary = response.getJSONArray("allSubjects");
                    subnames = new String[subary.length()];
                    subids = new String[subary.length()];
                    for(int j=0;j<=subary.length();j++){
                        JSONObject subobj = subary.getJSONObject(j);
                        String sid = subobj.getString("id");
                        Log.e("CLASS ID",sid);
                        String sname = subobj.getString("subjectName");
                        Log.e("CLASS NAME",sname);
                        subnames[j]=sname;
                        subids[j]=sid;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("CLASS NAME",cnames.toString());
                //Log.e("CLASS CID",cids.toString());
                ArrayAdapter<String> cnameadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subnames);
                subject_s.setAdapter(cnameadapter);
                subject_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.SchoolDarkBlue));
                        tosub = subject_s.getSelectedItem().toString();
                        int x = subject_s.getSelectedItemPosition();
                        tosubid = subids[x];
                         //Log.e("GET",tosubid);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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

        subjectobj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(subjectobj, "tag");


        rollno_s=(Spinner)findViewById(R.id.s_rollno);
        rollno_s.setPopupBackgroundResource(R.color.SchoolBlue);
        JsonObjectRequest rollnoobj = new JsonObjectRequest(Request.Method.GET, URLrollno, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("ROLLNO STATUS",status);

                    JSONArray rollary = response.getJSONArray("allStudentDetails");
                    rollnames = new String[rollary.length()];
                    rollids = new String[rollary.length()];
                    for(int j=0;j<=rollary.length();j++){
                        JSONObject rollobj = rollary.getJSONObject(j);
                        String rid = rollobj.getString("id");
                        Log.e("ROLL ID",rid);
                        String rname = rollobj.getString("studentId");
                        Log.e("ROLL NAME",rname);
                        rollnames[j]=rname;
                        rollids[j]=rid;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("CLASS NAME",cnames.toString());
                //Log.e("CLASS CID",cids.toString());
                ArrayAdapter<String> cnameadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, rollnames);
                rollno_s.setAdapter(cnameadapter);
                rollno_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.SchoolDarkBlue));
                        toroll = rollno_s.getSelectedItem().toString();
                        int x = rollno_s.getSelectedItemPosition();
                        torollid = rollids[x];
                        Log.e("ROLL",torollid);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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

        rollnoobj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(rollnoobj, "tag");


        maxmarks_et=(EditText)findViewById(R.id.et_maxmarks);
        marksobtained_et=(EditText)findViewById(R.id.et_marksobtained);
       // class_s=(Spinner)findViewById(R.id.s_class);
        section_s=(Spinner)findViewById(R.id.s_section);
       // subject_s=(Spinner)findViewById(R.id.s_subject);
        exam_s=(Spinner)findViewById(R.id.s_exam);

        bulkentry_b=(Button)findViewById(R.id.b_bulkentry);
        bulkentry_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent marksintent=new Intent(TeacherMarks.this,TeacherBulkMarkEntry.class);
                //startActivity(marksintent);
                bulkpath_tv.setText("");
            }
        });

        submit_b=(Button)findViewById(R.id.b_submit);
        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark = marksobtained_et.getText().toString();
                if (tosubid.equals(sub)){
                    Toast.makeText(getApplicationContext(),"Mark submited for tis subjct",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        params.put("facultyId", id);
                        params.put("classId", toclassid);
                        params.put("subjectId", tosubid);
                        params.put("mark", mark);
                        params.put("studentId", torollid);
                        Log.e("PARAM", params.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.e("RESPONSE MARK", response.toString());
                                String status = response.getString("status");
                                Log.e("MARK STATUS", status);
                                if (status.equals("Ok")) {
                                    sub = tosubid;
                                    Log.e("OK",sub);
                                    marksobtained_et.setText("");
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
            }
        });

    }
}
