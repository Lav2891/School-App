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
 * Created by Ashwin on 5/25/2018.
 */

public class TeacherViewMaterial extends AppCompatActivity {
    RecyclerView material_rv;
    LinearLayoutManager lm;
    TeacherViewMaterialAdapter adapter;
    ArrayList<JSONObject> listobj = new ArrayList<>();

    String id,updatedURL;

    String URL ="http://139.59.18.46:8080/smsservices/services/secure/faculty/getallmaterialsbyfacultyid?facultyId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherviewmaterial);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        updatedURL=URL+id;

        material_rv=(RecyclerView)findViewById(R.id.rv_materials);
        lm=new LinearLayoutManager(this);
        material_rv.setLayoutManager(lm);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, updatedURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("VIEW MATERIAL STATUS",status);
                    JSONArray materialdetails = response.getJSONArray("markDetails");

                    for(int i=0;i<materialdetails.length();i++){
                        JSONObject materialobj = materialdetails.getJSONObject(i);
                       // Log.e("VM", String.valueOf(materialobj));
                        listobj.add(materialobj);
                    }
                    adapter=new TeacherViewMaterialAdapter(TeacherViewMaterial.this,listobj);
                    material_rv.setAdapter(adapter);

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
