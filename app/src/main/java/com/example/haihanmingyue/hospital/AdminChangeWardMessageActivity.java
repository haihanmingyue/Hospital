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

public class AdminChangeWardMessageActivity extends AppCompatActivity {
    private String str;
    private EditText changename;
    private EditText changesex;
    private EditText changetel;
    private EditText changeage;

    private EditText changzhicheng;
    private EditText changedepart;

    private MydatabaseHelper dbHelper;


    public void change(){
        changename =(EditText)findViewById(R.id.xing_ming);
        changesex=(EditText)findViewById(R.id.xing_bie);
        changetel=(EditText)findViewById(R.id.tel);
        changeage=(EditText)findViewById(R.id.ningl);

        changzhicheng=(EditText)findViewById(R.id.zhicheng);
        changedepart=(EditText)findViewById(R.id.depart);

        String Changename=changename.getText().toString();
        String Changesex=changesex.getText().toString();
        String Changetel=changetel.getText().toString();
        String Changeage=changeage.getText().toString();
        String Changzhicheng=changzhicheng.getText().toString();
        String Changdepart=changedepart.getText().toString();

        if(Changename.equals("")||Changesex.equals("")||Changetel.equals("")||Changeage.equals("")||Changzhicheng.equals("")||Changdepart.equals("")){
            Toast.makeText(this,"修改失败！不能有空！",Toast.LENGTH_SHORT).show();
        }

        else {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("depart",Changename);
            values.put("shifouuse",Changesex);
            values.put("lessbed",Changeage);
            values.put("allbed",Changetel);
            values.put("zerenrencode",Changzhicheng);
            values.put("shifouorder",Changdepart);
            db.update("Ward",values,"code=?",new String[]{str});
            Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG).show();

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_ward_message);
        setTitle("修改病房信息");
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        str=bundle.getString("ward_code");//getString()返回指定key的值
        TextView user_name = (TextView) findViewById(R.id.user_name4);//用TextView显示值
        user_name.setText(str);
        String ccc=user_name.getText().toString();


        Button queren=(Button)findViewById(R.id.adddoctor);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminChangeWardMessageActivity.this)
                        .setTitle("信息修改")
                        .setMessage("是否确认修改?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                change();
                                Intent intent = new Intent();
                                intent.setClass(AdminChangeWardMessageActivity.this,AdminWatchWard.class);
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
        String sql = "select * from Ward where code="+ccc;
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()) {


           String depart = cursor.getString(cursor.getColumnIndex("depart"));
            String shifouuse = cursor.getString(cursor.getColumnIndex("shifouuse"));
            int allbed=cursor.getInt(cursor.getColumnIndex("allbed"));
            int lessbed=cursor.getInt(cursor.getColumnIndex("lessbed"));
           String shifouorder = cursor.getString(cursor.getColumnIndex("shifouorder"));
            String zerenrencode = cursor.getString(cursor.getColumnIndex("zerenrencode"));

            TextView xingming = (TextView) findViewById(R.id.xing_ming);//用TextView显示值
            xingming.setText(depart);

            TextView ningl = (TextView) findViewById(R.id.ningl);//用TextView显示值
            ningl.setText(""+lessbed);

            TextView xing_bie = (TextView) findViewById(R.id.xing_bie);//用TextView显示值
            xing_bie.setText(shifouuse);


            TextView de_part = (TextView) findViewById(R.id.depart);//用TextView显示值
            de_part.setText(shifouorder);

            TextView zhi_cheng = (TextView) findViewById(R.id.zhicheng);//用TextView显示值
           zhi_cheng.setText(zerenrencode);

           TextView telphone = (TextView) findViewById(R.id.tel);//用TextView显示值
           telphone.setText(""+allbed);

        }
        cursor.close();
    }
}
