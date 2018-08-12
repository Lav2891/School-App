package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/30/2018.
 */

public class CalanderOne extends AppCompatActivity {
    String url = "http://139.59.18.46:8080/smsservices/services/secure/calender/getallcalenderactivities";
    ArrayList<JSONObject> list = new ArrayList<>();
    RecyclerView calanderone_rv;
    LinearLayoutManager lm;
    CalanderOneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calanderone);

        calanderone_rv=(RecyclerView)findViewById(R.id.rv_calanderone);
        lm=new LinearLayoutManager(this);
        calanderone_rv.setLayoutManager(lm);

        Intent intent = getIntent();
      final String selecteddate = intent.getStringExtra("selectedDate");

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("CALENDAR STATUS",status);
                    JSONArray allactivity = response.getJSONArray("allCalenderActivities");

                    for(int i=0;i<allactivity.length();i++){
                        JSONObject obj = allactivity.getJSONObject(i);
                        String activitytype = obj.getString("activityType");
                        String activitytitle = obj.getString("activityTitle");
                        String activitydescription = obj.getString("activityDescription");
                        String startdate = obj.getString("startDate");
                        String enddate = obj.getString("endDate");

                        //  Log.e("SD",startdate);
                        long val = Long.parseLong(startdate);
                        Date date=new Date(val);
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                        String dateText = df2.format(date);

                        if (selecteddate.equals(dateText)){
                            list.add(obj);
                            adapter = new CalanderOneAdapter(CalanderOne.this,list);
                            calanderone_rv.setAdapter(adapter);
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
