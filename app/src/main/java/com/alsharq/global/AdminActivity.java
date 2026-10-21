package com.alsharq.alhami;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private Spinner categorySpinner;
    private EditText titleInput;
    private EditText descriptionInput;
    private EditText imageInput;
    private Button publishButton;

    private final String[] categories = {
            "news",
            "activities",
            "trips",
            "matches",
            "championships",
            "players",
            "presidents",
            "history",
            "gallery"
    };

    private final String[] categoryNames = {
            "الأخبار",
            "الأنشطة",
            "الرحلات",
            "المباريات",
            "البطولات",
            "اللاعبون",
            "رؤساء الفريق",
            "تاريخ الفريق",
            "معرض الصور"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();

        buildInterface();
    }

    private void buildInterface() {

        ScrollView scrollView = new ScrollView(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        scrollView.addView(layout);

        TextView heading = new TextView(this);
        heading.setText("لوحة إدارة فريق الشرق");
        heading.setTextSize(25);
        heading.setGravity(android.view.Gravity.CENTER);
        heading.setPadding(0, 12, 0, 28);

        layout.addView(heading);

        TextView categoryLabel = new TextView(this);
        categoryLabel.setText("اختر القسم:");
        categoryLabel.setTextSize(17);
        layout.addView(categoryLabel);

        categorySpinner = new Spinner(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(adapter);

        layout.addView(categorySpinner);

        titleInput = new EditText(this);
        titleInput.setHint("عنوان الخبر أو الموضوع");
        titleInput.setSingleLine(false);
        layout.addView(titleInput);

        descriptionInput = new EditText(this);
        descriptionInput.setHint("اكتب التفاصيل هنا");
        descriptionInput.setMinLines(4);
        descriptionInput.setGravity(
                android.view.Gravity.TOP | android.view.Gravity.START
        );
        layout.addView(descriptionInput);

        imageInput = new EditText(this);
        imageInput.setHint("رابط الصورة (اختياري)");
        imageInput.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT
                        | android.text.InputType.TYPE_TEXT_VARIATION_URI
        );
        layout.addView(imageInput);

        TextView note = new TextView(this);
        note.setText(
                "ملاحظة: أدخل رابط صورة صالحًا إن أردت إرفاق صورة. "
                        + "رفع الصور مباشرة من الهاتف سيُجهز في خطوة لاحقة."
        );
        note.setTextSize(13);
        note.setPadding(0, 16, 0, 16);
        layout.addView(note);

        publishButton = new Button(this);
        publishButton.setText("حفظ ونشر المحتوى");
        layout.addView(publishButton);

        publishButton.setOnClickListener(this::saveContent);

        setContentView(scrollView);
    }

    private void saveContent(View view) {

        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String image = imageInput.getText().toString().trim();

        if (title.isEmpty()) {
            titleInput.setError("اكتب عنوان المحتوى");
            titleInput.requestFocus();
            return;
        }

        int selectedIndex = categorySpinner.getSelectedItemPosition();

        if (selectedIndex < 0 || selectedIndex >= categories.length) {
            Toast.makeText(
                    this,
                    "يرجى اختيار القسم",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String collection = categories[selectedIndex];

        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("desc", description);
        data.put("description", description);
        data.put("image", image);
        data.put("category", collection);
        data.put("createdAt", System.currentTimeMillis());
        data.put("time", System.currentTimeMillis());
        data.put("published", true);

        publishButton.setEnabled(false);
        publishButton.setText("جارٍ الحفظ...");

        db.collection(collection)
                .add(data)
                .addOnSuccessListener(documentReference -> {

                    Toast.makeText(
                            AdminActivity.this,
                            "تم حفظ المحتوى بنجاح",
                            Toast.LENGTH_LONG
                    ).show();

                    titleInput.setText("");
                    descriptionInput.setText("");
                    imageInput.setText("");

                    publishButton.setEnabled(true);
                    publishButton.setText("حفظ ونشر المحتوى");
                })
                .addOnFailureListener(error -> {

                    Toast.makeText(
                            AdminActivity.this,
                            "تعذر الحفظ: " + error.getLocalizedMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                    publishButton.setEnabled(true);
                    publishButton.setText("إعادة المحاولة");
                });
    }
}
