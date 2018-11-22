package com.kenyrim.fitnesskit;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Raspisanie> posts;

    public PostsAdapter(List<Raspisanie> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Raspisanie post = posts.get(position);

        holder.name.setText(post.getName());
        if (post.getDescription().isEmpty()) {
            holder.desc.setText("Нет описания");
        }
        else {
            holder.desc.setText(post.getDescription());
        }
        holder.teacher.setText(post.getTeacher());
        holder.st_time.setText(post.getStartTime());
        holder.end_time.setText(post.getEndTime());
        holder.place.setText(post.getPlace());
        holder.week.setText(String.valueOf(post.getWeekDay()));

        Log.e("ADAPTER_name", post.getName());

    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, teacher, st_time, end_time, place, week;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            teacher = itemView.findViewById(R.id.ticher);
            st_time = itemView.findViewById(R.id.st_time);
            end_time = itemView.findViewById(R.id.end_time);
            place = itemView.findViewById(R.id.place);
            week = itemView.findViewById(R.id.week);
        }
    }
}