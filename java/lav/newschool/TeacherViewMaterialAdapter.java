package lav.newschool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by Ashwin on 5/25/2018.
 */

public class TeacherViewMaterialAdapter extends RecyclerView.Adapter<TeacherViewMaterialAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> listobj;
    String deleteURL = "http://139.59.18.46:8080/smsservices/services/secure/faculty/deletematerialinfobyid?id=";
  //  String updateurl = "http://139.59.18.46:8080/smsservices/services/secure/faculty/getmaterialinfobyid?id=";

    public TeacherViewMaterialAdapter(Context context, ArrayList<JSONObject> listobj) {
        this.context=context;
       this.listobj=listobj;

    }

    @Override
    public TeacherViewMaterialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.teacherviewmaterialadapter, parent, false);

        TeacherViewMaterialAdapter.ViewHolder myViewHolder = new TeacherViewMaterialAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TeacherViewMaterialAdapter.ViewHolder holder, final int position) {
        final JSONObject obj = listobj.get(position);
        try {
           final String classname = obj.getString("className");
           final String subject = obj.getString("subject");
           String chaptertitle = obj.getString("chapterTitle");
           String chapterdescription = obj.getString("chapterDescription");
            final String id = obj.getString("id");
            final String fid = obj.getString("facultyId");

            holder.classname_tv.setText(classname);
            holder.subject_tv.setText(subject);
            holder.chaptertitle_tv.setText(chaptertitle);
            holder.chapterdescription_tv.setText(chapterdescription);
            holder.edit_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Modify..?");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                           // Toast.makeText(context,classname,Toast.LENGTH_SHORT).show();
                           String updateddeleteurl = deleteURL+id;
                            JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, updateddeleteurl, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("status");

                                        String message = response.getString("Message");
                                        Log.e("DELETE MESSAGE",message);

                                        listobj.remove(position);
                                        notifyDataSetChanged();

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

                    alertDialogBuilder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          //  Toast.makeText(context,subject,Toast.LENGTH_SHORT).show();
                         //   String updateupdateurl = updateurl+id;

                            Intent intent = new Intent(context,UpdateMaterial.class);
                            intent.putExtra("id",id);
                            intent.putExtra("fid",fid);
                            Log.e("FD",fid);
                            intent.putExtra("JOBJ",obj.toString());
                            context.startActivity(intent);

                            listobj.notify();

                        }
                    });
                    alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // Toast.makeText(context,"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                   // return false;
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return listobj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView classname_tv,subject_tv,chaptertitle_tv,chapterdescription_tv;
        CardView cc;
        Button edit_b;

        public ViewHolder(View itemView) {
            super(itemView);
            classname_tv=(TextView)itemView.findViewById(R.id.tv_classname);
            subject_tv=(TextView)itemView.findViewById(R.id.tv_subject);
            chaptertitle_tv=(TextView)itemView.findViewById(R.id.tv_chaptertitle);
            chapterdescription_tv=(TextView)itemView.findViewById(R.id.tv_chapterdescription);
            cc = (CardView)itemView.findViewById(R.id.c);
            edit_b=(Button)itemView.findViewById(R.id.b_edit);
        }
    }
}
