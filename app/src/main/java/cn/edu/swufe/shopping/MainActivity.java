package cn.edu.swufe.shopping;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends ListActivity implements Runnable,AdapterView.OnItemClickListener{
    private static int RESULT_LOAD_IMAGE;
    Handler handler;
    Helpersave helpersave;
    Cursor cursor;
    private ImageView image;
    SQLiteDatabase db;
    private ArrayList<HashMap<String, Object>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        //MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listIerms)
        this.setListAdapter(listItemAdapter);
        /**Thread t = new Thread(this);
        t.start();**/

        db = helpersave.getWritableDatabase();
        cursor = db.query("image", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            // byte[] —> Bitmap
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("bit_image"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            image.setImageBitmap(bitmap);
            cursor.close();
            db.close();
            helpersave.close();
        }

        /**handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 7) {;
                    listItemAdapter = new SimpleAdapter(MainActivity.this, listItems,//listItem 数据源)
                            R.layout.activity_listitem,//ListItem 的xml 布局实现
                            new String[]{"goodsname","description", "price", "picture"},
                            new int[]{R.id.goodsname, R.id.description,R.id.price, R.id.picture}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }


        };**/
        getListView().setOnItemClickListener(this);
    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String, Object>>();
        for (int i = 0;i<10;i++){
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("Itemname","name: "+i);//标题文字
            map.put("Itemintro","intro"+i);//详情描述
            map.put("Itemprice","price"+i);
            map.put("Itemphoto",R.drawable.apple);

            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,//listItem 数据源)
                R.layout.activity_listitem,//ListItem 的xml 布局实现
                new String[]{"Itemname","Itemintro", "Itemprice", "Itemphoto"},
                new int[]{R.id.goodsname,R.id.description,R.id.price, R.id.picture}
        );
    }
    @Override
    public void run() {
        List<HashMap<String,Object>> retlist = new ArrayList<HashMap<String, Object>>();
        Products products = new Products(MainActivity.this);
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
        Object image1 =R.drawable.apple;
        for (ProductItem productItem : products.listAll()) {
            String name = productItem.getName();
            String price = productItem.getPrice();
            String intro = productItem.getIntro();
            String picture = productItem.getPhoto();

            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("Itemname",name);
            map.put("Itemintro",intro);
            map.put("Itemprice",price);
            map.put("Itemphoto",image1);
            retlist.add(map);
            Log.i("db :", name+price+intro+picture);
        }

        /**Message msg = handler.obtainMessage(7);
        msg.obj = retlist;
        handler.sendMessage(msg);

        Log.i("thread","sendMessage.....");**/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
