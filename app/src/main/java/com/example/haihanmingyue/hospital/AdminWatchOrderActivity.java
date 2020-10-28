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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminWatchOrderActivity extends AppCompatActivity {

    private MydatabaseHelper dbHelper;
    private ListView listview = null;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private SimpleAdapter simpleAdapter = null;
    private ImageButton xx1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardorder_zt);
        setTitle("预约状态");


        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        listview = (ListView) findViewById(android.R.id.list);

        final EditText eSearch = (EditText) findViewById(R.id.etSearch);
        final String s=eSearch.getText().toString();


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
                new String[]{"doctor_code", "ward_code", "data", "zhuangtai"}, new int[]{
                R.id.bfid, R.id.nysid, R.id.sj, R.id.yyzt});
        listview.setAdapter(simpleAdapter);
        //



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
                listview = (ListView) findViewById(android.R.id.list);
                String a=map.get("doctor_code");
                String b=map.get("ward_code");
                String c=map.get("data");
                String d=map.get("zhuangtai");
                String e=map.get("depart");
                String g=map.get("number");
                final String[] message ={"医生工号："+a+"\n"+"病房id："+b+"\n"+"入住人数："+g+"\n"+"预约日期："+c+"\n"+"预约状态："+d+"\n"+"科室："+e};
                String f= Arrays.toString(message);
                int len = f.length();
                f = f.substring(1, len-1);//去除Arrays.toString(message)输出产生的【】；

                AlertDialog alertDialog = new AlertDialog.Builder(AdminWatchOrderActivity.this)
                        .setTitle("详细信息")
                        .setMessage(f)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
//                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                AlertDialog alertDialog = new AlertDialog.Builder(AdminWatchOrderActivity.this)
//                                        .setTitle("删除")
//                                        .setMessage("确定删除？")
//                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
//                                                String a=map.get("ward_code");
//                                                String b=map.get("data");
//                                                String c=map.get("doctor_code");
//                                                delete(a,b,c);
//                                                list.remove(position);
//                                                simpleAdapter.notifyDataSetChanged();
//                                                return;
//                                            }
//                                        })
//                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                return;
//
//
//
//                                            }
//                                        }).create();
//                                alertDialog.show();
//
//
//
//                            }
//                        })
                        .setNeutralButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
                                String a=map.get("ward_code");
                                String b=map.get("data");
                                String c=map.get("doctor_code");

                                Intent intent = new Intent();
                                intent.setClass(AdminWatchOrderActivity.this,AdminChangeOrderMessageActivity.class);
                                intent.putExtra("ward_code", a);
                                intent.putExtra("data", b);
                                intent.putExtra("doctor_code", c);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                                startActivity(intent);
                                finish();
                            }
                        }).create();
                alertDialog.show();
            }
        });

    }

//    public void delete(String a,String b,String c){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        db.delete("Ward_order","ward_code=? and data=? and doctor_code=?",new String[]{a,b,c});
//        db.close();
//    }
//

    private List<Map<String, Object>> getData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward_order";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String doctor_code = cursor.getString(1);
            String ward_code = cursor.getString(2);
            String data = cursor.getString(4);
            String depart = cursor.getString(3);
            String zhuangtai = cursor.getString(5);
            int number = cursor.getInt(6);
            //这里同样是查询，只不过把查询到的数据添加到list集合，可以参照ListView的用法
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("doctor_code", doctor_code);
            map.put("ward_code", ward_code);
            map.put("data", data);
            map.put("zhuangtai", zhuangtai);
            map.put("depart", depart);
            map.put("number", number+"");
            list.add(map);
        }
        cursor.close();
        return list;
    }


    private List<Map<String, Object>> getData(String s){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward_order where ward_code like"+"'%"+s+"%'";
        list.clear();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String doctor_code = cursor.getString(1);
            String ward_code = cursor.getString(2);
            String data = cursor.getString(4);
            String depart = cursor.getString(3);
            String zhuangtai = cursor.getString(5);
            int number = cursor.getInt(6);
            //这里同样是查询，只不过把查询到的数据添加到list集合，可以参照ListView的用法
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("doctor_code", doctor_code);
            map.put("ward_code", ward_code);
            map.put("data", data);
            map.put("zhuangtai", zhuangtai);
            map.put("depart", depart);
            map.put("number", number+"");
            list.add(map);
        }
        cursor.close();
        return list;
    }

}
