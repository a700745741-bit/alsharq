package com.alsharq.alhami;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra("title");

        if (title == null || title.trim().isEmpty()) {
            title = "قوائم فريق الشرق";
        }

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(24, 24, 24, 24);

        TextView heading = new TextView(this);
        heading.setText(title);
        heading.setTextSize(24);
        heading.setGravity(Gravity.CENTER);

        TextView message = new TextView(this);
        message.setText("محتوى فريق الشرق سيظهر هنا.");
        message.setTextSize(18);
        message.setGravity(Gravity.CENTER);
        message.setPadding(0, 24, 0, 0);

        layout.addView(heading);
        layout.addView(message);

        setContentView(layout);
    }
}
