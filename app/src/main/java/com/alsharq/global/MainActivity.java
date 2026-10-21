package com.alsharq.alhami;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ValueCallback<Uri[]> fileCallback;

    private final ActivityResultLauncher<Intent> filePicker =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (fileCallback == null) {
                            return;
                        }

                        Uri[] selectedFiles = null;

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            Intent data = result.getData();

                            Uri selectedUri = data.getData();

                            if (selectedUri != null) {
                                selectedFiles = new Uri[]{selectedUri};
                            }
                        }

                        fileCallback.onReceiveValue(selectedFiles);
                        fileCallback = null;
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        configureWebView();

        subscribeToNewsNotifications();

        requestNotificationPermission();

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.loadUrl("https://alsharq1983.netlify.app/");
        }
    }

    private void configureWebView() {

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);

        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(
                    WebView view,
                    ValueCallback<Uri[]> callback,
                    FileChooserParams params) {

                if (fileCallback != null) {
                    fileCallback.onReceiveValue(null);
                }

                fileCallback = callback;

                try {
                    Intent intent = params.createIntent();
                    filePicker.launch(intent);
                    return true;

                } catch (Exception exception) {

                    fileCallback = null;

                    Toast.makeText(
                            MainActivity.this,
                            "تعذر فتح معرض الصور",
                            Toast.LENGTH_LONG
                    ).show();

                    return false;
                }
            }
        });
    }

    private void subscribeToNewsNotifications() {

        FirebaseMessaging.getInstance()
                .subscribeToTopic("all-news")
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        android.util.Log.d(
                                "AlSharq",
                                "تم الاشتراك في إشعارات الأخبار"
                        );
                    } else {
                        android.util.Log.e(
                                "AlSharq",
                                "تعذر الاشتراك في إشعارات الأخبار",
                                task.getException()
                        );
                    }
                });
    }

    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        1001
                );
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (webView != null) {
            webView.saveState(outState);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
}
