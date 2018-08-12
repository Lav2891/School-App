package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Ashwin on 7/10/2018.
 */

public class LoginNew extends AppCompatActivity {
    Button login_b;
    EditText username_et, password_et;

    String url = "http://139.59.18.46:8080/smsservices/services/secure/faculty/getallfacultydetails";
    String studenturl = "http://139.59.18.46:8080/smsservices/services/secure/student/getallstudentdetails";
    String parenturl = "http://139.59.18.46:8080/smsservices/services/secure/parent/getallparentdetails";

    String loginurl = "http://139.59.18.46:8080/smsservices/services/secure/user/login";
    JSONObject params = new JSONObject();
    String who;
    String password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();
        who = intent.getStringExtra("who");
        Log.e("WHO", who);

        username_et = (EditText) findViewById(R.id.et_username);
        password_et = (EditText) findViewById(R.id.et_password);

        login_b = (Button) findViewById(R.id.b_login);
        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = password_et.getText().toString();
                username = username_et.getText().toString();

                if (!password.isEmpty() || !username.isEmpty()) {

                    try {
                        params.put("password", password);
                        params.put("loginId", username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, loginurl, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.e("CN", response.toString());
                                String status = response.getString("status");
                                Log.e("LOGIN STATUS", status);
                                if (status.equals("Ok")) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                    if (who.equals("teacher")) {

                                        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("RES", response.toString());
                                                try {
                                                    String status = response.getString("status");
                                                    Log.e("STATUS", status);
                                                    JSONArray array = response.getJSONArray("allFacultyDetails");
                                                    for (int i = 0; i < array.length(); i++) {
                                                        JSONObject oa = array.getJSONObject(i);
                                                        String mobileno = oa.getString("mobileNumber");
                                                        //Log.e("Mobileno",mobileno);
                                                        if (mobileno.equals(username)) {
                                                            String firstname = oa.getString("firstName");
                                                            String lastname = oa.getString("lastName");
                                                            String facultyid = oa.getString("facultyId");
                                                            String id = oa.getString("id");
                                                            String userid = oa.getString("userId");
                                                            // Log.e("USERID 0",userid);
                                                            Intent intent = new Intent(LoginNew.this, TeacherDashboard.class);
                                                            intent.putExtra("fname", firstname);
                                                            intent.putExtra("lname", lastname);
                                                            intent.putExtra("facultyid", facultyid);
                                                            intent.putExtra("id", id);
                                                            intent.putExtra("userid",userid);
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
                                    } else if (who.equals("student")){
                                        Log.e("STUDENTURL",studenturl);
                                        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, studenturl, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("RES", response.toString());
                                                try {
                                                    String status = response.getString("status");
                                                    Log.e("STATUS", status);
                                                    JSONArray array = response.getJSONArray("allStudentDetails");
                                                    for (int i = 0; i < array.length(); i++) {
                                                        JSONObject oa = array.getJSONObject(i);
                                                        String mobileno = oa.getString("mobileNumber");
                                                        Log.e("Mobileno",mobileno);
                                                        if (mobileno.equals(username)) {
                                                            String firstname = oa.getString("firstName");
                                                            String lastname = oa.getString("lastName");
                                                            String studentid = oa.getString("studentId");
                                                            String classnumber = oa.getString("className");
                                                            String rollno = oa.getString("rollNumber");
                                                            String id = oa.getString("id");
                                                            String userid = oa.getString("userId");
                                                            Intent intent = new Intent(LoginNew.this, Dashboard.class);
                                                            intent.putExtra("fname", firstname);
                                                            intent.putExtra("lname", lastname);
                                                            intent.putExtra("studentid", studentid);
                                                            intent.putExtra("id", id);
                                                            intent.putExtra("classname",classnumber);
                                                            intent.putExtra("rollno",rollno);
                                                            intent.putExtra("userid",userid);
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
                                    } else if (who.equals("parent")) {
                                        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, parenturl, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                // Log.e("RES", response.toString());
                                                try {
                                                    String status = response.getString("status");
                                                    Log.e("PARENT STATUS", status);
                                                    JSONArray a = response.getJSONArray("allParentDetails");
                                                    for(int i=0;i<a.length();i++){
                                                        final JSONObject o = a.getJSONObject(i);
                                                        String mobno = o.getString("mobileNumber");
                                                        // Log.e("UN",mobno);
                                                        if (mobno.equals(username)){
                                                            final String puserid = o.getString("userId");
                                                            // Log.e("UName",mobno);
                                                            JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, studenturl, null, new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.e("RES", response.toString());
                                                                    try {
                                                                        String status = response.getString("status");
                                                                        Log.e("STATUS", status);
                                                                        JSONArray array = response.getJSONArray("allStudentDetails");
                                                                        for (int i = 0; i < array.length(); i++) {
                                                                            JSONObject oa = array.getJSONObject(i);
                                                                            String mothermobno = oa.getString("motherMobileNumber");
                                                                            String fathermobno = oa.getString("fatherMobileNumber");
                                                                            if (username.equals(mothermobno)||username.equals(fathermobno)){
                                                                                //  Log.e("MN",mothermobno);
                                                                                // Log.e("FN",fathermobno);
                                                                                // Log.e("UN",username);
                                                                                String firstname = oa.getString("firstName");
                                                                                String lastname = oa.getString("lastName");
                                                                                String studentid = oa.getString("studentId");
                                                                                String id = oa.getString("id");
                                                                                Intent intent = new Intent(LoginNew.this, ParentDashboard.class);
                                                                                intent.putExtra("fname", firstname);
                                                                                intent.putExtra("lname", lastname);
                                                                                intent.putExtra("studentid", studentid);
                                                                                intent.putExtra("id", id);
                                                                                intent.putExtra("puserid",puserid);
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

                                }else {
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
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
        });
    }

}

