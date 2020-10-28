package com.example.haihanmingyue.hospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminWatchWard extends AppCompatActivity {

    private MydatabaseHelper dbHelper;
    private ListView listview = null;
    private List<Map<String, Object>> wardlist = new ArrayList<Map<String, Object>>();
    private SimpleAdapter simpleAdapter = null;
    private ImageButton xx1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_watch_ward);
        setTitle("查看医生");


        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        listview = (ListView) findViewById(android.R.id.list);

        final EditText eSearch = (EditText) findViewById(R.id.etSearch);
        final String s=eSearch.getText().toString();
        Button add=(Button)findViewById(R.id.add) ;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AdminWatchWard.this, AdminAddWardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        xx1 = findViewById(R.id.xx);
        xx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eSearch.setText("");
            }
        });
        eSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                String  wz = eSearch.getText().toString();
                if (wz.length()==0){
                    xx1.setVisibility(View.GONE);
                }else{
                    xx1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                getData(s.toString());
                simpleAdapter.notifyDataSetChanged();
            }
        });

//适配器添加查询结果，并加到ListView中显示
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.list,
                new String[]{"code", "depart", "shifouuse", "shifouorder"}, new int[]{
                R.id.nysid, R.id.bfid, R.id.sj, R.id.yyzt});
        listview.setAdapter(simpleAdapter);
        //



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
                listview = (ListView) findViewById(android.R.id.list);
                String a=map.get("code");
                String b=map.get("depart");
                String c=map.get("shifouuse");
                String d=map.get("allbed");
                String e=map.get("lessbed");
                String f=map.get("shifouorder");
                String g=map.get("zerenrencode");

                final String[] message ={"病房ID："+a+"\n"+"科室："+b+"\n"+"是否使用："+c+"\n"+"床位数："+d+"\n"+"剩余床位数："+e+"\n"+"是否可预约："+f+"\n"+"责任人ID："+g};
                String o= Arrays.toString(message);
                int len = o.length();
                o = o.substring(1, len-1);//去除Arrays.toString(message)输出产生的【】；

                AlertDialog alertDialog = new AlertDialog.Builder(AdminWatchWard.this)
                        .setTitle("详细信息")
                        .setMessage(o)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                AlertDialog alertDialog = new AlertDialog.Builder(AdminWatchWard.this)
                                        .setTitle("删除")
                                        .setMessage("确定删除？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
                                                String a=map.get("code");
                                                delete(a);
                                                wardlist.remove(position);
                                                simpleAdapter.notifyDataSetChanged();
                                                return;
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                return;



                                            }
                                        }).create();
                                alertDialog.show();



                            }
                        })
                        .setNeutralButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
                                String str=map.get("code");
                                Intent intent = new Intent();
                                intent.setClass(AdminWatchWard.this,AdminChangeWardMessageActivity.class);
                                intent.putExtra("ward_code", str);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                                startActivity(intent);
                                finish();
                            }
                        }).create();
                alertDialog.show();
            }
        });

    }

    public void delete(String code){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.delete("Ward","code=?",new String[]{code});
    }


//    public void order(String a,String d,String data){
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("ward_code",a);
//        values.put("doctor_code",str);
//        values.put("depart",d);
//        values.put("data", data);
//        values.put("zhuangtai","预约中");
//        db.insert("Ward_order",null,values);
//        values.clear();
//
//    }


    private List<Map<String, Object>> getData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward";
        Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String code = cursor.getString(1);
                String depart = cursor.getString(2);
                String shifouuse = cursor.getString(3);
                int allbed=cursor.getInt(4);
                int lessbed=cursor.getInt(5);
                String shifouorder = cursor.getString(6);
                String zerenrencode = cursor.getString(7);
                //这里同样是查询，只不过把查询到的数据添加到list集合，可以参照ListView的用法
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", code);
                map.put("depart", depart);
                map.put("shifouuse", shifouuse);
                map.put("allbed", allbed+"");
                map.put("lessbed", lessbed+"");
                map.put("shifouorder", shifouorder);
                map.put("zerenrencode", zerenrencode);

                wardlist.add(map);
            }
            cursor.close();
            return wardlist;
    }


    private List<Map<String, Object>> getData(String s){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward where code like"+"'%"+s+"%'";

        wardlist.clear();

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String code = cursor.getString(1);
            String depart = cursor.getString(2);
            String shifouuse = cursor.getString(3);
           int allbed=cursor.getInt(4);
            int lessbed=cursor.getInt(5);
            String shifouorder = cursor.getString(6);
            String zerenrencode = cursor.getString(7);
            //这里同样是查询，只不过把查询到的数据添加到list集合，可以参照ListView的用法
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", code);
            map.put("depart", depart);
            map.put("shifouuse", shifouuse);
            map.put("allbed", allbed+"");
            map.put("lessbed", lessbed+"");
            map.put("shifouorder", shifouorder);
            map.put("zerenrencode", zerenrencode);

            wardlist.add(map);
        }
        cursor.close();
        return wardlist;
    }
}
