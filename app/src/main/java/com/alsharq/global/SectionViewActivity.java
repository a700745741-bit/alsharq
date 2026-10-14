package com.alsharq.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SectionViewActivity extends AppCompatActivity {

    TextView titleText, contentText;
    ArrayList<TeamSection> sections;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_view);

        titleText = findViewById(R.id.viewTitle);
        contentText = findViewById(R.id.viewContent);

        position = getIntent().getIntExtra("position", 0);
        loadSections();

        if (position < sections.size()) {
            TeamSection sec = sections.get(position);
            setTitle(sec.title);
            titleText.setText(sec.title);
            contentText.setText(sec.content);
        }
    }

    private void loadSections() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(MainActivity.KEY_SECTIONS, null);
        Type type = new TypeToken<ArrayList<TeamSection>>(){}.getType();
        sections = new Gson().fromJson(json, type);
    }
}