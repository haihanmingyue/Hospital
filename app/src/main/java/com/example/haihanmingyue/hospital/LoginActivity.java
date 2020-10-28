package com.example.haihanmingyue.hospital;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private MydatabaseHelper dbHelper;
    private EditText username;
    private EditText userpassword;
    private Button login;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int b=2;
    private int c=2;



    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from User where code=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


    public void xxxxx( String userName,String passWord){

        userName = username.getText().toString();
        passWord = userpassword.getText().toString();

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("user_id", userName);
        editor.putString("password",passWord);

        editor.commit();

    }

public void xxxxxxxxx (String userName,String passWord ){

    if (login(userName, passWord)) {
        xxxxx(userName, passWord);
        Intent intent = new Intent();
        intent.setClass(com.example.haihanmingyue.hospital.LoginActivity.this, DoctorActivity.class);
        intent.putExtra("my_code", userName);
        startActivity(intent);
        finish();
    }

    }
    public boolean adminlogin(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from Admin where code=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        setTitle("登录");

       int a= this.getIntent().getIntExtra("a",0);
       int d=b-a;
        username = findViewById(R.id.code);
        userpassword = findViewById(R.id.password);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String us = sp.getString("user_id", null);//(key,若无数据需要赋的值)
        String pa = sp.getString("password", null);

//判断是否需要保存登陆状态
if(d==1) {
    editor = sp.edit();
    editor.remove("user_id");
    editor.remove("password");
    editor.commit();
}

else if(c==2 && us!=null && pa!=null) {
    username.setText(us);
    userpassword.setText(pa);
    xxxxxxxxx(us, pa);
}


//普通登陆


        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String passWord = userpassword.getText().toString();


                if (login(userName, passWord)) {

                            Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            xxxxx(userName,passWord);
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, DoctorActivity.class);
                            intent.putExtra("my_code", userName);
                            startActivity(intent);
                            finish();

                        } else if (userName.equals("") || passWord.equals("")) {

                            Toast.makeText(LoginActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "登陆失败,用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }

                    }


//
        });



//管理员登陆
        final Button admin = (Button) findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String AdminuserName = username.getText().toString();
                String AdminpassWord = userpassword.getText().toString();

                if (AdminuserName.equals("") || AdminpassWord.equals("")) {

                    Toast.makeText(LoginActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
                } else if (adminlogin(AdminuserName, AdminpassWord)) {

                    Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, AdminActivity.class);
                    intent.putExtra("my_code", AdminuserName);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "登陆失败,用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        注册
        Button zhuce = (Button) findViewById(R.id.zhuce);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ZhuceActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);


            }
        });


        Button shuju = (Button) findViewById(R.id.shuju);
        shuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);


            }
        });


    }

}
