package com.alsharq.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditSectionActivity extends AppCompatActivity {

    EditText editTitle, editContent;
    Button saveButton;
    ArrayList<TeamSection> sections;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        saveButton = findViewById(R.id.saveButton);

        position = getIntent().getIntExtra("position", 0);
        loadSections();

        if (position < sections.size()) {
            TeamSection sec = sections.get(position);
            setTitle("تعديل: " + sec.title);
            editTitle.setText(sec.title);
            editContent.setText(sec.content);
        }

        saveButton.setOnClickListener(v -> {
            String newTitle = editTitle.getText().toString().trim();
            String newContent = editContent.getText().toString().trim();

            if (newTitle.isEmpty()) {
                Toast.makeText(this, "ادخل عنوان القسم", Toast.LENGTH_SHORT).show();
                return;
            }

            sections.get(position).title = newTitle;
            sections.get(position).content = newContent;

            saveSections();
            Toast.makeText(this, "تم الحفظ وسيظهر في الصفحة الرئيسية", Toast.LENGTH_LONG).show();
            finish(); // يرجع للوحة المدير
        });
    }

    private void loadSections() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(MainActivity.KEY_SECTIONS, null);
        Type type = new TypeToken<ArrayList<TeamSection>>(){}.getType();
        sections = new Gson().fromJson(json, type);
    }

    private void saveSections() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(MainActivity.KEY_SECTIONS, new Gson().toJson(sections));
        editor.apply();
    }
}