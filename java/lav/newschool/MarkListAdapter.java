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

public class MarkListAdapter extends RecyclerView.Adapter<MarkListAdapter.ViewHolder>{
    Context context;
    ArrayList<JSONObject> objlist;
    ArrayList<String> subid;
    ArrayList<String> subname;
    String mark, subjectid, nameofsub;
    int m=0;
    int y=0;
    int z=0;
    addtotal t;

    public MarkListAdapter(Context context, ArrayList<JSONObject> objlist, ArrayList<String> subid, ArrayList<String> subname,addtotal t) {
        this.context = context;
        this.objlist = objlist;
        this.subid=subid;
        this.subname=subname;
        this.t=t;
    }

    @Override
    public MarkListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.marklistadapter, parent, false);

        MarkListAdapter.ViewHolder myViewHolder = new MarkListAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MarkListAdapter.ViewHolder holder, int position) {
        JSONObject obj = objlist.get(position);
        try {
             mark = obj.getString("mark");
             subjectid = obj.getString("subjectId");
            int x = subid.indexOf(subjectid);
            nameofsub = subname.get(x);

             m = Integer.parseInt(mark);
            y=y+m;
            z=z+100;
            Log.e("Z", String.valueOf(z));

            t.method(y,z);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mark_tv.setText(mark);
        holder.subject_tv.setText(nameofsub);
    }

    @Override
    public int getItemCount() {
        return objlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject_tv, mark_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            subject_tv=(TextView)itemView.findViewById(R.id.tv_subject);
            mark_tv=(TextView)itemView.findViewById(R.id.tv_mark);
        }
    }

}

interface addtotal{
    void method(int y, int z);
}




