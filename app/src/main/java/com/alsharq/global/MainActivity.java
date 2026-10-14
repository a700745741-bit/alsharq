package com.alsharq.global;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SectionAdapter adapter;
    private List<Section> sections;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(this);
        recyclerView = findViewById(R.id.recyclerView);
        Button adminButton = findViewById(R.id.adminButton);

        adminButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdminActivity.class));
        });

        sections = tinyDB.getListObject("sections", Section.class);
        if (sections.isEmpty()) {
            sections = new ArrayList<>();
            sections.add(new Section("من نحن", "نحن شركة الشرق العالمية..."));
            sections.add(new Section("رؤيتنا", "أن نكون رواداً..."));
            sections.add(new Section("رسالتنا", "تقديم خدمات متميزة..."));
            sections.add(new Section("خدماتنا", "خدمات الاستيراد والتصدير..."));
            sections.add(new Section("فريق العمل", "فريق متخصص..."));
            sections.add(new Section("مشاريعنا", "أهم مشاريعنا..."));
            sections.add(new Section("عملاؤنا", "نفتخر بخدمة..."));
            sections.add(new Section("تواصل معنا", "عدن - اليمن..."));
            tinyDB.putListObject("sections", sections);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SectionAdapter(sections);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sections = tinyDB.getListObject("sections", Section.class);
        if (adapter != null) {
            adapter.updateList(sections);
        }
    }
}