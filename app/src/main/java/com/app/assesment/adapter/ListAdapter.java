package com.app.assesment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.assesment.model.Event;
import com.app.assesment.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<Event> timings;
    private Context context;
    private static int currentPosition = 0;

    public ListAdapter(List<Event> timing, Context context) {
        this.timings = timing;
        this.context = context;

        Log.d("TAG", "ListAdapter: Called ");
    }

    public void setData(List<Event> t) {
        this.timings = t;
        notifyDataSetChanged();
        Log.d("TAG", "setData: timings " + timings);
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {

        Event event = timings.get(position);
        holder.textViewName.setText(event.getDay());
        int l = timings.get(position).getPrograms().size();
        Log.d("TAG", "onBindViewHolder: "+l);
        for (int p = 0; p < l; p++) {
            //    Log.d("TAG", "onBindViewHolder2: hero "+event.getDay() + event.getPrograms().get(p).getEvent()+ event.getPrograms().get(p).getTime()+"\n\n\n"  );
            String Eventsname = event.getPrograms().get(p).getEvent();
            holder.textViewRealName.setText(Eventsname);
            String EventTime = event.getPrograms().get(p).getTime();
            holder.Day.setText(EventTime);

        }


        holder.linearLayout.setVisibility(View.GONE);
        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.layout_anim);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout.startAnimation(slideDown);
        }
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textViewName, Day, textViewRealName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRealName = itemView.findViewById(R.id.textViewRealName);
            Day = itemView.findViewById(R.id.Day);

        }
    }
}
