package com.alsharq.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.adminRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refresh();

        findViewById(R.id.addButton).setOnClickListener(v -> showAddDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    void refresh() {
        recyclerView.setAdapter(new SectionAdapter(this, MainActivity.sections, true));
    }

    void showAddDialog() {
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_add, null);
        EditText tTitle = view.findViewById(R.id.dialogTitle);
        EditText tContent = view.findViewById(R.id.dialogContent);

        new AlertDialog.Builder(this)
                .setTitle("إضافة قسم")
                .setView(view)
                .setPositiveButton("إضافة", (d, w) -> {
                    String title = tTitle.getText().toString();
                    String content = tContent.getText().toString();
                    if (!title.isEmpty()) {
                        MainActivity.sections.add(new TeamSection(title, content));
                        saveAndRefresh();
                    }
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    void saveAndRefresh() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
        prefs.edit().putString(MainActivity.KEY, new Gson().toJson(MainActivity.sections)).apply();
        refresh();
    }
}