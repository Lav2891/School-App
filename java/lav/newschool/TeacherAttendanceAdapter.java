package lav.newschool;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 6/1/2018.
 */

public class TeacherAttendanceAdapter extends RecyclerView.Adapter<TeacherAttendanceAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> studentlist;
    long date;
    String x, facultyid;
    submit s;
    int count = 0;
    int p;
    ArrayList<Integer> plist = new ArrayList<>();

    String attendanceurl = "http://139.59.18.46:8080/smsservices/services/secure/faculty/addattendance";
    JSONObject params = new JSONObject();

    public TeacherAttendanceAdapter(Context context, ArrayList<JSONObject> studentlist, long date, String facultyid, submit s) {
        this.context=context;
        this.studentlist=studentlist;
        this.date=date;
        this.facultyid=facultyid;
        this.s=s;
    }

    @Override
    public TeacherAttendanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.teacherattendanceadapter, parent, false);

        TeacherAttendanceAdapter.ViewHolder myViewHolder = new TeacherAttendanceAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TeacherAttendanceAdapter.ViewHolder holder, final int position) {
        JSONObject obj = studentlist.get(position);

        try {
            final String studentid = obj.getString("id");
            String dispstudentid = obj.getString("studentId");
            String firstname = obj.getString("firstName");
            String lastname = obj.getString("lastName");
            String rollno = obj.getString("rollNumber");
            final String fullname = firstname+" "+lastname;

            holder.fullname_tv.setText(fullname);
            holder.studentid_tv.setText(dispstudentid);
            holder.rollno_tv.setText(rollno);

           holder.g_r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                       if (checkedId == R.id.r_p) {
                           x = "Present";

                         boolean presentornot =  plist.contains(position);
                           count = count + 1;
                           if (presentornot==false){
                             //  Log.e("C", String.valueOf(count));
                               s.method(fullname, x, studentid, facultyid, date);

                               try {
                                   params.put("studentId",studentid);
                                   params.put("studentFullName", fullname);
                                   params.put("attendance",x);
                                   params.put("facultyId",facultyid);
                                   params.put("date",date);
                                   Log.e("PARAM", params.toString());
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                               JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, attendanceurl, params, new Response.Listener<JSONObject>() {
                                   @Override
                                   public void onResponse(JSONObject response) {
                                       try {
                                           Log.e("RESPONSE ATTENDANCE", response.toString());
                                           String status = response.getString("status");
                                           Log.e("ATTENDANCE STATUS", status);
                                           if (status.equals("Ok")){
                                               p = position;
                                               Log.e("POSITION", String.valueOf(position));
                                               plist.add(p);
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

                           }else {
                               holder.a_r.setChecked(false);
                               holder.p_r.setChecked(false);
                               Toast.makeText(context,"Registered already. To make changes go to update",Toast.LENGTH_SHORT).show();
                           }
                       }else if (checkedId == R.id.r_a){
                           x = "Absent";
                           count = count + 1;
                           boolean presentornot =  plist.contains(position);
                           if (presentornot==false){
                              // Log.e("C", String.valueOf(count));
                               s.method(fullname, x, studentid, facultyid, date);
                               try {
                                   params.put("studentId",studentid);
                                   params.put("studentFullName", fullname);
                                   params.put("attendance",x);
                                   params.put("facultyId",facultyid);
                                   params.put("date",date);
                                   Log.e("PARAM", params.toString());
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                               JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, attendanceurl, params, new Response.Listener<JSONObject>() {
                                   @Override
                                   public void onResponse(JSONObject response) {
                                       try {
                                           Log.e("RESPONSE ATTENDANCE", response.toString());
                                           String status = response.getString("status");
                                           Log.e("ATTENDANCE STATUS", status);
                                           if (status.equals("Ok")){
                                               p = position;
                                               Log.e("POSITION", String.valueOf(position));
                                              plist.add(p);
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
                           else {
                               holder.a_r.setChecked(false);
                               holder.p_r.setChecked(false);
                               Toast.makeText(context,"Registered already. To make changes go to update",Toast.LENGTH_SHORT).show();
                           }
                       }
               }
           });

           /* if (holder.g_r.getCheckedRadioButtonId()== -1){
                Log.e("NO RB","no rb");
                if(holder.p_r.isChecked()){
                    x = "Present";
                    Log.e("T",x);
                    s.method(fullname, x, studentid, facultyid, date);
                }else if (holder.a_r.isChecked()) {
                    x = "Absent";
                    Log.e("T",x);
                    s.method(fullname, x, studentid, facultyid, date);
                }
            }else {
                Log.e("YES RB","yes rb");
            }

          /*  if(holder.p_r.isChecked()){
                    x = "Present";
                    Log.e("T",x);
                    s.method(fullname, x, studentid, facultyid, date);
                holder.a_r.setChecked(false);

            } else if (holder.a_r.isChecked()){
                    x = "Absent";
                    s.method(fullname, x, studentid, facultyid, date);
                holder.p_r.setEnabled(false);
            }*/

         /*   holder.present_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      //  Log.e("X", String.valueOf(x));
                        holder.present_b.setBackground(context.getDrawable(R.drawable.attendancebutton));
                        holder.present_b.setTextColor(context.getColor(R.color.White));
                       x = "Present";
                    holder.absent_b.setBackground(context.getDrawable(R.drawable.studentprofilepic));
                    holder.absent_b.setTextColor(context.getColor(R.color.SchoolDarkBlue));

                    String[] idlist = new String[studentlist.size()];
                    if ( Arrays.asList(idlist).contains(studentid)&&(x.equals("Absent"))) {

                    }else {
                        Log.e("SI",studentid);
                        Log.e("X",x);
                        s.method(fullname, x, studentid, facultyid, date);
                        idlist[position] = studentid;
                    }
                }
            });

            holder.absent_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        holder.absent_b.setBackground(context.getDrawable(R.drawable.attendancebutton));
                        holder.absent_b.setTextColor(context.getColor(R.color.White));
                    x = "Absent";
                    holder.present_b.setBackground(context.getDrawable(R.drawable.studentprofilepic));
                    holder.present_b.setTextColor(context.getColor(R.color.SchoolDarkBlue));

                    String[] idlist = new String[studentlist.size()];
                    if ( Arrays.asList(idlist).contains(studentid)&&(x.equals("Present"))) {

                    }else {
                        Log.e("SI",studentid);
                        Log.e("X",x);
                        s.method(fullname, x, studentid, facultyid, date);
                        idlist[position] = studentid;
                    }

                }
            });*/


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return studentlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname_tv, studentid_tv, rollno_tv;
        RadioButton p_r, a_r;
        RadioGroup g_r;
       // Button present_b, absent_b;
        public ViewHolder(View itemView) {
            super(itemView);
            fullname_tv=(TextView)itemView.findViewById(R.id.tv_fullname);
            studentid_tv=(TextView)itemView.findViewById(R.id.tv_studentid);
            rollno_tv=(TextView)itemView.findViewById(R.id.tv_rollno);
            p_r = (RadioButton)itemView.findViewById(R.id.r_p);
            a_r = (RadioButton)itemView.findViewById(R.id.r_a);
            g_r = (RadioGroup)itemView.findViewById(R.id.r_g);
           // present_b=(Button)itemView.findViewById(R.id.b_present);
           // absent_b=(Button)itemView.findViewById(R.id.b_absent);
        }
    }
}

interface submit{
    void  method(String fullname, String attendance, String studentid, String facultyid, long date);
}
