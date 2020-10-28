package com.example.haihanmingyue.hospital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyMseeageActivity extends AppCompatActivity {
    private String str;


    private MydatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mseeage);
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        setTitle("我的信息");
        //

// 已有新的思路不需要这个
//        Button shuax=(Button) findViewById(R.id.shuaxin);
//        shuax.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MyMseeageActivity.this,MyMseeageActivity.class);
//                intent.putExtra("my_code", str);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
//                startActivity(intent);
//                finish();
//            }
//        });//自己跳转自己

        Button xiugai_message=(Button) findViewById(R.id.xiu_gai_message);
        xiugai_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyMseeageActivity.this,ChangeMessageActivity.class);
                intent.putExtra("my_code", str);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);
                finish();
            }
        });

        //

        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        str=bundle.getString("my_code");//getString()返回指定key的值
        TextView user_name = (TextView) findViewById(R.id.user_name4);//用TextView显示值
        user_name.setText(str);
        String ccc=user_name.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from Doctor where code="+ccc;
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String genstr = cursor.getString(cursor.getColumnIndex("genstr"));
            String bithday = cursor.getString(cursor.getColumnIndex("birthday"));
            String depart = cursor.getString(cursor.getColumnIndex("depart"));
            String zhicheng = cursor.getString(cursor.getColumnIndex("zhicheng"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));

        TextView xingming = (TextView) findViewById(R.id.xing_ming);//用TextView显示值
           xingming.setText(name);

          TextView ningl = (TextView) findViewById(R.id.ningl);//用TextView显示值
           ningl.setText(""+age);

         TextView xing_bie = (TextView) findViewById(R.id.xing_bie);//用TextView显示值
           xing_bie.setText(genstr);

         TextView bith_day = (TextView) findViewById(R.id.birthday);//用TextView显示值
        bith_day.setText(bithday);

        TextView de_part = (TextView) findViewById(R.id.depart);//用TextView显示值
        de_part.setText(depart);
//
        TextView zhi_cheng = (TextView) findViewById(R.id.zhicheng);//用TextView显示值
         zhi_cheng.setText(zhicheng);

        TextView telphone = (TextView) findViewById(R.id.tel);//用TextView显示值
        telphone.setText(tel);

      }
cursor.close();



    }



}

