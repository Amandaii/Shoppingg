package cn.edu.swufe.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PictureDatabase extends SQLiteOpenHelper {
    //数据库的字段
    public static class PictureColumns implements BaseColumns {
        public static final String PICTURE = "picture";
    }

    private Context mContext;

    //数据库名
    private static final String DATABASE_NAME = "picture.db";
    //数据库版本号
    private static final int DATABASE_Version = 1;
    //表名
    private static final String TABLE_NAME = "picture";

    //创建数据库
    public PictureDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.mContext = context;
    }

    //创建表并初始化表
    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + "(" + BaseColumns._ID
                + " integer primary key autoincrement," + PictureColumns.PICTURE
                + " blob not null);";
        db.execSQL(sql);
    }
    //将转换后的图片存入到数据库中
    public void savePhoto (Bitmap bitmap ) {
        // Drawable drawable = context.getResources().getDrawable(resources); 传入int资源时用
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PictureColumns.PICTURE, getPicture(bitmap));
        // cv.put(PictureColumns.PICTURE, getPicture(drawable)); 传入int资源时用
        db.delete(TABLE_NAME,null,null);//每次设置头像之前清空数据库的数据
        db.insert(TABLE_NAME, null, cv);
    }

    //将drawable转换成可以用来存储的byte[]类型
    private byte[] getPicture(Bitmap bitmap) {
//        if(drawable == null) {
//            return null;
//        }
        if(bitmap == null) {
            return null;
        }
//        BitmapDrawable bd = (BitmapDrawable) drawable;传入int资源时用
//        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }
    //从数据库读取照片
    public ArrayList<Bitmap> getDrawable() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        //查询数据库
        Cursor c = db.rawQuery("select * from picture", null);
        //遍历数据
        if(c != null && c.getCount() != 0) {
            while(c.moveToNext()) {
                //获取数据
                byte[] b = c.getBlob(c.getColumnIndexOrThrow(PictureDatabase.PictureColumns.PICTURE));
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//            Drawable drawable = bitmapDrawable;
                bitmaps.add(bitmap);
            }
        }
        return bitmaps;
    }
    //更新数据库
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

}
