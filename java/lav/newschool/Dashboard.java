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
 * Created by Ashwin on 5/4/2018.
 */

public class Dashboard extends AppCompatActivity {
    TextView studentname_tv, rollno_tv, classnumber_tv;
    ImageView attendance_iv, marklist_iv, calendar_iv, eventsnws_iv, homework_iv, notification_iv, transport_iv;
    String firstname, lastname, studentid, id, classnumber, rollno, getcnumber, fullname, userid;

    String classnameurl = "http://139.59.18.46:8080/smsservices/services/secure/class/getallclassdetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Intent intent=getIntent();
        firstname=intent.getStringExtra("fname");
        lastname=intent.getStringExtra("lname");
        studentid=intent.getStringExtra("studentid");
        id=intent.getStringExtra("id");
        classnumber=intent.getStringExtra("classname");
       // Log.e("CN",classnumber);
        rollno = intent.getStringExtra("rollno");
        userid = intent.getStringExtra("userid");


        studentname_tv=(TextView)findViewById(R.id.tv_studentname);
        rollno_tv=(TextView)findViewById(R.id.tv_rollno);
        classnumber_tv=(TextView)findViewById(R.id.tv_classnumber);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, classnameurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RES", response.toString());
                try {
                    String status = response.getString("status");
                    Log.e("STATUS", status);
                    JSONArray a = response.getJSONArray("allClassDetails");
                    for(int i=0;i<a.length();i++){
                        JSONObject o = a.getJSONObject(i);
                        String cid = o.getString("id");
                      //  Log.e("CID",cid);
                        String cname = o.getString("className");
                        if(cid.equals(classnumber)){
                            getcnumber=cname;
                         //   Log.e("CG",getcnumber);
                            classnumber_tv.setText(getcnumber);

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


        studentname_tv.setText(firstname+" "+lastname);
        rollno_tv.setText(rollno);
        fullname=firstname+" "+lastname;

        attendance_iv=(ImageView)findViewById(R.id.iv_attendance);
        attendance_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Attendance.class);
                intent.putExtra("fullname",fullname);
                startActivity(intent);
            }
        });

        marklist_iv=(ImageView)findViewById(R.id.iv_marklist);
        marklist_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,MarkList.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        calendar_iv=(ImageView)findViewById(R.id.iv_calendar);
        calendar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Calendar.class);
                startActivity(intent);
            }
        });

        eventsnws_iv=(ImageView)findViewById(R.id.iv_eventsnews);
        eventsnws_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,EventsNews.class);
                intent.putExtra("studentid",studentid);
                startActivity(intent);
            }
        });

        homework_iv=(ImageView)findViewById(R.id.iv_homework);
        homework_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,HomeWork.class);
                intent.putExtra("classno",classnumber);
                startActivity(intent);
            }
        });

        notification_iv=(ImageView)findViewById(R.id.iv_notification);
        notification_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Notification.class);
                intent.putExtra("studentid",studentid);
                Log.e("SID",studentid);
                startActivity(intent);
            }
        });

        transport_iv=(ImageView)findViewById(R.id.iv_transport);
        transport_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Transport.class);
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
            Intent intent=new Intent(Dashboard.this,MainActivity.class);
            startActivity(intent);
            return true;
        }else if (vid == R.id.action_changepassword) {
            Intent intent=new Intent(Dashboard.this,StudentChangePassword.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
