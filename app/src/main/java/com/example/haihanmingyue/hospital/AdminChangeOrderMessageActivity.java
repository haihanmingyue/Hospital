package com.example.haihanmingyue.hospital;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminChangeOrderMessageActivity extends AppCompatActivity {

    private String a;
    private String b;
    private String c;



    private EditText changetel;
    private EditText changeage;
    private EditText changzhicheng;
    private EditText changedepart;

    private MydatabaseHelper dbHelper;


    public void change(){

        changetel=(EditText)findViewById(R.id.tel);
        changeage=(EditText)findViewById(R.id.ningl);
        changzhicheng=(EditText)findViewById(R.id.xing_bie);
        changedepart=(EditText)findViewById(R.id.depart);


        String ChangeNum=changetel.getText().toString();
        String ChangeSj=changeage.getText().toString();
        String ChangeKeshi=changzhicheng.getText().toString();
        String ChangZt=changedepart.getText().toString();

        if(ChangeNum.equals("")||ChangeSj.equals("")||ChangeKeshi.equals("")||ChangZt.equals("")){
            Toast.makeText(this,"修改失败！不能有空！",Toast.LENGTH_SHORT).show();
        }

        else {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("depart",ChangeKeshi);
            values.put("number",ChangeNum);
            values.put("zhuangtai",ChangZt);

            db.update("Ward_order",values,"ward_code=? and doctor_code=? and data=?",new String[]{a,c,b});
            Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG).show();

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_order_message);
        setTitle("修改预约信息");

        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        a=bundle.getString("ward_code");//getString()返回指定key的值
        b=bundle.getString("data");//getString()返回指定key的值
        c=bundle.getString("doctor_code");//getString()返回指定key的值
        TextView user_name = (TextView) findViewById(R.id.user_name4);//用TextView显示值
        user_name.setText(c);
        TextView wardid = (TextView) findViewById(R.id.xing_ming);//用TextView显示值
        wardid.setText(a);
        TextView date = (TextView) findViewById(R.id.ningl);//用TextView显示值
        date.setText(b);
String aa=a;
String bb=b;
String cc=c;


        Button queren=(Button)findViewById(R.id.adddoctor);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminChangeOrderMessageActivity.this)
                        .setTitle("信息修改")
                        .setMessage("是否确认修改?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                change();
                                Intent intent = new Intent();
                                intent.setClass(AdminChangeOrderMessageActivity.this,AdminWatchOrderActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                                startActivity(intent);
                                finish();
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
        });


        SQLiteDatabase db = dbHelper.getWritableDatabase();
       String sql = "select * from Ward_order where ward_code='"+aa+"'and doctor_code="+cc;
//               +"'and doctor_order='"+c+ "'and data="+b;
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()) {


            String depart = cursor.getString(cursor.getColumnIndex("depart"));
            String zhuangtai = cursor.getString(cursor.getColumnIndex("zhuangtai"));
            int number=cursor.getInt(cursor.getColumnIndex("number"));


            TextView xingming = (TextView) findViewById(R.id.xing_bie);//用TextView显示值
            xingming.setText(depart);

            TextView de_part = (TextView) findViewById(R.id.depart);//用TextView显示值
            de_part.setText(zhuangtai);

           TextView telphone = (TextView) findViewById(R.id.tel);//用TextView显示值
            telphone.setText(""+number);

       }
        cursor.close();
    }
}
