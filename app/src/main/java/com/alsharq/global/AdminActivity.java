package com.alsharq.global;
import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import com.google.firebase.firestore.FirebaseFirestore; import java.util.HashMap; import java.util.Map;
public class AdminActivity extends AppCompatActivity {
    FirebaseFirestore db; 
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); 
        LinearLayout layout=new LinearLayout(this); 
        layout.setOrientation(LinearLayout.VERTICAL); 
        layout.setPadding(30,30,30,30); 
        db=FirebaseFirestore.getInstance();
        TextView titleL=new TextView(this); titleL.setText("لوحة تحكم الشرق - الحامي"); titleL.setTextSize(18);
        Spinner spinner=new Spinner(this); 
        String[] cats={"players","presidents","news","matches","gallery","championships","history","stadium"}; 
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,cats));
        EditText title=new EditText(this); title.setHint("العنوان - مثال: فوز الشرق 2-0"); 
        EditText desc=new EditText(this); desc.setHint("الوصف / التفاصيل"); 
        EditText img=new EditText(this); img.setHint("رابط الصورة (اختياري)");
        Button save=new Button(this); save.setText("نشر + اشعار للكل 🔔");
        save.setOnClickListener(v->{ 
            Map<String,Object> data=new HashMap<>(); 
            data.put("title",title.getText().toString()); 
            data.put("desc",desc.getText().toString()); 
            data.put("image",img.getText().toString()); 
            data.put("time",System.currentTimeMillis()); 
            db.collection(cats[spinner.getSelectedItemPosition()]).add(data)
            .addOnSuccessListener(r->Toast.makeText(this,"تم النشر بنجاح ✅ سيصل اشعار للكل",Toast.LENGTH_LONG).show())
            .addOnFailureListener(e->Toast.makeText(this,"خطأ: "+e.getMessage(),Toast.LENGTH_LONG).show()); 
        });
        layout.addView(titleL); layout.addView(spinner); layout.addView(title); layout.addView(desc); layout.addView(img); layout.addView(save); 
        setContentView(layout);
    }
}