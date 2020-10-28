package com.example.haihanmingyue.hospital;

import android.content.ContentValues;
import android.content.Context;
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

public class ChangeMessageActivity extends AppCompatActivity {
    private String str;
    private EditText changename;
    private EditText changesex;
    private EditText changetel;
    private EditText changeage;
    private EditText changebir;

    private MydatabaseHelper dbHelper;


    public void change(){
         changename =(EditText)findViewById(R.id.xing_ming);
         changesex=(EditText)findViewById(R.id.xing_bie);
         changetel=(EditText)findViewById(R.id.tel);
         changeage=(EditText)findViewById(R.id.ningl);
         changebir=(EditText)findViewById(R.id.birthday);

         String Changename=changename.getText().toString();
         String Changesex=changesex.getText().toString();
         String Changetel=changetel.getText().toString();
         String Changeage=changeage.getText().toString();
         String Changebir=changebir.getText().toString();


if(Changename.equals("")||Changesex.equals("")||Changetel.equals("")||Changeage.equals("")||Changebir.equals("")){
    Toast.makeText(this,"修改失败！不能有空！",Toast.LENGTH_SHORT).show();
}

else {
    SQLiteDatabase db=dbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("name",Changename);
    values.put("genstr",Changesex);
    values.put("age",Changeage);
    values.put("birthday",Changebir);
    values.put("tel",Changetel);
    db.update("Doctor",values,"code=?",new String[]{str});
    Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG).show();

}



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_message);
        setTitle("修改个人信息");
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        str=bundle.getString("my_code");//getString()返回指定key的值
        TextView user_name = (TextView) findViewById(R.id.user_name4);//用TextView显示值
        user_name.setText(str);
        String ccc=user_name.getText().toString();


        Button queren=(Button)findViewById(R.id.xiu_gai_queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ChangeMessageActivity.this)
                        .setTitle("信息修改")
                        .setMessage("是否确认修改?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              change();
                                Intent intent = new Intent();
                                intent.setClass(ChangeMessageActivity.this,MyMseeageActivity.class);
                                intent.putExtra("my_code", str);
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
