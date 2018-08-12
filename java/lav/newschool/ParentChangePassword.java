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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 7/2/2018.
 */

public class ParentChangePassword extends AppCompatActivity {
    EditText currentpass, newpass,confirmpass;
    Button submit;
    String url = "http:// 139.59.18.46:8080/smsservices/services/secure/user/parentchangepassword";
    String userid,cpass,npass,conpass;
    JSONObject params = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherchangepassword);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");

        // Log.e("USERID",userid);

        currentpass = (EditText)findViewById(R.id.et_currentpassword);
        newpass = (EditText)findViewById(R.id.et_newpassword);
        confirmpass = (EditText)findViewById(R.id.et_confirmpassword);
        submit = (Button)findViewById(R.id.b_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpass = currentpass.getText().toString();
                npass = newpass.getText().toString();
                conpass = confirmpass.getText().toString();

                Log.e("CPASS",cpass);

                try {
                    params.put("id",userid);
                    params.put("oldPassword",cpass);
                    params.put("newPassword",npass);
                    params.put("confirmNewPassword",conpass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("RESPONSE",response.toString());
                            String status = response.getString("status");
                            String changePassword = response.getString("changePassword");
                            if (changePassword.equals("Success")){
                                Toast.makeText(getApplicationContext(),"Password changed successfully",Toast.LENGTH_SHORT).show();
                                currentpass.setText("");
                                newpass.setText("");
                                confirmpass.setText("");
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
