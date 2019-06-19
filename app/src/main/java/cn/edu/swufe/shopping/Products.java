package cn.edu.swufe.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Products {
    private DBproduct dBproduct;
    private String TBNAME;
    public Products(Context context){
        dBproduct = new DBproduct(context);
        TBNAME = DBproduct.TBname;
    }
    public boolean addproduct(ProductItem productItem){
        SQLiteDatabase sdb=dBproduct.getReadableDatabase();
        String sql="insert into user(name,price,intro,photo,type) values(?,?,?,?,?)";
        Object obj[]={productItem.getName(),productItem.getPrice(),productItem.getIntro(),productItem.getPhoto(),productItem.getType()};
        try {
            sdb.execSQL(sql, obj);
        }catch(Exception e){
            Log.e("ERRORs", "register: " + e.toString() );
        }
        return true;
    }

    public void add(ProductItem item){
        SQLiteDatabase db = dBproduct.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("intro", item.getIntro());
        values.put("type", item.getType());
        values.put("price", item.getPrice());
        values.put("number", item.getNumber());
        values.put("photo", item.getPhoto());
        db.insert(TBNAME,null,values);
        db.close();
    }

    public List<ProductItem> listAll(){
        List<ProductItem> productList = null;
        SQLiteDatabase db = dBproduct.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            productList = new ArrayList<ProductItem>();
            while(cursor.moveToNext()){
                ProductItem item = new ProductItem();
                item.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
                item.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
                item.setName(cursor.getString(cursor.getColumnIndex("name")));
                item.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                item.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));

                productList.add(item);
            }
            cursor.close();
        }
        db.close();
        return productList;
    }

}
