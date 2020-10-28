package com.example.haihanmingyue.hospital;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private MydatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MydatabaseHelper(this,"Hospital.db",null,1);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getWritableDatabase();
            }
        });

        Button addDatabase = (Button) findViewById(R.id.add_database);
        addDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =helper.getWritableDatabase();
                ContentValues values = new ContentValues();

               values.put("code","10002");
               values.put("password","1");
               db.insert("User",null,values);
               values.clear();

                values.put("code","admin");
                values.put("password","admin");
                db.insert("Admin",null,values);
                values.clear();

                values.put("code","10002");
                values.put("tel","11111111111");
                values.put("depart","儿科");
                values.put("name","Alice");
                values.put("zhicheng","院长");
                values.put("usingid","10000001");
                values.put("genstr","女");
                values.put("age",23);
                values.put("birthday","1996-1-1");
                db.insert("Doctor",null,values);
                values.clear();

                values.put("code","1000002");
                values.put("shifouuse","是");
                values.put("depart","儿科");
                values.put("allbed",6);
                values.put("lessbed",6);
                values.put("shifouorder","是");
                values.put("zerenrencode","10001");
                db.insert("Ward",null,values);
                values.clear();



                values.put("doctor_code","10002");
                values.put("ward_code","1000002");
                values.put("data","2019-5-23");
                values.put("number",2);
                values.put("zhuangtai","预约中");
                values.put("depart","儿科");
                db.insert("Ward_order",null,values);
                values.clear();

                values.put("doctor_code","10002");
                values.put("ward_code","1000003");
                values.put("data","2019-5-23");
                values.put("number",3);
                values.put("zhuangtai","预约中");
                values.put("depart","儿科");
                db.insert("Ward_order",null,values);
                values.clear();

                values.put("doctor_code","10002");
                values.put("ward_code","1000004");
                values.put("data","2019-5-23");
                values.put("number",1);
                values.put("zhuangtai","预约中");
                values.put("depart","儿科");
                db.insert("Ward_order",null,values);
                values.clear();

                values.put("doctor_code","10001");
                values.put("ward_code","1000002");
                values.put("data","2019-5-23");
                values.put("number",2);
                values.put("zhuangtai","预约中");
                values.put("depart","内分泌科");
                db.insert("Ward_order",null,values);
                values.clear();





                db.close();
Toast.makeText(MainActivity.this,"succeeded",Toast.LENGTH_LONG).show();

            }
        });

    }
}
