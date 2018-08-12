package lav.newschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwin on 5/24/2018.
 */

public class TeacherTimeTableAdapter extends RecyclerView.Adapter<TeacherTimeTableAdapter.ViewHolder>{

    Context context;
    ArrayList<JSONObject> objlist = new ArrayList<>();

    public TeacherTimeTableAdapter(Context context, ArrayList<JSONObject> objlist) {
        this.context=context;
        this.objlist=objlist;
    }


    @Override
    public TeacherTimeTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.teachertimetableadapter, parent, false);

        TeacherTimeTableAdapter.ViewHolder myViewHolder = new TeacherTimeTableAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(TeacherTimeTableAdapter.ViewHolder holder, int position) {
        JSONObject obj = objlist.get(position);
        try {
            final String time = obj.getString("slotTimings");
            String whichclass = obj.getString("className");
            String section = obj.getString("section");
            String subject = obj.getString("subject");

            holder.time_tv.setText(time);
            holder.time_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,time,Toast.LENGTH_SHORT).show();
                }
            });
            holder.class_tv.setText(whichclass);
            holder.section_tv.setText(section);
            holder.subject_tv.setText(subject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return objlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_tv,class_tv,section_tv,subject_tv;

        public ViewHolder(View itemView) {
            super(itemView);

            time_tv=(TextView)itemView.findViewById(R.id.tv_time);
            class_tv=(TextView)itemView.findViewById(R.id.tv_class);
            section_tv=(TextView)itemView.findViewById(R.id.tv_section);
            subject_tv=(TextView)itemView.findViewById(R.id.tv_subject);
        }
    }
}
