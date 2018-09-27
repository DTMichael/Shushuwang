package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.administrator.shushu1.LoginActivity.dengluid;


public class Xiumima extends AppCompatActivity {
    private EditText xinmima;
    private EditText quedingmima;
    //    private EditText userpassword2Edit;
    //    private EditText shopnameEdit;
    private Button queding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiumima);
        xinmima = (EditText) findViewById(R.id.xinmima);
        quedingmima = (EditText) findViewById(R.id.quedingmima);
//        userpassword2Edit = (EditText) findViewById(R.id.userpassword2);
//        shopnameEdit = (EditText) findViewById(R.id.shopname);
        queding = (Button) findViewById(R.id.queding);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userxinmima = xinmima.getText().toString();
                String userqueingmima = quedingmima.getText().toString();
//                String userpassword2 = userpassword2Edit.getText().toString();
//                String shopname = shopnameEdit.getText().toString();
                if(userxinmima.equals("")||userqueingmima.equals(""))
                {
                    Toast.makeText(getApplication(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                }

//                else if(userpassword2.equals(""))
//                {
//                    Toast.makeText(RegisterActivity.this,"请确认密码！",Toast.LENGTH_SHORT).show();
//                }
//                else if(!userpassword2.equals(userpassword1))
//                {
//                    Toast.makeText(RegisterActivity.this,"密码确认错误！",Toast.LENGTH_SHORT).show();
//                }
//                else if(shopname.equals(""))
//                {
//                    Toast.makeText(RegisterActivity.this,"店铺名不能为空！",Toast.LENGTH_SHORT).show();
//                }
                else
                {
                    if(userxinmima.equals(userqueingmima))
                    {
                       BmobUser user = new BmobUser();
                        user.setPassword(userxinmima);
                        user.update(dengluid, new UpdateListener()
                        {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplication(),"修改成功！",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Xiumima.this,Third.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getApplication(),"修改失败！",Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"两次填写的密码不一致！"+userxinmima+userqueingmima,Toast.LENGTH_SHORT).show();
                    }




                }
            }
        });
        Button back = (Button) findViewById(R.id.fanhui);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Xiumima.this,Third.class);
                startActivity(intent);
            }
        });

    }
}
