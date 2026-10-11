package com.alsharq.team;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;

public class AdminHelper {
    public static String tempBase64 = "";
    public static ImageView tempPreview = null;

    public static void showAddDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(com.alsharq.R.layout.dialog_add, null);
        EditText etTitle = view.findViewById(com.alsharq.R.id.et_title);
        EditText etYear = view.findViewById(com.alsharq.R.id.et_year);
        EditText etDesc = view.findViewById(com.alsharq.R.id.et_desc);
        tempPreview = view.findViewById(com.alsharq.R.id.img_preview);
        tempBase64 = "";
        if(tempPreview!=null) tempPreview.setImageResource(android.R.color.transparent);

        view.findViewById(com.alsharq.R.id.btn_pick).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((android.app.Activity)context).startActivityForResult(intent, 1001);
        });

        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("اضافة بطولة جديدة")
                .setPositiveButton("حفظ", (d, w) -> {
                    try{
                        if(etTitle.getText().toString().trim().isEmpty()){
                            Toast.makeText(context,"اكتب اسم البطولة",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject obj = new JSONObject();
                        obj.put("title", etTitle.getText().toString());
                        obj.put("year", etYear.getText().toString());
                        obj.put("desc", etDesc.getText().toString());
                        obj.put("image", tempBase64);
                        SharedPreferences p = context.getSharedPreferences("alsharq_data", Context.MODE_PRIVATE);
                        String old = p.getString("data", "[]");
                        JSONArray arr = new JSONArray(old);
                        arr.put(obj);
                        p.edit().putString("data", arr.toString()).apply();
                        Toast.makeText(context, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                        tempBase64="";
                        tempPreview=null;
                        if(context instanceof android.app.Activity){
                            ((android.app.Activity)context).recreate();
                        }
                    }catch(Exception e){
                        Toast.makeText(context, "خطأ: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("الغاء", null)
                .show();
    }

    // هذه الدالة المتوافقة مع MainActivity.java سطر 52
    public static void handleImageResult(Context context, Intent data, ImageView preview) {
        try{
            if(data==null || data.getData()==null) return;
            android.net.Uri uri = data.getData();
            Bitmap bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            Bitmap scaled = Bitmap.createScaledBitmap(bmp, 600, 600, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 75, baos);
            tempBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            if(preview!=null) preview.setImageBitmap(scaled);
            if(tempPreview!=null) tempPreview.setImageBitmap(scaled);
            Toast.makeText(context,"تم اختيار الصورة",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(context,"فشل تحميل الصورة: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    // للتوافق مع الاستدعاء القديم
    public static void handleImageResult(Intent data){
        // لا يستخدم
    }
}
