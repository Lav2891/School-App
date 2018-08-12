package lav.newschool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashwin on 5/22/2018.
 */

public class StudentTimeTableAdapter extends RecyclerView.Adapter<StudentTimeTableAdapter.ViewHolder> {
    Context context;
    ArrayList<String> timing = new ArrayList<>();

    public StudentTimeTableAdapter(Context context, ArrayList<String> timing) {
        this.context=context;
        this.timing=timing;
    }

    @Override
    public StudentTimeTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.studenttimetableadapter, parent, false);

        StudentTimeTableAdapter.ViewHolder myViewHolder = new StudentTimeTableAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(StudentTimeTableAdapter.ViewHolder holder, int position) {
        String time = timing.get(position);
        holder.timing_tv.setText(time);
        holder.timing_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Attendance.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timing.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timing_tv;
        public ViewHolder(View itemView) {
            super(itemView);
           timing_tv=(TextView)itemView.findViewById(R.id.tv_timing);
        }
    }
}
