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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/6/2018.
 */

public class EventsNews extends AppCompatActivity {
    RecyclerView events_rv;
    LinearLayoutManager lm;
    EventsAdapter adapter;
    ArrayList<JSONObject> list = new ArrayList<>();
    String url = "http://139.59.18.46:8080/smsservices/services/secure/event/getalleventdetails";
    String studentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        Intent intent = getIntent();
        studentid=intent.getStringExtra("studentid");
        //Log.e("SID",studentid);

        events_rv=(RecyclerView)findViewById(R.id.rv_events);
        lm=new LinearLayoutManager(this);
        events_rv.setLayoutManager(lm);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("EVENT STATUS",status);
                    JSONArray alleventdetails = response.getJSONArray("allEventDetails");

                    for(int i=0;i<alleventdetails.length();i++){
                        JSONObject obj = alleventdetails.getJSONObject(i);
                       String eventforwhom = obj.getString("eventForWhom");
                        if (eventforwhom.equals("parents")||eventforwhom.equals("students")){
                            list.add(obj);
                            Log.e("EVENT LIST", String.valueOf(list));
                        }

                    }
                    adapter=new EventsAdapter(EventsNews.this,list,studentid);
                    events_rv.setAdapter(adapter);

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
