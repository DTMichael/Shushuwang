package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.administrator.shushu1.LoginActivity.username;
import static com.example.administrator.shushu1.R.drawable.user;

public class Bangding extends AppCompatActivity {
    private EditText weixin;
    private EditText qq;
    private EditText shouji;
    private Button bangding;
    private Button fanhui;
    public static String id;
    public static String yongweixin="";
    public static String yongqq="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangding);
        weixin = (EditText) findViewById(R.id.weixin);
        qq = (EditText) findViewById(R.id.qq);
        shouji = (EditText) findViewById(R.id.shouji);
        bangding = (Button) findViewById(R.id.bangding);
        bangding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (weixin.getText().length() == 0 || weixin.getText().length() == 0 || shouji.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "请将信息填写完整！！！", Toast.LENGTH_SHORT).show();
                } else
                {
                    cha();
                    String weixin1 = weixin.getText().toString();
                    String qq1 = qq.getText().toString();
                    String shouji1 = shouji.getText().toString();
                    users user = new users();
                    user.setWeixin(weixin1);
                    user.setQq(qq1);
                    user.setMobilePhoneNumber(shouji1);
                    user.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getApplication(), "绑定成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Bangding.this, Third.class);
                                startActivity(intent);

                            } else {

                            }
                        }

                    });
                }


            }
        });
        fanhui = (Button) findViewById(R.id.backzll);

        fanhui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Bangding.this, Third.class);
                startActivity(intent);

            }
        });
    }

    public void cha() {

        String bql = "select * from users where username='" + username + "'";
        new BmobQuery<users>().doSQLQuery(bql, new SQLQueryListener<users>() {

            @Override
            public void done(BmobQueryResult<users> result, BmobException e) {
                if (e == null) {
                    List<users> list = (List<users>) result.getResults();
                    if (list != null && list.size() > 0) {
                        //for (int i = 0; i < list.size(); i++) {
                            id = list.get(0).getObjectId();
                        //}

                    } else {

                        Toast.makeText(getApplicationContext(), "没有该书的信息", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), e.getMessage() + "查询失败"+username, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
