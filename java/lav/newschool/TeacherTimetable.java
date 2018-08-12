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

public class TeacherTimetable extends AppCompatActivity {
    Spinner day_s;
    String[] day = new String[] {"Day","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    String[] days = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    String whichday, id, updatedurl;
    RecyclerView timetable_rv;
    LinearLayoutManager lm;
    TeacherTimeTableAdapter timetableadapter;
    ArrayList<String> objlist = new ArrayList<>();
    ArrayList<ArrayList<String>> finallist = new ArrayList<>();
    ArrayList<JSONObject> finalobj = new ArrayList<>();
    String url = "http://139.59.18.46:8080/smsservices/services/secure/faculty/getallslotsbyfacultyid?facultyId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachertimetable);

        Log.e("STRING",day.toString());

        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        updatedurl=url+id;

        day_s=(Spinner)findViewById(R.id.s_day);
        day_s.setPopupBackgroundResource(R.color.SchoolBlue);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,day);
        day_s.setAdapter(adapter);

        day_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.SchoolDarkBlue));
                whichday=day_s.getSelectedItem().toString();
                finalobj.clear();
                timetableadapter = new TeacherTimeTableAdapter(TeacherTimetable.this, finalobj);
                timetable_rv.setAdapter(timetableadapter);
                Log.e("WHICHDAY",whichday);
                parsing(whichday);
                //finalobj.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timetable_rv=(RecyclerView)findViewById(R.id.rv_timetable);
        lm=new LinearLayoutManager(this);
        timetable_rv.setLayoutManager(lm);


    }

    public void parsing(final String whichday){

        final JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, updatedurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("TIME TABLE STATUS",status);
                    JSONArray slotdetails = response.getJSONArray("slotDetails");
                    // Log.e("SLOT DETAILS",slotdetails.toString());
                    for(int i=0;i<slotdetails.length();i++){
                        JSONObject slotobj = slotdetails.getJSONObject(i);
                        String day = slotobj.getString("day");

                           // Log.e("INSIDE", insidedays.toString());
                            // Log.e("DAY",whichday);
                            if (whichday.equals(day)) {
                                // Log.e("DAY",day);
                                // Log.e("SO",slotobj.toString());
                                finalobj.add(slotobj);
                                // Log.e("FL",finalobj.toString());
                                timetableadapter = new TeacherTimeTableAdapter(TeacherTimetable.this, finalobj);
                                timetable_rv.setAdapter(timetableadapter);
                            } else if (whichday.equals("Day")) {
                                finalobj.clear();
                                timetableadapter = new TeacherTimeTableAdapter(TeacherTimetable.this, finalobj);
                                timetable_rv.setAdapter(timetableadapter);
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
}
