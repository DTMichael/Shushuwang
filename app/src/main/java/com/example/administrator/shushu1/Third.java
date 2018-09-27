package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.administrator.shushu1.First.flag1;

public class Third extends AppCompatActivity {
    private long firstTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ImageButton A =(ImageButton) findViewById(R.id.imageButton9);
        A.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                    Intent intent = new Intent(Third.this, First.class);
                    startActivity(intent);

            }
        });
        ImageButton B1=(ImageButton) findViewById(R.id.imageButton10);
        B1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, PersonalStoreActivity.class);
                startActivity(intent);

            }
        });
        Button B =(Button) findViewById(R.id.button2);
        B.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, Xiumima.class);
                startActivity(intent);
            }
        });
        Button C =(Button) findViewById(R.id.button4);
        C.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, LoginActivity.class);
                flag1=0;
                startActivity(intent);
            }
        });
        Button D =(Button) findViewById(R.id.button);
        D.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, Women.class);
                startActivity(intent);
            }
        });
        Button E =(Button) findViewById(R.id.buttonfan);
        E.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, Yonghufankui.class);
                startActivity(intent);
            }
        });
        Button F =(Button) findViewById(R.id.buttonshe);
        F.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Third.this, Bangding.class);
                startActivity(intent);
            }
        });
        Button G =(Button) findViewById(R.id.button3);
        G.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Toast.makeText(Third.this,"我们会严格保护您的个人隐私",Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(Third.this,"再按一次退出书书网",Toast.LENGTH_SHORT).show();
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
