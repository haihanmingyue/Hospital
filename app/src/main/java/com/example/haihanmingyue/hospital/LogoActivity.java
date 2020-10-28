package com.example.haihanmingyue.hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        Timer timer = new Timer();
        TimerTask timertask = new TimerTask(){
            @Override
public void run() {
// TODO Auto-generated method stub
                Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                startActivity(intent);
                LogoActivity.this.finish();
            }};
        timer.schedule(timertask, 1000);
//先显示一个界面，1000ms后自动跳转
    }
}
