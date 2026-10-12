package com.alsharq.team;
import com.alsharq.team.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.io.ByteArrayOutputStream;

public class AdminHelper {

    public static void showAddDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null);

        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etYear = view.findViewById(R.id.et_year);
        EditText etDesc = view.findViewById(R.id.et_desc);
        ImageView imgPreview = view.findViewById(R.id.img_preview);

        view.findViewById(R.id.btn_pick).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).startActivityForResult(intent, 1001);
            }
        });

        new AlertDialog.Builder(context)
                .setTitle("إضافة عمل جديد")
                .setView(view)
                .setPositiveButton("إضافة", (d, w) -> {
                    String title = etTitle.getText().toString().trim();
                    if (title.isEmpty()) {
                        Toast.makeText(context, "اكتب العنوان", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(context, "تمت الإضافة: " + title, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}