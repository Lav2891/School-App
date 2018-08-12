package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/6/2018.
 */

public class Calendar extends AppCompatActivity {
   // TextView t;
    CalendarView calander_cv;
    String url = "http://139.59.18.46:8080/smsservices/services/secure/calender/getallcalenderactivities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

      //  t = (TextView)findViewById(R.id.tv);

        calander_cv=(CalendarView)findViewById(R.id.cv_calander);

       /* long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        Log.e("CD",dateString);*/

        calander_cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day)  {
               /* SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                final String selectedDatefirst = sdf.format(new Date(calander_cv.getDate()));
                Log.e("C",selectedDatefirst);*/

               int monthofyr = month+1;

               final String selectedDate = (day<10?("0"+day):(day)) + "/" + (monthofyr<10?("0"+monthofyr):(monthofyr)) + "/" + year;
                Log.e("SD",selectedDate);
               // final String selectedDate = "30/05/2018";

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

                      /*  SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                        String d = dayFormat.format(Date.parse(dateText));

                        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                        String m = monthFormat .format(Date.parse(dateText));*/
                      Log.e("T",enddate);
                               // t.setText(dateText);

                                if (selectedDate.equals(dateText)){
                                    Log.e("I",selectedDate);
                                    Intent intent=new Intent(Calendar.this,CalanderOne.class);
                                    intent.putExtra("selectedDate",selectedDate);
                                    startActivity(intent);
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
        });




    }
}
