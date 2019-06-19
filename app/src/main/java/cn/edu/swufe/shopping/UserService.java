package cn.edu.swufe.shopping;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.edu.swufe.shopping.User;

public class UserService {
    private DBHelper dbHelper;
    public UserService(Context context){
        dbHelper=new DBHelper(context);
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    public boolean register(User user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(username,password,address,phone,realname) values(?,?,?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getAddress(),user.getPhone(),user.getRealname()};
        try {
            sdb.execSQL(sql, obj);
        }catch(Exception e){
            Log.e("ERRORs", "register: " + e.toString() );
        }
        return true;
    }
}
