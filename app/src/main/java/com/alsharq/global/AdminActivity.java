package com.alsharq.global;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<TeamSection> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        gridView = findViewById(R.id.gridViewAdmin);
        loadSections();

        SectionAdapter adapter = new SectionAdapter(this, sections, true);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(AdminActivity.this, EditSectionActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }

    private void loadSections() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(MainActivity.KEY_SECTIONS, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<TeamSection>>(){}.getType();
            sections = new Gson().fromJson(json, type);
        } else {
            sections = new ArrayList<>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSections();
        gridView.setAdapter(new SectionAdapter(this, sections, true));
    }
}