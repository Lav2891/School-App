package lav.newschool;

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
 * Created by Ashwin on 5/29/2018.
 */

public class Transport extends AppCompatActivity {
    RecyclerView transport_rv;
    LinearLayoutManager lm;
    TransportAdapter adapter;
    ArrayList<JSONObject> list = new ArrayList<>();
    String url = "http://139.59.18.46:8080/smsservices/services/secure/root/getalltransportdetails";
    String vehicleurl="http://139.59.18.46:8080/smsservices/services/secure/vehicle/getallvehicledetails";

    ArrayList<JSONObject> ao = new ArrayList<>();
    ArrayList<JSONObject> vo = new ArrayList<>();
    ArrayList<ArrayList<JSONObject>> o = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport);

        transport_rv=(RecyclerView)findViewById(R.id.rv_transport);
        lm=new LinearLayoutManager(this);
        transport_rv.setLayoutManager(lm);


      /*  JsonObjectRequest vobj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("TRANSPORT STATUS",status);

                    JSONArray transportdetails = response.getJSONArray("allTransportDetails");
                    for(int i=0;i<transportdetails.length();i++){
                        JSONObject o =transportdetails.getJSONObject(i);
                        vo.add(o);
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

        vobj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(vobj, "tag");
*/


        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    Log.e("TRANSPORT STATUS",status);

                    JSONArray transportdetails = response.getJSONArray("allTransportDetails");
                    for(int i=0;i<transportdetails.length();i++){
                        JSONObject o =transportdetails.getJSONObject(i);
                        ao.add(o);
                    }
                    adapter= new TransportAdapter(Transport.this,ao);
                    transport_rv.setAdapter(adapter);

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
