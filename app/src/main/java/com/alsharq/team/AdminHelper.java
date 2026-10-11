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

    public static void showAddDialog(Context context, String sectionName) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null);
        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etYear = view.findViewById(R.id.et_year);
        EditText etDesc = view.findViewById(R.id.et_desc);
        tempPreview = view.findViewById(R.id.img_preview);
        
        view.findViewById(R.id.btn_pick).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((android.app.Activity)context).startActivityForResult(intent, 1001);
        });

        new AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton("حفظ", (d,w)->{
                try{
                    JSONObject obj = new JSONObject();
                    obj.put("title", etTitle.getText().toString());
                    obj.put("year", etYear.getText().toString());
                    obj.put("desc", etDesc.getText().toString());
                    obj.put("image", tempBase64);
                    SharedPreferences p = context.getSharedPreferences("alsharq", Context.MODE_PRIVATE);
                    String old = p.getString("data_"+sectionName,"[]");
                    JSONArray arr = new JSONArray(old);
                    arr.put(obj);
                    p.edit().putString("data_"+sectionName, arr.toString()).apply();
                    Toast.makeText(context,"تم حفظ "+sectionName+" ✅", Toast.LENGTH_SHORT).show();
                    tempBase64="";
                }catch(Exception e){}
            })
            .setNegativeButton("الغاء", null).show();
    }

    public static void handleImageResult(Context context, Intent data, ImageView preview){
        try{
            android.net.Uri uri = data.getData();
            Bitmap bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            Bitmap scaled = Bitmap.createScaledBitmap(bmp, 600, 600 * bmp.getHeight() / bmp.getWidth(), true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            tempBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            if(preview!=null) preview.setImageBitmap(scaled);
            if(tempPreview!=null) tempPreview.setImageBitmap(scaled);
        }catch(Exception e){}
    }
}
