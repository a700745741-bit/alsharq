package com.alsharq.global;
import android.os.Bundle;
import android.widget.*;
import android.widget.LinearLayout;
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
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 120, 60, 60);
        emailField = new EditText(this);
        emailField.setHint("البريد الالكتروني");
        passField = new EditText(this);
        passField.setHint("كلمة المرور");
        passField.setInputType(129);
        loginBtn = new Button(this);
        loginBtn.setText("تسجيل دخول");
        registerBtn = new Button(this);
        registerBtn.setText("انشاء حساب");
        statusText = new TextView(this);
        statusText.setText("مرحبا في الشرق العالمي");
        layout.addView(statusText);
        layout.addView(emailField);
        layout.addView(passField);
        layout.addView(loginBtn);
        layout.addView(registerBtn);
        setContentView(layout);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loginBtn.setOnClickListener(v -> {
            String e = emailField.getText().toString().trim();
            String p = passField.getText().toString().trim();
            if(e.isEmpty()||p.isEmpty()){Toast.makeText(this,"اكمل البيانات",Toast.LENGTH_SHORT).show();return;}
            statusText.setText("جاري الدخول...");
            auth.signInWithEmailAndPassword(e,p).addOnSuccessListener(a-> statusText.setText("تم الدخول: "+e)).addOnFailureListener(err-> statusText.setText("خطأ: "+err.getMessage()));
        });
        registerBtn.setOnClickListener(v -> {
            String e = emailField.getText().toString().trim();
            String p = passField.getText().toString().trim();
            if(e.isEmpty()||p.isEmpty()){Toast.makeText(this,"اكمل البيانات",Toast.LENGTH_SHORT).show();return;}
            statusText.setText("جاري انشاء الحساب...");
            auth.createUserWithEmailAndPassword(e,p).addOnSuccessListener(a-> {
                Map<String,Object> u = new HashMap<>();
                u.put("email", e);
                db.collection("users").document(auth.getUid()).set(u);
                statusText.setText("تم انشاء الحساب: "+e);
            }).addOnFailureListener(err-> statusText.setText("خطأ: "+err.getMessage()));
        });
    }
}