package com.alsharq.global;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private List<TeamSection> list;
    private Context context;
    private boolean isAdmin;

    public SectionAdapter(Context context, List<TeamSection> list, boolean isAdmin) {
        this.context = context;
        this.list = list;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamSection section = list.get(position);
        holder.title.setText(section.title);
        holder.content.setText(section.content);

        holder.itemView.setOnClickListener(view -> {
            if (isAdmin) {
                Intent i = new Intent(context, EditSectionActivity.class);
                i.putExtra("pos", position);
                i.putExtra("title", section.title);
                i.putExtra("content", section.content);
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, SectionViewActivity.class);
                i.putExtra("title", section.title);
                i.putExtra("content", section.content);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            content = itemView.findViewById(R.id.cardContent);
        }
    }
}