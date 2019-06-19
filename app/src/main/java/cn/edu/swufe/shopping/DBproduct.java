package cn.edu.swufe.shopping;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBproduct extends SQLiteOpenHelper {
    public static final String DBname = "user.db";
    public static final String TBname = "product";
    static int dbVersion=1;
    public DBproduct(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    public DBproduct(Context context) {
        super(context, DBname, null, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql=("create TABLE " + TBname + "(number integer primary key AUTOINCREMENT,name varchar(20),price varchar(20), intro varchar(20),photo blob not null,type varchar(20)");
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
