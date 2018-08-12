package lav.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
 * Created by Ashwin on 5/30/2018.
 */

public class UpdateMaterial extends AppCompatActivity {
    Spinner materialsubject_s;
    EditText chaptertitle_et, chapterdescription_et;
    Button submit_b;
    String[] subjects = new String[] {"English","Maths","Physics","Chemistry","Biology","History"};
    String title, description, id, classname, subject, fid;

    String settitle,setdescription,setsubject;

    String URL = "http://139.59.18.46:8080/smsservices/services/secure/faculty/updatematerialinfo";
    JSONObject params = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatematerial);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        fid = intent.getStringExtra("fid");
        Log.e("ID",id);
        try {
            JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("JOBJ"));
            settitle = jsonObj.getString("chapterTitle");
            setdescription = jsonObj.getString("chapterDescription");
            setsubject = jsonObj.getString("subject");

            classname = jsonObj.getString("className");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        materialsubject_s=(Spinner)findViewById(R.id.s_materialsubject);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjects);
        materialsubject_s.setAdapter(adapter);

        materialsubject_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject=materialsubject_s.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject=setsubject;
            }
        });

        chaptertitle_et=(EditText)findViewById(R.id.et_chaptertitle);
        chaptertitle_et.setText(settitle);
        chapterdescription_et=(EditText)findViewById(R.id.et_chapterdescription);
        chapterdescription_et.setText(setdescription);
        submit_b=(Button)findViewById(R.id.b_submit);
        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=chaptertitle_et.getText().toString();
                description=chapterdescription_et.getText().toString();

                parsing(title,description,subject,id,classname);
                chapterdescription_et.setText("");
                chaptertitle_et.setText("");
            }
        });
    }

    public void parsing(String title, String description, String subject, String id, String classname){
        try {
            params.put("facultyId",fid);
            Log.e("SETFID",fid);
            params.put("className",classname);
            Log.e("SETCN",classname);
            params.put("subject",subject);
            Log.e("SETSUB",subject);
            params.put("chapterTitle",title);
            Log.e("SETTITLE",title);
            params.put("chapterDescription",description);
            Log.e("SETDES",description);
            params.put("id",id);
            Log.e("SETID",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("CN",response.toString());
                    String status = response.getString("status");
                    Log.e("UPDATE STATUS",status);
                    String message = response.getString("Message");
                    Log.e("M",message);
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
