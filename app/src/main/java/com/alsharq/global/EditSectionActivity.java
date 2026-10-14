package com.alsharq.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

public class EditSectionActivity extends AppCompatActivity {

    int pos;
    EditText eTitle, eContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);

        pos = getIntent().getIntExtra("pos", -1);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        eTitle = findViewById(R.id.editTitle);
        eContent = findViewById(R.id.editContent);
        Button save = findViewById(R.id.saveButton);

        eTitle.setText(title);
        eContent.setText(content);

        save.setOnClickListener(v -> {
            MainActivity.sections.get(pos).title = eTitle.getText().toString();
            MainActivity.sections.get(pos).content = eContent.getText().toString();

            SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
            prefs.edit().putString(MainActivity.KEY, new Gson().toJson(MainActivity.sections)).apply();

            Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
            finish();
        });

        // زر حذف
        findViewById(R.id.saveButton).setOnLongClickListener(view -> {
            MainActivity.sections.remove(pos);
            SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
            prefs.edit().putString(MainActivity.KEY, new Gson().toJson(MainActivity.sections)).apply();
            Toast.makeText(this, "تم الحذف", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        });
    }
}