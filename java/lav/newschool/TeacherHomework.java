package lav.newschool;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/28/2018.
 */

public class TeacherHomework extends AppCompatActivity {
    Spinner class_s;
    EditText hwtitle_et, hwdescription_et;
    Button submit_b, attachments_b;
    TextView attachment_tv,date_tv;
    String getdate, cid, hwtitle,hwdescription;
    long finalDate;

    DatePickerDialog datepicker;

    String[] classes = new String[]{"Select","UKG", "5", "9", "6", "7", "8","civil-2"};
    String userid, date, toclass, x;

    String URL = "http://139.59.18.46:8080/smsservices/services/secure/homework/savehomework";
    JSONObject params = new JSONObject();

    String attachmenturl = "http://139.59.18.46:8080/smsservices/services/secure/document/savedocument";
    JSONObject paramsatt = new JSONObject();

    String classurl = "http://139.59.18.46:8080/smsservices/services/secure/class/getallclassdetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherhomeowrk);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");

        class_s = (Spinner) findViewById(R.id.s_class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        class_s.setAdapter(adapter);

        class_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toclass = class_s.getSelectedItem().toString();

                if(!toclass.isEmpty()){
                    JsonObjectRequest subjectobj = new JsonObjectRequest(Request.Method.GET, classurl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("status");
                                Log.e("CLASS STATUS",status);
                                JSONArray subjects = response.getJSONArray("allClassDetails");
                                for(int i=0;i<subjects.length();i++){
                                    JSONObject sub = subjects.getJSONObject(i);
                                   String cname = sub.getString("className");
                                    if(toclass.equals(cname)){
                                        cid = sub.getString("id");
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

                    subjectobj.setRetryPolicy(new DefaultRetryPolicy(
                            40000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    AppController.getInstance().addToRequestQueue(subjectobj, "tag");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        date_tv = (TextView) findViewById(R.id.tv_date);
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR);
                int mMonth = c.get(java.util.Calendar.MONTH);
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(TeacherHomework.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);

                        getdate = date_tv.getText().toString();
                        Log.e("GET DATE",getdate);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                        try {
                           Date date = sdf.parse(getdate);
                             finalDate = date.getTime();
                            Log.e("FINAL DATE", String.valueOf(finalDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, mYear, mMonth, mDay);
                datepicker.show();

            }
        });


        hwtitle_et = (EditText) findViewById(R.id.et_hwtitle);
        hwdescription_et = (EditText) findViewById(R.id.et_hwdescription);
        attachment_tv=(TextView)findViewById(R.id.tv_attachments);

        attachments_b = (Button) findViewById(R.id.b_attachments);

        submit_b = (Button) findViewById(R.id.b_submit);
        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hwtitle = hwtitle_et.getText().toString();
                hwdescription = hwdescription_et.getText().toString();
                if(!cid.isEmpty()||!hwtitle.isEmpty()||!userid.isEmpty()||!hwdescription.isEmpty()||!getdate.isEmpty()){
                    parsing();
                }else {
                    Toast.makeText(getApplicationContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void parsing(){
        try {
            params.put("classId",cid);
            params.put("homeWorkTitle",hwtitle);
            params.put("userId",userid);
            params.put("howeWorkDescription",hwdescription);
            params.put("assignedDate",finalDate);
            params.put("blobId","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("CN",response.toString());
                    String status = response.getString("status");
                    Log.e("HOMEWORK STATUS",status);
                    if (status.equals("Ok")){
                        Toast.makeText(getApplicationContext(),"Homework Saved",Toast.LENGTH_SHORT).show();
                        hwtitle_et.setText("");
                        hwdescription_et.setText("");
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
