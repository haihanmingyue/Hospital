package com.example.haihanmingyue.hospital;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZhuceActivity extends AppCompatActivity {

   private MydatabaseHelper dbHelper;
   private EditText user_name;
   private EditText psw;
   private EditText password_again;

    public boolean zhuce(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from User where code=? ";
        Cursor cursor = db.rawQuery(sql, new String[] {username});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
            return false;
        }

     public void register(){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
         String newname=user_name.getText().toString();
         String password=psw.getText().toString();
         ContentValues values = new ContentValues();
         values.put("code",newname);
         values.put("password",password);
         db.insert("User",null,values);
         db.close();
         values.clear();
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        setTitle("注册");
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name=(EditText)findViewById(R.id.user_name);
                psw=(EditText)findViewById(R.id.psw);
                password_again=(EditText)findViewById(R.id.psw_again);
                String userName=user_name.getText().toString();
                String passWord=psw.getText().toString();
                String passWord_again=password_again.getText().toString();

         if(userName.equals("") || passWord.equals("") || passWord_again.equals(""))   {
           Toast.makeText(ZhuceActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
    }

         else if (zhuce(userName)) {
                   Toast.makeText(ZhuceActivity.this, "该用户名已存在，注册失败！", Toast.LENGTH_SHORT).show();
                   }


                else {
                    if (passWord.equals(passWord_again)) {
                        register();
                        Toast.makeText(ZhuceActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(com.example.haihanmingyue.hospital.ZhuceActivity.this, LoginActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                        startActivity(intent);
                        finish();

                   }else
                        Toast.makeText(ZhuceActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                }

       }
        });


    }
}
