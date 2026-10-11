package com.alsharq.global;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import com.alsharq.team.AdminHelper;

public class MainActivity extends Activity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button btnWhats = new Button(this);
        btnWhats.setText("واتساب");
        Button btnFb = new Button(this);
        btnFb.setText("فيسبوك");
        Button btnSite = new Button(this);
        btnSite.setText("الموقع");
        Button btnAdd = new Button(this);
        btnAdd.setText("اضافة بطولة +");
        btnWhats.setOnClickListener(v -> openLink("https://wa.me/967774000000"));
        btnFb.setOnClickListener(v -> openLink("https://facebook.com/alsharq"));
        btnSite.setOnClickListener(v -> webView.loadUrl("https://alsharq-global.com"));
        btnAdd.setOnClickListener(v -> AdminHelper.showAddDialog(this, "بطولات الفريق"));
        btnLayout.addView(btnWhats);
        btnLayout.addView(btnFb);
        btnLayout.addView(btnSite);
        btnLayout.addView(btnAdd);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://alsharq-global.com");
        layout.addView(btnLayout);
        layout.addView(webView);
        setContentView(layout);
    }
    void openLink(String url){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            AdminHelper.handleImageResult(this, data, AdminHelper.tempPreview);
        }
    }
}
