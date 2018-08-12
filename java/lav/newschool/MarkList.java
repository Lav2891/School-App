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
 * Created by Ashwin on 5/6/2018.
 */

public class MarkList extends AppCompatActivity implements addtotal {

    String id, updatedurl;
    String URL = "http://139.59.18.46:8080/smsservices/services/secure/student/getmarksinfobystudentid?studentId=";
    String subjecturl = "http://139.59.18.46:8080/smsservices/services/secure/subject/getallsubjectdetails";
    ArrayList<JSONObject> objlist = new ArrayList<>();
    ArrayList<String> subid = new ArrayList<>();
    ArrayList<String> subname = new ArrayList<>();
    ArrayList<String> trytry = new ArrayList<>();
    ArrayList<ArrayList<String>> newtry = new ArrayList<>();

    RecyclerView marks_rv;
    LinearLayoutManager lm;
    MarkListAdapter markadapter;
    JSONObject marknid = new JSONObject();

    TextView obtainedtotal_tv;
    TextView grandtotal_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marklist);

        obtainedtotal_tv=(TextView)findViewById(R.id.tv_obtainedtotal);
        grandtotal_tv=(TextView)findViewById(R.id.tv_grandtotal);

        Intent intent=getIntent();
        String id = intent.getStringExtra("id");

        marks_rv=(RecyclerView)findViewById(R.id.rv_marks);
        lm=new LinearLayoutManager(this);
        marks_rv.setLayoutManager(lm);

        JsonObjectRequest subjectobj = new JsonObjectRequest(Request.Method.GET, subjecturl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("SUBJECT STATUS",status);
                    JSONArray subjects = response.getJSONArray("allSubjects");
                    for(int i=0;i<subjects.length();i++){
                        JSONObject sub = subjects.getJSONObject(i);
                        String sid = sub.getString("id");
                        subid.add(sid);
                        String sname = sub.getString("subjectName");
                        subname.add(sname);
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

        for(int i=0;i<=subid.size()&&i<=subname.size();i++){
            //Objects.equals(subid.get(i), subname.get(i));

        }

        updatedurl = URL+id;

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, updatedurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("MARK LIST STATUS",status);
                    JSONArray studentmarks = response.getJSONArray("studentMarks");
                    for(int i=0;i<studentmarks.length();i++){
                        JSONObject submarks = studentmarks.getJSONObject(i);
                       // Log.e("JSON", String.valueOf(submarks));
                       // String subjectid = submarks.getString("subjectId");
                        //String mark = submarks.getString("mark");
                      // int x = subid.indexOf(subjectid);
                        //String nameofsub = subname.get(x);
                        //Log.e("SUBJECTNAME",nameofsub);
                       // Log.e("MARK",mark);
                       // trytry.add(nameofsub);
                       // trytry.add(mark);
                       // newtry.add(trytry);
                       // Log.e("NEWTRY", String.valueOf(trytry));
                       // marknid.put("subjectName",nameofsub);
                       // marknid.put("mark",mark);
                       // Log.e("MARK N ID", String.valueOf(marknid));
                        objlist.add(submarks);
                        //Log.e("OBJLIST", String.valueOf(objlist));
                    }


                    markadapter=new MarkListAdapter(MarkList.this,objlist,subid,subname,MarkList.this );
                    marks_rv.setAdapter(markadapter);

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
    public void method(int y, int z) {
       // Log.e("Y HERE", String.valueOf(y));
        obtainedtotal_tv.setText(""+y);
        grandtotal_tv.setText(""+z);
    }
}
