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
 * Created by Ashwin on 7/10/2018.
 */

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    ArrayList<JSONObject> list;
    Context context;

   public HomeworkAdapter(Context context,ArrayList<JSONObject> list){
       this.context =context;
       this.list = list;
   }

    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.homeworkadapter, parent, false);

        HomeworkAdapter.ViewHolder myViewHolder = new HomeworkAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeworkAdapter.ViewHolder holder, int position) {
        final JSONObject obj = list.get(position);
        try {
            String title = obj.getString("homeWorkTitle");
            String description = obj.getString("howeWorkDescription");
            String assigneddate = obj.getString("assignedDate");

            long val = Long.parseLong(assigneddate);
            Date date=new Date(val);
            SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            String dateText = df2.format(date);


            holder.date_tv.setText(dateText);
            holder.hwtitle_tv.setText(title);
            holder.hwdescription_tv.setText(description);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        }

    @Override
    public int getItemCount() {
        Log.e("LIST SIZE", String.valueOf(list.size()));
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_tv, hwtitle_tv, hwdescription_tv;

        public ViewHolder(View itemView) {
            super(itemView);

            date_tv=(TextView)itemView.findViewById(R.id.tv_date);
            hwtitle_tv=(TextView)itemView.findViewById(R.id.tv_hwtitle);
            hwdescription_tv=(TextView)itemView.findViewById(R.id.tv_hwdescription);
        }
    }
}
