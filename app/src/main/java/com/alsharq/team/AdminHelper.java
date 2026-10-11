package com.alsharq.team;

import android.app.AlertDialog;
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
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;

public class AdminHelper {
    public static String tempBase64 = "";
    public static ImageView tempPreview;
    public static View tempViewPreview;

    public static void showAddDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(com.alsharq.R.layout.dialog_add, null);
        EditText etTitle = view.findViewById(com.alsharq.R.id.et_title);
        EditText etYear = view.findViewById(com.alsharq.R.id.et_year);
        EditText etDesc = view.findViewById(com.alsharq.R.id.et_desc);
        tempPreview = view.findViewById(com.alsharq.R.id.img_preview);
        tempBase64 = "";

        view.findViewById(com.alsharq.R.id.btn_pick).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((android.app.Activity)context).startActivityForResult(intent, 1001);
        });

        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("اضافة بطولة جديدة")
                .setPositiveButton("حفظ", (d, w) -> {
                    try{
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

    public static void handleImageResult(Intent data){
        try{
            android.net.Uri uri = data.getData();
            Bitmap bmp = MediaStore.Images.Media.getBitmap(com.alsharq.App.getAppContext().getContentResolver(), uri);
            Bitmap scaled = Bitmap.createScaledBitmap(bmp, 500, 500, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            tempBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            if(tempPreview!=null) tempPreview.setImageBitmap(scaled);
            if(tempViewPreview!=null) ((ImageView)tempViewPreview).setImageBitmap(scaled);
        }catch(Exception e){
        }
    }
}