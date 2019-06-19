package cn.edu.swufe.shopping;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddProduct extends AppCompatActivity {
    private final String TAG="addproduct";
    private RadioGroup type;
    Cursor cursor;
    SQLiteDatabase db;
    Helpersave helpersave;
    private RadioButton fruit;
    private RadioButton sx;
    private RadioButton ls;
    private ImageButton addProduct;
    private EditText productname;
    private EditText productintro;
    private ImageButton productphoto;
    private EditText productprice;
    private static int RESULT_LOAD_IMAGE;
    String str; //存放点击的按钮的值


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        findViews();

        productphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //一个重定向打开系统图库
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }

        });
        //提交按钮的点击事件
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pro_name=productname.getText().toString().trim();
                String pro_intro=productintro.getText().toString().trim();
                String pro_price=productprice.getText().toString().trim();
                Log.i(TAG,pro_name+"_"+pro_intro+"_"+pro_price);
                Products products=new Products(AddProduct.this);
                ProductItem productItem=new ProductItem();
                productItem.setName(pro_name);
                productItem.setIntro(pro_intro);
                productItem.setPrice(pro_price);
                products.addproduct(productItem);
                for (int i = 0;i<type.getChildCount();i++){
                    RadioButton r = (RadioButton)type.getChildAt(i);//根据索引获取单选按钮
                    if (r.isChecked()){ //判断按钮是否被选中
                        str = r.getText().toString().trim();//获取被选中的单选按钮的值
                        productItem.setType(str);
                        break;
                    }
                }
                Toast.makeText(AddProduct.this, "添加商品成功",Toast.LENGTH_LONG).show();
                productname.setText("");
                productprice.setText("");
                productintro.setText("");
            }
        });
        }

    private void findViews() {
        productname=(EditText) findViewById(R.id.productname);
        productintro=(EditText) findViewById(R.id.productintro);
        productphoto=(ImageButton) findViewById(R.id.productphoto);
        productprice=(EditText)findViewById(R.id.productprice);
        type = (RadioGroup) findViewById(R.id.id_radiogroup);
        fruit = (RadioButton) findViewById(R.id.fruit);
        sx = (RadioButton) findViewById(R.id.sx);
        ls = (RadioButton) findViewById(R.id.ls);
        addProduct = (ImageButton) findViewById(R.id.btn_addproduct);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Log.d(TAG, "已经得到bitmap");

            //Image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            //从手机选择图片插入数据库

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            byte[] by = bitmapToBytes(bitmap);
            //cursor = db.query("image", null, null, null, null, null, null);
            ContentValues values = new ContentValues();
            values.put("image_id", 17);
            values.put("bit_image", by);
            db = helpersave.getWritableDatabase();
            db.insert("image", null, values);//插入数据
            db.close();
            helpersave.close();
        }

    }




    public static byte[] bitmapToBytes(Bitmap bitmap){
        if (bitmap == null) {
            return null;
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);//除了PNG还有很多常见格式，如jpeg等。
        return os.toByteArray();
    }

}
