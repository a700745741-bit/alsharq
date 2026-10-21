package com.alsharq.global;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private EditText etTitle, etContent, etImage;
    private Button btnPublish;
    private FirebaseFirestore db;

    // غير كلمة السر من هنا
    private final String ADMIN_PASSWORD = "Alsharq2026";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // حماية بكلمة سر قبل فتح الصفحة
        showPasswordDialog();

        db = FirebaseFirestore.getInstance();

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etImage = findViewById(R.id.etImage);
        btnPublish = findViewById(R.id.btnPublish);

        btnPublish.setOnClickListener(v -> publishPost());
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("دخول الإدارة");
        builder.setMessage("أدخل كلمة سر الإدارة");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("كلمة السر");
        builder.setView(input);

        builder.setCancelable(false);
        builder.setPositiveButton("دخول", (dialog, which) -> {
            String pass = input.getText().toString().trim();
            if (!pass.equals(ADMIN_PASSWORD)) {
                Toast.makeText(this, "كلمة السر خطأ!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "مرحبا بك في لوحة الإدارة", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("إلغاء", (dialog, which) -> finish());
        builder.show();
    }

    private void publishPost() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        String imageUrl = etImage.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "اكتب العنوان والمحتوى", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> post = new HashMap<>();
        post.put("title", title);
        post.put("content", content);
        post.put("imageUrl", imageUrl);
        post.put("timestamp", FieldValue.serverTimestamp());

        db.collection("posts").add(post)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "تم النشر بنجاح ✅", Toast.LENGTH_SHORT).show();
                    etTitle.setText("");
                    etContent.setText("");
                    etImage.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "فشل النشر: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}