package com.example.haihanmingyue.hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        setTitle("用户中心");
//上一个页面传递来的账户信息
        final Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        final String str=bundle.getString("my_code");//getString()返回指定key的值

        //查看我的信息

      Button my_message=(Button) findViewById(R.id.my_message);
        my_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DoctorActivity.this,MyMseeageActivity.class);
                intent.putExtra("my_code", str);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });

//查看预约状态
        Button my_order=(Button) findViewById(R.id.my_order);
        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DoctorActivity.this,WardorderZTActivity.class);
                intent.putExtra("my_code", str);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });
//查看病房
        Button ward=(Button) findViewById(R.id.bf_see);
        ward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DoctorActivity.this,WatchingWardActivity.class);
                intent.putExtra("my_code", str);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);

            }
        });
//修改密码
       Button change=(Button) findViewById(R.id.change_psw) ;
       change.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        Intent intent = new Intent();
        intent.setClass(DoctorActivity.this,ChangepswActivity.class);
        intent.putExtra("my_code", str);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
        startActivity(intent);

    }
});

        Button tui_chu=(Button) findViewById(R.id.tui_chu);
        tui_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent();
                intent_login.setClass(DoctorActivity.this,LoginActivity.class);
                intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                intent_login.putExtra("a",1);
                startActivity(intent_login);
                finish();
            }
        });
    }
}
