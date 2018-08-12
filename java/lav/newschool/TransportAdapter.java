package lav.newschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Ashwin on 5/29/2018.
 */

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> olist;
    String urlid = "http://139.59.18.46:8080/smsservices/services/secure/vehicle/getvehicleinfobyid?id=";

    public TransportAdapter(Context context, ArrayList<JSONObject> olist) {
        this.context=context;
        this.olist=olist;
    }


    @Override
    public TransportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.transportadapter, parent, false);

        TransportAdapter.ViewHolder myViewHolder = new TransportAdapter.ViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final TransportAdapter.ViewHolder holder, int position) {

       JSONObject o = olist.get(position);
        try {
            String vid = o.getString("vehicleId");
            String url = urlid+vid;

            JsonObjectRequest vobj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        Log.e("VID STATUS",status);

                        JSONObject o = response.getJSONObject("vehicleVO");
                        String vt = o.getString("vehicleType");
                        String vn = o.getString("vehicleNumber");
                        holder.vehicletype_tv.setText(vt);
                        holder.vehicleno_tv.setText(vn);
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

            String startpt = o.getString("startPoint");
            final String noofstudents = o.getString("noOfstudents");
            String endpt = o.getString("endPoint");


           // holder.vehicletype_tv.setText(vid);
            holder.noofstudents_tv.setText(noofstudents);
            holder.noofstudents_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,noofstudents,Toast.LENGTH_SHORT).show();
                }
            });
            holder.startpoint_tv.setText(startpt);
            holder.endpoint_tv.setText(endpt);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return olist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startpoint_tv, endpoint_tv, vehicletype_tv,noofstudents_tv, vehicleno_tv;

        public ViewHolder(View itemView) {
            super(itemView);
           startpoint_tv=(TextView)itemView.findViewById(R.id.tv_startpoint);
            endpoint_tv=(TextView)itemView.findViewById(R.id.tv_endpoint);
            vehicletype_tv=(TextView)itemView.findViewById(R.id.tv_vehicletype);
            noofstudents_tv=(TextView)itemView.findViewById(R.id.tv_numberofstudents);
            vehicleno_tv=(TextView)itemView.findViewById(R.id.tv_vehiclenumber);
        }
    }
}

