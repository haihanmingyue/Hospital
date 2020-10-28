package com.example.haihanmingyue.hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("管理员界面");
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        final String str=bundle.getString("my_code");//getString()返回指定key的值

        Button doctormessage=(Button) findViewById(R.id.doctor_message) ;
        doctormessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(AdminActivity.this,AdminWatchDoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });

        //yyxx
        Button yyxx=(Button) findViewById(R.id.ward_order_message) ;
        yyxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(AdminActivity.this,AdminWatchOrderActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });

        Button wardmessage=(Button) findViewById(R.id.ward_message) ;
        wardmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(AdminActivity.this,AdminWatchWard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });
        //修改密码
        Button change=(Button) findViewById(R.id.change_admin_password) ;
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(AdminActivity.this,ChangeAdminPassActivity.class);
                intent.putExtra("my_code", str);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });

//退出
        Button tui_chu=(Button) findViewById(R.id.admin_tuichu);
        tui_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent();
                intent_login.setClass(AdminActivity.this,LoginActivity.class);
                intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent_login);
                finish();
            }
        });
    }
}
