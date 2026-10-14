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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button adminButton = findViewById(R.id.adminButton);
        adminButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdminActivity.class));
        });
        List<Section> sections = new ArrayList<>();
        sections.add(new Section("من نحن", "نحن شركة الشرق العالمية"));
        sections.add(new Section("رؤيتنا", "أن نكون رواداً"));
        sections.add(new Section("رسالتنا", "تقديم خدمات متميزة"));
        sections.add(new Section("خدماتنا", "الاستيراد والتصدير"));
        sections.add(new Section("فريق العمل", "فريق متخصص"));
        sections.add(new Section("مشاريعنا", "أهم مشاريعنا"));
        sections.add(new Section("عملاؤنا", "نفتخر بخدمة عملائنا"));
        sections.add(new Section("تواصل معنا", "عدن - اليمن"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SectionAdapter(sections));
    }
}