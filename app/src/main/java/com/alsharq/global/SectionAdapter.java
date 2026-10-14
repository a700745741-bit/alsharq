package com.alsharq.global;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private List<Section> list;
    public SectionAdapter(List<Section> list) { this.list = list; }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(android.R.id.text1);
            desc = v.findViewById(android.R.id.text2);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder h, int p) {
        h.title.setText(list.get(p).title);
        h.desc.setText(list.get(p).desc);
    }
    @Override
    public int getItemCount() { return list.size(); }
}