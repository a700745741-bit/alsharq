#!/bin/bash
set -e
rm -rf app/src/main/res app/src/main/java/com app/build.gradle build.gradle settings.gradle gradle.properties gradle
mkdir -p app/src/main/java/com/alsharq/team
mkdir -p app/src/main/res/values
mkdir -p gradle/wrapper
echo "include ':app'" > settings.gradle
echo "android.useAndroidX=true" > gradle.properties
cat > build.gradle <<'B1'
buildscript {
 repositories { google(); mavenCentral() }
 dependencies { classpath 'com.android.tools.build:gradle:8.2.0' }
}
allprojects { repositories { google(); mavenCentral() } }
B1
cat > app/build.gradle <<'A1'
plugins { id 'com.android.application' }
android {
 namespace 'com.alsharq.team'
 compileSdk 33
 defaultConfig { applicationId "com.alsharq.team"; minSdk 21; targetSdk 33; versionCode 40; versionName "10.0" }
 compileOptions { sourceCompatibility JavaVersion.VERSION_17; targetCompatibility JavaVersion.VERSION_17 }
}
dependencies { implementation 'androidx.cardview:cardview:1.0.0'; implementation 'androidx.appcompat:appcompat:1.6.1' }
A1
cat > gradle/wrapper/gradle-wrapper.properties <<'GW'
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.4-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
GW
echo '<resources></resources>' > app/src/main/res/values/colors.xml
cat > app/src/main/AndroidManifest.xml <<'M1'
<manifest xmlns:android="http://schemas.android.com/apk/res/android"><uses-permission android:name="android.permission.INTERNET"/><application android:label="فريق الشرق"><activity android:name=".MainActivity" android:exported="true" android:theme="@android:style/Theme.Light.NoTitleBar"><intent-filter><action android:name="android.intent.action.MAIN"/><category android:name="android.intent.category.LAUNCHER"/></intent-filter></activity><activity android:name=".DetailActivity" android:theme="@android:style/Theme.Light.NoTitleBar"/></application></manifest>
M1
cat > app/src/main/java/com/alsharq/team/MainActivity.java <<'J1'
package com.alsharq.team;
import android.app.*;import android.content.*;import android.graphics.*;import android.graphics.drawable.*;import android.net.Uri;import android.os.Bundle;import android.view.*;import android.widget.*;import androidx.cardview.widget.CardView;
public class MainActivity extends Activity{
protected void onCreate(Bundle b){super.onCreate(b);
LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setBackgroundColor(Color.parseColor("#F2F4F7"));
LinearLayout top=new LinearLayout(this);top.setOrientation(0);top.setPadding(20,12,20,12);top.setBackgroundColor(Color.parseColor("#0d47a1"));
Button bf=new Button(this);bf.setText("فيسبوك");bf.setTextColor(-1);bf.setBackgroundColor(Color.parseColor("#1877F2"));bf.setOnClickListener(v->{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/ALSHAREQ/")));});
Button bw=new Button(this);bw.setText("واتساب");bw.setTextColor(-1);bw.setBackgroundColor(Color.parseColor("#25D366"));bw.setOnClickListener(v->{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://chat.whatsapp.com/GKe8JAo24dO4tjdY9XCMc0")));});
LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(0,-2,1);bp.setMargins(10,0,10,0);top.addView(bf,bp);top.addView(bw,bp);
LinearLayout header=new LinearLayout(this);header.setOrientation(1);header.setGravity(Gravity.CENTER);header.setPadding(0,35,0,35);header.setBackgroundColor(Color.parseColor("#1976D2"));
TextView logo=new TextView(this);logo.setText("الشرق");logo.setTextColor(-1);logo.setTextSize(26);logo.setGravity(Gravity.CENTER);logo.setTypeface(null,Typeface.BOLD);GradientDrawable circ=new GradientDrawable();circ.setShape(1);circ.setStroke(4,Color.WHITE);circ.setColor(Color.parseColor("#1565C0"));logo.setBackground(circ);int s=(int)(90*getResources().getDisplayMetrics().density);logo.setLayoutParams(new LinearLayout.LayoutParams(s,s));logo.setOnLongClickListener(v->{login();return true;});
TextView t1=new TextView(this);t1.setText("فريق الشرق");t1.setTextColor(-1);t1.setTextSize(26);t1.setGravity(Gravity.CENTER);t1.setPadding(0,15,0,0);t1.setTypeface(null,Typeface.BOLD);
TextView t2=new TextView(this);t2.setText("الرياضي الثقافي الاجتماعي بالحامي");t2.setTextColor(-1);t2.setTextSize(12);t2.setGravity(Gravity.CENTER);
header.addView(logo);header.addView(t1);header.addView(t2);
GridLayout grid=new GridLayout(this);grid.setColumnCount(2);grid.setPadding(15,15,15,15);grid.setUseDefaultMargins(true);
grid.addView(card("نبذة الفريق"));grid.addView(card("بطولات الفريق"));grid.addView(card("رؤساء الفريق"));grid.addView(card("صور الفريق"));grid.addView(card("البومات الفريق"));grid.addView(card("اخبار الفريق"));grid.addView(card("رابط الفريق واتس اب"));grid.addView(card("رابط الفريق فيس بوك"));
ScrollView sv=new ScrollView(this);LinearLayout cont=new LinearLayout(this);cont.setOrientation(1);cont.addView(header);cont.addView(grid);
TextView hint=new TextView(this);hint.setText("اضغط مطولا على الشرق للدخول كمدير - 1234");hint.setGravity(Gravity.CENTER);hint.setTextColor(Color.GRAY);hint.setTextSize(11);hint.setPadding(0,20,0,30);cont.addView(hint);sv.addView(cont);
root.addView(top);root.addView(sv);setContentView(root);}
CardView card(String t){CardView c=new CardView(this);c.setRadius(30);c.setCardElevation(8);c.setUseCompatPadding(true);c.setCardBackgroundColor(Color.WHITE);GridLayout.LayoutParams lp=new GridLayout.LayoutParams();lp.width=0;lp.height=(int)(110*getResources().getDisplayMetrics().density);lp.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);lp.setMargins(12,12,12,12);c.setLayoutParams(lp);TextView tv=new TextView(this);tv.setText(t);tv.setGravity(Gravity.CENTER);tv.setTextColor(Color.parseColor("#0d47a1"));tv.setTypeface(null,Typeface.BOLD);tv.setTextSize(14);c.addView(tv);c.setOnClickListener(v->{if(t.contains("واتس")){startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://chat.whatsapp.com/GKe8JAo24dO4tjdY9XCMc0")));}else if(t.contains("فيس")){startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/ALSHAREQ/")));}else{Intent i=new Intent(this,DetailActivity.class);i.putExtra("t",t);startActivity(i);}});return c;}
void login(){AlertDialog.Builder b=new AlertDialog.Builder(this);b.setTitle("دخول المدير");EditText e=new EditText(this);e.setHint("كلمة السر 1234");b.setView(e);b.setPositiveButton("دخول",(d,w)->{if(e.getText().toString().equals("1234")){Intent i=new Intent(this,DetailActivity.class);i.putExtra("t","لوحة تحكم المدير");i.putExtra("admin",true);startActivity(i);}else Toast.makeText(this,"خطأ",0).show();});b.setNegativeButton("الغاء",null);b.show();}
}
J1
cat > app/src/main/java/com/alsharq/team/DetailActivity.java <<'J2'
package com.alsharq.team;
import android.app.*;import android.content.*;import android.os.Bundle;import android.view.*;import android.widget.*;import java.util.*;
public class DetailActivity extends Activity{
String title;boolean isAdmin;LinearLayout list;SharedPreferences pref;ArrayList<String> items;
protected void onCreate(Bundle b){super.onCreate(b);title=getIntent().getStringExtra("t");isAdmin=getIntent().getBooleanExtra("admin",false);
LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setBackgroundColor(0xFFF2F4F7);
LinearLayout h=new LinearLayout(this);h.setOrientation(0);h.setBackgroundColor(0xFF1976D2);h.setPadding(20,20,20,20);
Button back=new Button(this);back.setText("رجوع");back.setTextColor(-1);back.setBackgroundColor(0);back.setOnClickListener(v->finish());
TextView tv=new TextView(this);tv.setText(title);tv.setTextColor(-1);tv.setTypeface(null,1);tv.setGravity(17);tv.setLayoutParams(new LinearLayout.LayoutParams(0,-2,1));
h.addView(back);h.addView(tv);if(isAdmin){Button add=new Button(this);add.setText("+ اضافة");add.setTextColor(-1);add.setBackgroundColor(0xFF0d47a1);add.setOnClickListener(v->{if(title.equals("رؤساء الفريق"))addPres();else addSimple();});h.addView(add);}
list=new LinearLayout(this);list.setOrientation(1);list.setPadding(20,20,20,20);ScrollView sv=new ScrollView(this);sv.addView(list);root.addView(h);root.addView(sv);setContentView(root);pref=getSharedPreferences("data",0);load();}
void load(){list.removeAllViews();String k=title.replace(" ","_");String s=pref.getString(k,"");items=new ArrayList<>();if(!s.isEmpty())items.addAll(Arrays.asList(s.split(";;NEXT;;")));
if(items.isEmpty()){TextView emp=new TextView(this);emp.setText(isAdmin?"اضغط + اضافة - كتابة كبيرة":"لا يوجد محتوى بعد");emp.setTextSize(22);emp.setGravity(17);emp.setPadding(0,60,0,0);list.addView(emp);return;}
for(int i=0;i<items.size();i++){String raw=items.get(i);
if(raw.startsWith("PRES|")){String[] p=raw.split("\\|",-1);String name=p.length>1?p[1]:"";String when=p.length>2?p[2]:"";String dur=p.length>3?p[3]:"";String info=p.length>4?p[4]:"";String tro=p.length>5?p[5]:"";
LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setBackgroundColor(-1);card.setPadding(35,30,35,30);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,18);card.setLayoutParams(lp);card.setElevation(8);
TextView num=new TextView(this);num.setText((i+1)+". "+name);num.setTextSize(24);num.setTypeface(null,1);num.setTextColor(0xFF0d47a1);
TextView sub=new TextView(this);sub.setText("اضغط هنا - متى مسك - المدة - المعلومات - التتويجات");sub.setTextSize(14);sub.setTextColor(0xFF888888);sub.setPadding(0,8,0,0);
card.addView(num);card.addView(sub);int idx=i;card.setOnClickListener(v->{show(name,when,dur,info,tro);});
if(isAdmin){LinearLayout bb=new LinearLayout(this);bb.setOrientation(0);bb.setPadding(0,15,0,0);Button e=new Button(this);e.setText("تعديل");Button d=new Button(this);d.setText("حذف");d.setTextColor(0xFFFF0000);d.setOnClickListener(v2->{items.remove(idx);save();load();});e.setOnClickListener(v2->{editPres(idx);});bb.addView(e,new LinearLayout.LayoutParams(0,-2,1));bb.addView(d,new LinearLayout.LayoutParams(0,-2,1));card.addView(bb);}list.addView(card);
}else{LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setBackgroundColor(-1);card.setPadding(35,35,35,35);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,18);card.setLayoutParams(lp);card.setElevation(8);TextView t=new TextView(this);t.setText((i+1)+". "+raw);t.setTextSize(24);t.setTypeface(null,1);t.setLineSpacing(12,1);card.addView(t);if(isAdmin){LinearLayout bb=new LinearLayout(this);bb.setOrientation(0);bb.setPadding(0,15,0,0);Button e=new Button(this);e.setText("تعديل");Button d=new Button(this);d.setText("حذف");d.setTextColor(0xFFFF0000);int idx=i;d.setOnClickListener(v->{items.remove(idx);save();load();});e.setOnClickListener(v->{editSimple(idx);});bb.addView(e,new LinearLayout.LayoutParams(0,-2,1));bb.addView(d,new LinearLayout.LayoutParams(0,-2,1));card.addView(bb);}list.addView(card);}}}
void show(String name,String when,String dur,String info,String tro){LinearLayout lay=new LinearLayout(this);lay.setOrientation(1);lay.setPadding(30,30,30,30);TextView t1=new TextView(this);t1.setText(name);t1.setTextSize(28);t1.setTypeface(null,1);t1.setTextColor(0xFF0d47a1);t1.setGravity(17);t1.setPadding(0,0,0,20);TextView t2=new TextView(this);t2.setText("متى مسك - "+when+" - كم فترة - "+dur+" - معلومات - "+name+" "+info+" - تتويجات - "+tro);t2.setTextSize(22);t2.setLineSpacing(14,1);ScrollView sv=new ScrollView(this);sv.addView(t2);lay.addView(t1);lay.addView(sv);new AlertDialog.Builder(this).setTitle("التفاصيل - كتابة كبيرة").setView(lay).setPositiveButton("اغلاق",null).show();}
void addPres(){LinearLayout lay=new LinearLayout(this);lay.setOrientation(1);lay.setPadding(20,20,20,20);EditText e1=new EditText(this);e1.setHint("الاسم");EditText e2=new EditText(this);e2.setHint("متى مسك");EditText e3=new