package lav.newschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ashwin on 5/30/2018.
 */

public class CalanderOneAdapter extends RecyclerView.Adapter<CalanderOneAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> list = new ArrayList<>();


    public CalanderOneAdapter(Context context, ArrayList<JSONObject> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public CalanderOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.calanderoneadapter, parent, false);

        CalanderOneAdapter.ViewHolder myViewHolder = new CalanderOneAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CalanderOneAdapter.ViewHolder holder, int position) {
        JSONObject o = list.get(position);
        try {
            String activitytype = o.getString("activityType");
            String activitytitle = o.getString("activityTitle");
            String activitydescription = o.getString("activityDescription");
            String startdate = o.getString("startDate");
            String enddate = o.getString("endDate");

            long val = Long.parseLong(startdate);
            Date sdate=new Date(val);
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            String sdateText = df2.format(sdate);

            long vall = Long.parseLong(enddate);
            Log.e("VALL", String.valueOf(vall));
            Date edate=new Date(vall);
            SimpleDateFormat edf2 = new SimpleDateFormat("dd/MM/yyyy");
            String edateText = edf2.format(edate);

            holder.startdate.setText(sdateText);
            holder.enddate.setText(edateText);
            holder.type.setText(activitytype);
            holder.title.setText(activitytitle);
            holder.description.setText(activitydescription);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description,title,type,enddate,startdate;
        public ViewHolder(View itemView) {
            super(itemView);
            description=(TextView)itemView.findViewById(R.id.tv_activitydescription);
            title=(TextView)itemView.findViewById(R.id.tv_activitytitle);
            type=(TextView)itemView.findViewById(R.id.tv_activitytype);
            enddate=(TextView)itemView.findViewById(R.id.tv_enddate);
            startdate=(TextView)itemView.findViewById(R.id.tv_startdate);

        }
    }
}

