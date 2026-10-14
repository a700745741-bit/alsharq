package com.alsharq.global;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<TeamSection> sections;
    public static final String PREFS_NAME = "AlsharqPrefs";
    public static final String KEY_SECTIONS = "sections";
    String adminPassword = "1234"; // غير كلمة سر المدير من هنا

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        loadSections();

        SectionAdapter adapter = new SectionAdapter(this, sections, false);
        gridView.setAdapter(adapter);

        // ضغطة عادية = مشاهدة القسم
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, SectionViewActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        // ضغطة طويلة = دخول المدير (فقط على أيقونة الشرق - اول واحدة)
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position == 0) { // اول ايقونة هي الشرق
                showAdminDialog();
                return true;
            }
            return false;
        });
    }

    private void showAdminDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("دخول المدير");
        final android.widget.EditText input = new android.widget.EditText(this);
        input.setHint("ادخل كلمة سر المدير");
        input.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("دخول", (dialog, which) -> {
            if (input.getText().toString().equals(adminPassword)) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            } else {
                Toast.makeText(this, "كلمة السر خطأ", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("الغاء", null);
        builder.show();
    }

    private void loadSections() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(KEY_SECTIONS, null);
        if (json == null) {
            // اول مرة - انشئ 8 اقسام افتراضية
            sections = new ArrayList<>();
            sections.add(new TeamSection("فريق الشرق", "الشعار الرسمي", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("الاخبار", "لا يوجد اخبار حاليا", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("المباريات", "لا يوجد مباريات", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("اللاعبون", "قائمة اللاعبين", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("الصور", "معرض الصور", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("الفيديوهات", "معرض الفيديو", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("الادارة", "ادارة الفريق", R.drawable.ic_launcher_foreground));
            sections.add(new TeamSection("عن الفريق", "معلومات عنا", R.drawable.ic_launcher_foreground));
            saveSections();
        } else {
            Type type = new TypeToken<ArrayList<TeamSection>>(){}.getType();
            sections = new Gson().fromJson(json, type);
        }
    }

    private void saveSections() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(KEY_SECTIONS, new Gson().toJson(sections));
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSections();
        gridView.setAdapter(new SectionAdapter(this, sections, false));
    }
}