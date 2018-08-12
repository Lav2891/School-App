package lav.newschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwin on 5/15/2018.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Context context;
    ArrayList<JSONObject> list = new ArrayList<>();
    String name,classname,startdate,enddate,description, status;
    String studentid;

    public EventsAdapter(Context context, ArrayList<JSONObject> list, String studentid) {
        this.context=context;
        this.list=list;
        this.studentid=studentid;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.eventsadapter, parent, false);

        EventsAdapter.ViewHolder myViewHolder = new EventsAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        JSONObject obj = list.get(position);
        try {
           // classname = obj.getString("eventForWhom");
               name = obj.getString("eventName");
               startdate = obj.getString("eventStartDate");
               enddate = obj.getString("eventEndDate");
               description = obj.getString("eventDetails");
               status = obj.getString("eventStatus");

               holder.notificationname_tv.setText(name);
              // holder.toclass_tv.setText(classname);
               holder.startdat_tv.setText(startdate);
               holder.enddate_tv.setText(enddate);
               holder.description_tv.setText(description);
               holder.status_tv.setText(status);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView toclass_tv,startdat_tv,enddate_tv,notificationname_tv,description_tv,status_tv;
        public ViewHolder(View itemView) {
            super(itemView);
          //  toclass_tv=(TextView)itemView.findViewById(R.id.tv_toclass);
            startdat_tv=(TextView)itemView.findViewById(R.id.tv_startdate);
            enddate_tv=(TextView)itemView.findViewById(R.id.tv_enddate);
            notificationname_tv=(TextView)itemView.findViewById(R.id.tv_notificationname);
            description_tv=(TextView)itemView.findViewById(R.id.tv_notificatidescription);
            status_tv=(TextView)itemView.findViewById(R.id.tv_status);
        }
    }
}
