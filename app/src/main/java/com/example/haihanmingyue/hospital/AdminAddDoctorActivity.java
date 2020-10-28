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

public class AdminAddDoctorActivity extends AppCompatActivity {
//直接把修改的拿来用

    private EditText changename;
    private EditText changesex;
    private EditText changetel;
    private EditText changeage;
    private EditText changebir;
    private EditText changeusig;
    private EditText changzhicheng;
    private EditText changedepart;
    private EditText changecode;
    private MydatabaseHelper dbHelper;


    public void change(){
        changecode=(EditText)findViewById(R.id.user_name4);
        changename =(EditText)findViewById(R.id.xing_ming);
        changesex=(EditText)findViewById(R.id.xing_bie);
        changetel=(EditText)findViewById(R.id.tel);
        changeage=(EditText)findViewById(R.id.ningl);
        changebir=(EditText)findViewById(R.id.birthday);
        changeusig=(EditText)findViewById(R.id.sybf);
        changzhicheng=(EditText)findViewById(R.id.zhicheng);
        changedepart=(EditText)findViewById(R.id.depart);

        String Changecode=changecode.getText().toString();
        String Changename=changename.getText().toString();
        String Changesex=changesex.getText().toString();
        String Changetel=changetel.getText().toString();
        String Changeage=changeage.getText().toString();
        String Changebir=changebir.getText().toString();
        String Changesybf=changeusig.getText().toString();
        String Changzhicheng=changzhicheng.getText().toString();
        String Changdepart=changedepart.getText().toString();

        if(Changecode.equals("")||Changename.equals("")||Changesex.equals("")||Changetel.equals("")||Changeage.equals("")||Changebir.equals("")||Changzhicheng.equals("")||Changdepart.equals("")){
            Toast.makeText(this,"修改失败！除使用中的病房外不能有空！",Toast.LENGTH_SHORT).show();
        }

        else {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("code",Changecode);
            values.put("name",Changename);
            values.put("genstr",Changesex);
            values.put("age",Changeage);
            values.put("birthday",Changebir);
            values.put("tel",Changetel);
            values.put("usingid",Changesybf);
            values.put("zhicheng",Changzhicheng);
            values.put("depart",Changdepart);
            db.insert("Doctor",null,values);
            values.clear();
            Toast.makeText(this,"添加成功！",Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(AdminAddDoctorActivity.this, AdminWatchDoctorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
            startActivity(intent);
            finish();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_doctor);
        setTitle("添加医生");
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);


        Button queren = (Button) findViewById(R.id.adddoctor);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminAddDoctorActivity.this)
                        .setTitle("医生添加")
                        .setMessage("是否确认添加?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                change();

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


    }

}
