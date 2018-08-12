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
 * Created by Ashwin on 5/23/2018.
 */

public class TeacherMaterialSubject extends AppCompatActivity  {
    Spinner materialsubject_s;
    EditText chaptertitle_et, chapterdescription_et;
    Button submit_b;
    String[] subjects = new String[] {"English","Maths","Physics","Chemistry","Biology","History"};
    String title, description, id, classname, subject;
    String x;

    String URL = "http://139.59.18.46:8080/smsservices/services/secure/faculty/addmaterial";
    JSONObject params = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachermaterialsubject);


        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        classname=intent.getStringExtra("classname");
       // x = intent.getStringExtra("X");

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

            }
        });

        chaptertitle_et=(EditText)findViewById(R.id.et_chaptertitle);
        chapterdescription_et=(EditText)findViewById(R.id.et_chapterdescription);
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
            params.put("facultyId",id);
            params.put("className",classname);
            params.put("subject",subject);
            params.put("chapterTitle",title);
            params.put("chapterDescription",description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("CN",response.toString());
                    String status = response.getString("status");
                    Log.e("MATERIAL STATUS",status);
                   JSONObject materialobj = response.getJSONObject("materialVO");
                   String materialid = materialobj.getString("id");


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
