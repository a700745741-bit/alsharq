package com.alsharq.global;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SectionAdapter extends BaseAdapter {
    Context context;
    ArrayList<TeamSection> sections;
    boolean isAdmin;

    public SectionAdapter(Context context, ArrayList<TeamSection> sections, boolean isAdmin) {
        this.context = context;
        this.sections = sections;
        this.isAdmin = isAdmin;
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public Object getItem(int position) {
        return sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
        }
        TextView title = convertView.findViewById(R.id.sectionTitle);
        ImageView icon = convertView.findViewById(R.id.sectionImage);

        TeamSection sec = sections.get(position);
        title.setText(sec.title);
        icon.setImageResource(R.drawable.ic_launcher_foreground);

        return convertView;
    }
}