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

import java.util.ArrayList;

/**
 * Created by Ashwin on 5/29/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> list = new ArrayList<>();
    String name,classname,startdate,enddate,description, status;

    public NotificationAdapter(Context context, ArrayList<JSONObject> list) {
     this.context=context;
        this.list=list;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.notificationadapter, parent, false);

        NotificationAdapter.ViewHolder myViewHolder = new NotificationAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        Log.e(" N LIST",list.toString());
        JSONObject obj = list.get(position);
        try {
             name = obj.getString("notificationName");
            // classname = obj.getString("notificationSendToWhom");
             startdate = obj.getString("notificationStartDate");
             enddate = obj.getString("notificationEndDate");
             description = obj.getString("notificationDescription");
            status = obj.getString("notificationStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.notificationname_tv.setText(name);
       // holder.toclass_tv.setText(classname);
        holder.startdat_tv.setText(startdate);
        holder.enddate_tv.setText(enddate);
        holder.description_tv.setText(description);
        holder.status_tv.setText(status);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView toclass_tv,startdat_tv,enddate_tv,notificationname_tv,description_tv,status_tv;

        public ViewHolder(View itemView) {
            super(itemView);

           // toclass_tv=(TextView)itemView.findViewById(R.id.tv_toclass);
            startdat_tv=(TextView)itemView.findViewById(R.id.tv_startdate);
            enddate_tv=(TextView)itemView.findViewById(R.id.tv_enddate);
            notificationname_tv=(TextView)itemView.findViewById(R.id.tv_notificationname);
            description_tv=(TextView)itemView.findViewById(R.id.tv_notificatidescription);
            status_tv=(TextView)itemView.findViewById(R.id.tv_status);
        }
    }
}
