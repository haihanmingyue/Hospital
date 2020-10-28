package com.example.haihanmingyue.hospital;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchingWardActivity extends AppCompatActivity {

    private String str;
    private MydatabaseHelper dbHelper;
    private ListView listview = null;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private SimpleAdapter simpleAdapter = null;
    private ImageButton xx1;
    private String ss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watching_ward);
        setTitle("查看病房");



        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        str = bundle.getString("my_code");//getString()返回指定key的值
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
                String l =map.get("allbed");
                String k=map.get("lessbed");
                String f=map.get("shifouorder");
                String g=map.get("zerenrencode");

                final String[] message ={"病房ID："+a+"\n"+"科室："+b+"\n"+"是否使用："+c+"\n"+"床位数："+l+"\n"+"剩余床位数："+k+"\n"+"是否可预约："+f+"\n"+"责任人ID："+g};
                String h= Arrays.toString(message);
                int len = h.length();
                h = h.substring(1, len-1);//去除Arrays.toString(message)输出产生的【】；

                AlertDialog alertDialog = new AlertDialog.Builder(WatchingWardActivity.this)
                        .setTitle("详细信息")
                        .setMessage(h)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .setNegativeButton("预约", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final EditText inputServer = new EditText(WatchingWardActivity.this);
                                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                                AlertDialog.Builder builder = new AlertDialog.Builder(WatchingWardActivity.this);
                                builder.setTitle("请输入预约时间").setView(inputServer)
                                        .setNegativeButton("取消", null);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String s = inputServer.getText().toString();
                                        if(!s.equals(""))
                                        {
                                            final EditText inputServer2 = new EditText(WatchingWardActivity.this);
                                            inputServer2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});

                                            AlertDialog.Builder builder = new AlertDialog.Builder(WatchingWardActivity.this);
                                            builder.setTitle("请输入住人数").setView(inputServer2)
                                                    .setNegativeButton("取消", null);
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                   final int number = Integer.parseInt(inputServer2.getText().toString());

                                                    HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);

                                                    String k=map.get("lessbed");
                                                    int l= Integer.parseInt(k);
                                                    if(l<=0){
                                                        Toast.makeText(WatchingWardActivity.this, "房间无空余床位!", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        if (number > 0) {
                                                            AlertDialog alertDialog = new AlertDialog.Builder(WatchingWardActivity.this)
                                                                    .setTitle("预约")
                                                                    .setMessage("确定预约？")
                                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            HashMap<String, String> map = (HashMap<String, String>) listview.getItemAtPosition(position);
                                                                            String a = map.get("code");
                                                                            String d = map.get("depart");
                                                                            if (xxx(a, s)) {
                                                                                Toast.makeText(WatchingWardActivity.this, "预约已存在！", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                order(a, d, s, number);
                                                                                Toast.makeText(WatchingWardActivity.this, "预约提交成功！", Toast.LENGTH_SHORT).show();
                                                                                finish();
                                                                            }
                                                                        }
                                                                    })
                                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            return;

                                                                        }
                                                                    }).create();
                                                            alertDialog.show();

                                                        } else {
                                                            Toast.makeText(WatchingWardActivity.this, "人数起码有1人！", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                            builder.show();

                                        }
                                        else
                                        {
                                            Toast.makeText(WatchingWardActivity.this,"日期为空",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        }).create();
                alertDialog.show();
            }
        });

    }

//    public void delete(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(DEFAULT_KEYS_DIALER);
//        String a=map.get("code");
//
//
//        db.delete("Ward_order","ward_code=?",new String[]{a});
//    }
//
public boolean xxx(String ward_code,String data) {
    String ccc=str;
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    String sql = "select * from Ward_order where ward_code=? and data=?"+"and doctor_code="+ccc;
    Cursor cursor = db.rawQuery(sql, new String[] {ward_code, data});
    if (cursor.moveToFirst()) {
        cursor.close();
        return true;
    }
    return false;
}

public void order(String a,String d,String data,int v){

    SQLiteDatabase db = dbHelper.getReadableDatabase();
    ContentValues values = new ContentValues();
    values.put("ward_code",a);
    values.put("doctor_code",str);
    values.put("depart",d);
    values.put("data", data);
    values.put("number", v);
    values.put("zhuangtai","预约中");
    db.insert("Ward_order",null,values);
    values.clear();

}


    private List<Map<String, Object>> getData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String code = cursor.getString(1);
            String depart = cursor.getString(2);
            String shifouuse = cursor.getString(3);
            int allbed = cursor.getInt(4);
            int lessbed = cursor.getInt(5);
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
            list.add(map);
        }
        cursor.close();
        return list;
    }


    private List<Map<String, Object>> getData(String s){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from Ward where code like"+"'%"+s+"%'";
        list.clear();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String code = cursor.getString(1);
            String depart = cursor.getString(2);
            String shifouuse = cursor.getString(3);
            int allbed = cursor.getInt(4);
            int lessbed = cursor.getInt(5);
            String shifouorder = cursor.getString(6);
            String zerenrencode = cursor.getString(7);

            //这里同样是查询，只不过把查询到的数据添加到list集合，可以参照ListView的用法
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", code);
            map.put("depart", depart);
            map.put("shifouuse", shifouuse);
            map.put("allbed", allbed+"");
            map.put("lessbed",lessbed+"");
            map.put("shifouorder", shifouorder);
            map.put("zerenrencode", zerenrencode);
            list.add(map);
        }
        cursor.close();
        return list;
    }
}
