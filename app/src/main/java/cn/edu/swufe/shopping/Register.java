package cn.edu.swufe.shopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.edu.swufe.shopping.UserService;
public class Register extends AppCompatActivity {

    EditText logname;
    EditText register_password;
    EditText address;
    EditText phone;
    EditText realname;
    ImageButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=logname.getText().toString().trim();
                String pass=register_password.getText().toString().trim();
                String phnumber=phone.getText().toString().trim();
                String addressstr=address.getText().toString().trim();
                String realnamestr=realname.getText().toString().trim();
                Log.i("TAG",name+"_"+pass+"_"+addressstr+"_"+phnumber+"_"+realnamestr);
                UserService uService=new UserService(Register.this);
                User user=new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setAddress(addressstr);
                user.setPhone(phnumber);
                user.setRealname(realnamestr);
                uService.register(user);
                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void findViews() {
        logname=(EditText) findViewById(R.id.logname);
        register_password=(EditText) findViewById(R.id.register_password);
        phone=(EditText) findViewById(R.id.phone);
        address=(EditText)findViewById(R.id.address);
        realname=(EditText)findViewById(R.id.realname);
        btn_register=(ImageButton) findViewById(R.id.btn_register);
    }

}

