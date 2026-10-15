package com.alsharq.alhami;
import android.content.Intent; import android.net.Uri; import android.os.Bundle; import android.widget.Toast; import android.app.AlertDialog; import android.widget.EditText; import androidx.appcompat.app.AppCompatActivity; import com.google.firebase.firestore.FirebaseFirestore; import com.google.firebase.messaging.FirebaseMessaging;
public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db; int logoClicks=0; long lastClickTime=0; String ADMIN_PASS="alsharq2026";
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main); db=FirebaseFirestore.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("alsharq_news");
        findViewById(R.id.logoLayout).setOnClickListener(v->{
            long now=System.currentTimeMillis(); if(now-lastClickTime>2000) logoClicks=0; logoClicks++; lastClickTime=now;
            if(logoClicks>=5){logoClicks=0; showAdminLogin();}
        });
        findViewById(R.id.icon1).setOnClickListener(v->open("players")); findViewById(R.id.icon2).setOnClickListener(v->open("presidents"));
        findViewById(R.id.icon3).setOnClickListener(v->open("news")); findViewById(R.id.icon4).setOnClickListener(v->open("matches"));
        findViewById(R.id.icon5).setOnClickListener(v->open("gallery")); findViewById(R.id.icon6).setOnClickListener(v->open("championships"));
        findViewById(R.id.icon7).setOnClickListener(v->open("history")); findViewById(R.id.icon8).setOnClickListener(v->open("stadium"));
        findViewById(R.id.btn_whatsapp).setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/967777777777"))));
        findViewById(R.id.btn_facebook).setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/alsharqteam"))));
        db.collection("news").addSnapshotListener((value, error)->{ if(value!=null &&!value.isEmpty()) Toast.makeText(this,"خبر جديد 🔔",Toast.LENGTH_SHORT).show(); });
    }
    void showAdminLogin(){ EditText input=new EditText(this); input.setHint("كلمة السر"); new AlertDialog.Builder(this).setTitle("دخول المدير").setView(input).setPositiveButton("دخول",(d,w)->{ if(input.getText().toString().equals(ADMIN_PASS)) startActivity(new Intent(this,AdminActivity.class)); else Toast.makeText(this,"خطأ",Toast.LENGTH_SHORT).show(); }).show(); }
    void open(String c){ Intent i=new Intent(this,ListActivity.class); i.putExtra("collection",c); startActivity(i); }
}