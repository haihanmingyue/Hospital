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
import android.widget.TextView;
import android.widget.Toast;

public class ChangepswActivity extends AppCompatActivity {


 private MydatabaseHelper dbHelper;

    private EditText newpassword;
    private EditText newpsw_again;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepsw);
        dbHelper = new MydatabaseHelper(this, "Hospital.db", null, 1);
        setTitle("修改密码");
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        assert bundle != null;
        str=bundle.getString("my_code");//getString()返回指定key的值
        TextView user_name = (TextView) findViewById(R.id.user_name3);//用TextView显示值
        user_name.setText(str);


        Button xiu_gai =(Button) findViewById(R.id.xiu_gai);
        xiu_gai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpassword=(EditText)findViewById(R.id.new_psw);
                newpsw_again=(EditText)findViewById(R.id.new_psw_again);
                String psw=newpassword.getText().toString();
                String pswagain=newpsw_again.getText().toString();

                if(psw.equals("") || pswagain.equals(""))   {

                    Toast.makeText(ChangepswActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
                }
               else if (change(psw)){

                    Toast.makeText(ChangepswActivity.this, "密码无修改！请勿输入旧密码！", Toast.LENGTH_SHORT).show();

                }else if(!change(psw) && !psw.equals(pswagain)) {
                    Toast.makeText(ChangepswActivity.this, "两次输入密码不相同！", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(ChangepswActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        xiugai();
                        Intent intent = new Intent();
                        intent.setClass(com.example.haihanmingyue.hospital.ChangepswActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }
        });
    }
    public void xiugai() {

        String psw = newpassword.getText().toString();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", psw);
        db.update("User", values, "code=?", new String[]{str});

    }
    public boolean change(String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from User where code="+ str + " and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
