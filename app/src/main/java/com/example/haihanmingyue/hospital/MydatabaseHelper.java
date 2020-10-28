package com.example.haihanmingyue.hospital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MydatabaseHelper extends SQLiteOpenHelper {

    public  static final String CREATE_USER = "create table User("+
            "id integer primary key autoincrement,"+
            "code text,"+
           "password text)";

   public  static final String CREATE_ADMIN = "create table Admin("+
            "id integer primary key autoincrement,"+
           "code text,"+
            "password text)";

public  static final String CREATE_DOCTOR= "create table Doctor("+
        "id integer primary key autoincrement,"+
        "code text,"+
        "tel text,"+
        "depart text,"+
        "genstr text,"+
        "birthday text,"+
        "zhicheng text,"+
        "usingid text,"+
        "name text,"+
        "age integer)";

    public  static final String CREATE_WARD= "create table Ward("+
            "id integer primary key autoincrement,"+
            "code text,"+
            "depart text,"+
            "shifouuse text,"+
            "allbed integer,"+
            "lessbed integer,"+
            "shifouorder text,"+
            "zerenrencode text)";


    public  static final String CREATE_WARD_ORDER= "create table Ward_order("+
            "id integer primary key autoincrement,"+
            "doctor_code text,"+
            "ward_code text,"+
            "depart text,"+
            "data text,"+
            "zhuangtai text,"+
            "number integer)";

    private Context mContext;

    public MydatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);
       db.execSQL(CREATE_ADMIN);
        db.execSQL(CREATE_DOCTOR);
        db.execSQL(CREATE_WARD);
        db.execSQL(CREATE_WARD_ORDER);
        Toast.makeText(mContext,"建表成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);//升级数据库

    }
}

