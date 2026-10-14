package com.alsharq.global;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static List<TeamSection> sections = new ArrayList<>();
    public static final String PREFS = "app_data";
    public static final String KEY = "sections";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        // هذا ينشئ 8 قوائم ثابتة أول مرة فقط
        if (sections.isEmpty()) {
            sections.add(new TeamSection("1- من نحن", "محتوى من نحن..."));
            sections.add(new TeamSection("2- رؤيتنا", "محتوى رؤيتنا..."));
            sections.add(new TeamSection("3- رسالتنا", "محتوى رسالتنا..."));
            sections.add(new TeamSection("4- خدماتنا", "محتوى خدماتنا..."));
            sections.add(new TeamSection("5- فريق العمل", "محتوى الفريق..."));
            sections.add(new TeamSection("6- مشاريعنا", "محتوى المشاريع..."));
            sections.add(new TeamSection("7- عملاؤنا", "محتوى العملاء..."));
            sections.add(new TeamSection("8- تواصل معنا", "محتوى التواصل..."));
            saveData();
        }

        SectionAdapter adapter = new SectionAdapter(this, sections, false);
        recyclerView.setAdapter(adapter);

        // زر دخول الإدارة - انت فقط تعرفه
        // لازم تضيف زر في activity_main.xml اسمه adminButton
        try {
            findViewbyId(R.id.adminButton).setOnClickListener(v -> {
                startActivity(new Intent(this, AdminActivity.class));
            });
        } catch (Exception e) {}
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        recyclerView.setAdapter(new SectionAdapter(this, sections, false));
    }

    public void loadData() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String json = prefs.getString(KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<TeamSection>>() {}.getType();
            sections = new Gson().fromJson(json, type);
        }
    }

    public void saveData() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        prefs.edit().putString(KEY, new Gson().toJson(sections)).apply();
    }
}