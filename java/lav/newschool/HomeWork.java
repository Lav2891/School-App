package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Ashwin on 5/23/2018.
 */

public class HomeWork extends AppCompatActivity {
    String classnumber;
    String url = "http://139.59.18.46:8080/smsservices/services/secure/homework/getallhomeworkdetailsbyclassid?classId=";
    String updatedurl;

    TextView date_tv, hwtitle_tv, hwdescription_tv;
    ArrayList<JSONObject> list = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager lm;
    HomeworkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework);

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        Intent intent = getIntent();
       classnumber = intent.getStringExtra("classno");
        Log.e("CLASS NO",classnumber);

        updatedurl = url+classnumber;

        JsonObjectRequest subjectobj = new JsonObjectRequest(Request.Method.GET, updatedurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("CLASS STATUS",status);
                    JSONArray subjects = response.getJSONArray("homeWorkList");
                    for(int i=0;i<subjects.length();i++){
                        JSONObject sub = subjects.getJSONObject(i);
                        list.add(sub);
                       /* String title = sub.getString("homeWorkTitle");
                        String description = sub.getString("howeWorkDescription");
                        String assigneddate = sub.getString("assignedDate");

                        long val = Long.parseLong(assigneddate);
                        Date date=new Date(val);
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                        String dateText = df2.format(date);

                        Log.e("STRING DATE TEXT",dateText);*/
                    }
Log.e("LIST", String.valueOf(list.size()));
                    adapter = new HomeworkAdapter(HomeWork.this,list);
                    recyclerView.setAdapter(adapter);

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
