package com.example.administrator.shushu1;


import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    private EditText useraccountEdit;
    private EditText userpassword1Edit;
    //    private EditText userpassword2Edit;
    private EditText phoneEdit;
    //    private EditText shopnameEdit;
    private Button quicklyregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        useraccountEdit = (EditText) findViewById(R.id.useraccount);
        userpassword1Edit = (EditText) findViewById(R.id.userpassword1);
//        userpassword2Edit = (EditText) findViewById(R.id.userpassword2);
        phoneEdit = (EditText) findViewById(R.id.phone);
//        shopnameEdit = (EditText) findViewById(R.id.shopname);
        quicklyregister = (Button) findViewById(R.id.quicklyregister);
        quicklyregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String useraccount = useraccountEdit.getText().toString();
                String userpassword1 = userpassword1Edit.getText().toString();
//                String userpassword2 = userpassword2Edit.getText().toString();
                String phone = phoneEdit.getText().toString();
//                String shopname = shopnameEdit.getText().toString();
                if(useraccount.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                }
                else if(userpassword1.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }
//                else if(userpassword2.equals(""))
//                {
//                    Toast.makeText(RegisterActivity.this,"请确认密码！",Toast.LENGTH_SHORT).show();
//                }
//                else if(!userpassword2.equals(userpassword1))
//                {
//                    Toast.makeText(RegisterActivity.this,"密码确认错误！",Toast.LENGTH_SHORT).show();
//                }
                else if(phone.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
                }
//                else if(shopname.equals(""))
//                {
//                    Toast.makeText(RegisterActivity.this,"店铺名不能为空！",Toast.LENGTH_SHORT).show();
//                }
                else
                {
                   final String name = useraccountEdit.getText().toString();
                    final String password = userpassword1Edit.getText().toString();
//                String userpassword2 = userpassword2Edit.getText().toString();
                    final  String shouji = phoneEdit.getText().toString();
//
                    BmobUser user = new BmobUser();
                    user.setUsername(useraccount);
                    user.setPassword(userpassword1);
                    user.setMobilePhoneNumber(phone);
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser,BmobException e) {
                            if(e==null)
                            {
                                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                users users1 = new users();
                                users1.setUsername(name);
                                users1.setPassword(password);
                                users1.setMobilePhoneNumber(shouji);
                                users1.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId,BmobException e) {
                                        if(e==null){

                                        }else{

                                        }
                                    }
                                });
                                RegisterActivity.this.finish();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"注册失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }
            }
        });
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}