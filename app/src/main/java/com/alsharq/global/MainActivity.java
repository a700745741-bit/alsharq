package com.alsharq.global;

import android.os.Bundle;
import android.widget.*;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText emailField, passField;
    Button loginBtn, registerBtn;
    TextView statusText;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // نصمم الواجهة برمجياً عشان ما نحتاج ملف layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 100, 50, 100);

        emailField = new EditText(this);
        emailField.setHint("البريد الإلكتروني");
        
        passField = new EditText(this);
        passField.setHint("كلمة المرور");
        passField.setInputType(129); // password

        loginBtn = new Button(this);
        loginBtn.setText("تسجيل دخول");

        registerBtn = new Button(this);
        registerBtn.setText("إنشاء حساب جديد");

        statusText = new TextView(this);
        statusText.setText("مرحباً في الشرق العالمي - سجل دخولك");

        layout.addView(statusText);
        layout.addView(emailField);
        layout.addView(passField);
        layout.addView(loginBtn);
        layout.addView(registerBtn);

        setContentView(layout);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // لو مسجل من قبل يدخل مباشرة
        if (auth.getCurrentUser() != null) {
            statusText.setText("تم تسجيل الدخول: " + auth.getCurrentUser().getEmail());
        }

        loginBtn.setOnClickListener(v -> login());
        registerBtn.setOnClickListener(v -> register());
    }

    void login() {
        String email = emailField.getText().toString().trim();
        String pass = passField.getText().toString().trim();
        if(email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "اكمل البيانات", Toast.LENGTH_SHORT).show();
            return;
        }
        statusText.setText("جاري تسجيل الدخول...");
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener(a -> {
                statusText.setText("تم الدخول بنجاح: " + email);
                Toast.makeText(this, "مرحباً!", Toast.LENGTH_SHORT).show();
                // هنا لاحقاً نفتح صفحة الألبومات
            })
            .addOnFailureListener(e -> {
                statusText.setText("فشل: " + e.getMessage());
            });
    }

    void register() {
        String email = emailField.getText().toString().trim();
        String pass = passField.getText().toString().trim();
        if(email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "اكمل البيانات", Toast.LENGTH_SHORT).show();
            return;
        }
        statusText.setText("جاري إنشاء الحساب...");
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener(a -> {
                String uid = auth.getCurrentUser().getUid();
                Map<String, Object> user = new HashMap<>();
                user.put("email", email);
                user.put("createdAt", System.currentTimeMillis());
                db.collection("users").document(uid).set(user);
                statusText.setText("تم إنشاء الحساب: " + email);
            })
            .addOnFailureListener(e -> {
                statusText.setText("فشل: " + e.getMessage());
            });
    }
}
