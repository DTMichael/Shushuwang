package com.example.administrator.shushu1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.shushu1.First;

import java.util.List;

import static com.example.administrator.shushu1.Bangding.yongqq;
import static com.example.administrator.shushu1.Bangding.yongweixin;
import static com.example.administrator.shushu1.First.flag1;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText accountEdit;
    private long firstTime=0;
    private EditText passwordEdit;
    private Button login;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    public static String lianxifangshi="";
    public static String dengluid="";
    public static String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password",false);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = accountEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                if(account.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"用户名为空！",Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"密码为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    BmobUser user1 = new BmobUser();

                    user1.setUsername(account);
                    user1.setPassword(password);
                    user1.login(new SaveListener<BmobUser>() {
                        public void done(BmobUser bmobUser, BmobException e)
                        {
                            if (e == null)
                            {

                                BmobUser user = BmobUser.getCurrentUser();
                                editor = pref.edit();
                                if(rememberPass.isChecked()){
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("account",account);
                                    editor.putString("password",password);
                                }
                                else
                                {
                                    editor.clear();
                                }
                                editor.apply();
                                flag1=1;
                                username=user.getUsername();
                                dengluid=user.getObjectId();
                                cha();
                                lianxifangshi=user.getMobilePhoneNumber();
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, Third.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(LoginActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();


                        }
                    });
                }

//
            }
        });
        Button register1 = (Button) findViewById(R.id.register);
        register1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button back = (Button) findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,First.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onClick(View v) {

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
                        if(list.get(0).getWeixin()==null)
                        {
                            yongweixin="无";
                        }
                        else {
                            yongweixin = list.get(0).getWeixin();
                        }
                        if(list.get(0).getQq()==null)
                        {
                            yongweixin="无";

                        }
                        else {
                            yongqq = list.get(0).getQq();
                        }

                        //}

                    } else {


                    }
                } else {

                }


            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(LoginActivity.this,"再按一次退出程序书书网",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
