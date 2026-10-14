package com.alsharq.global;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SectionViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_view);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        ((TextView)findViewById(R.id.viewTitle)).setText(title);
        ((TextView)findViewById(R.id.viewContent)).setText(content);
    }
}