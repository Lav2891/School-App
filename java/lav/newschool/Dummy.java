package lav.newschool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 5/23/2018.
 */

public class Dummy extends AppCompatActivity {

    TextView idq, studentidq, useridq, firstnameq, lastnameq, mobilenoq, genderq,fathernameq, parentmobileq;
    String url = "http://139.59.18.46:8080/smsservices/services/secure/student/getallstudentdetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);

        idq=(TextView)findViewById(R.id.id);
        studentidq=(TextView)findViewById(R.id.studentid);
        useridq=(TextView)findViewById(R.id.userid);
        firstnameq=(TextView)findViewById(R.id.fathername);
        lastnameq=(TextView)findViewById(R.id.lastname);
        mobilenoq=(TextView)findViewById(R.id.mobileno);
        genderq=(TextView)findViewById(R.id.gender);
        fathernameq=(TextView)findViewById(R.id.fathername);
        parentmobileq=(TextView)findViewById(R.id.parentmobile);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RES", response.toString());
                try {
                    String status = response.getString("status");
                    Log.e("STATUS",status);
                    JSONArray array = response.getJSONArray("allStudentDetails");
                    for(int i=0;i<array.length();i++){
                        JSONObject oa = array.getJSONObject(i);
                        String id = oa.getString("id");
                        String studentid = oa.getString("studentId");
                        String userid = oa.getString("userId");

                        idq.setText(id);
                        studentidq.setText(studentid);
                        useridq.setText(userid);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));

            }
        }){
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
