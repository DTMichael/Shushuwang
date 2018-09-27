package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.administrator.shushu1.First.flag1;
import static com.example.administrator.shushu1.LoginActivity.username;

public class Yonghufankui extends AppCompatActivity {
    private EditText editText;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghufankui);
        editText=(EditText)findViewById(R.id.yonghufankui);
        button1=(Button)findViewById(R.id.button6);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Yonghufankui.this, Third.class);
                String sss=editText.getText().toString();
                if(editText.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(), "请勿提交空数据！！！", Toast.LENGTH_SHORT).show();

                }
                else {

                    Fankui xinxi = new Fankui();
                    xinxi.setName(username);
                    xinxi.setXinxi(sss);
                    xinxi.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null)
                            {
                                Toast.makeText(getApplicationContext(), "感谢您的反馈", Toast.LENGTH_SHORT).show();

                            }
                            else {

                            }
                        }
                    });

                    startActivity(intent);
                }
            }
        });
        button2=(Button)findViewById(R.id.button8);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Yonghufankui.this, Third.class);
                startActivity(intent);

            }
        });


    }
}
