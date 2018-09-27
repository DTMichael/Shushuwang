package com.example.administrator.shushu1;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import a.We;
import cn.bmob.v3.b.From;

public class Bookxinxi extends AppCompatActivity {
    public static final String Bookname="book_name";
    public static final String Bookurl="bookurl";
    public static final String Bookfrom="bookfrom";
    public static final String Lianxi="lianxi";
    public static final String Money="money";
    public static final String Xinjiu="0";
    public static final String Weixin="0";
    public static final String QQ="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookxinxi);
        Intent intent=getIntent();

        String bookname=intent.getStringExtra(Bookname);
        String lianxi=intent.getStringExtra(Lianxi);

        String url=intent.getStringExtra(Bookurl);
        String money=intent.getStringExtra(Money);
        String from=intent.getStringExtra(Bookfrom);
        String xinjiu11=intent.getStringExtra(Xinjiu);
        String weixin=intent.getStringExtra(Weixin);

        String qq=intent.getStringExtra(QQ);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collspsing_toolbar);

        ImageView bookImageView=(ImageView) findViewById(R.id.fruit_image_view);
        TextView bookContentText =(TextView) findViewById(R.id.fruit_content_text);
        TextView bookContentText1 =(TextView) findViewById(R.id.fruit_content_text1);
        TextView bookContentText2 =(TextView) findViewById(R.id.fruit_content_text2);
        TextView bookContentText3 =(TextView) findViewById(R.id.fruit_content_text3);
        TextView bookContentText4 =(TextView) findViewById(R.id.fruit_content_text4);
        TextView bookContentText5 =(TextView) findViewById(R.id.fruit_content_text5);
        TextView bookContentText6 =(TextView) findViewById(R.id.bookxinjiu);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(bookname);
        Glide.with(this).load(url).into(bookImageView);
        bookContentText.setText("书名："+bookname);
        bookContentText1.setText("价格："+money+"¥");
        bookContentText2.setText("手机："+lianxi);
        bookContentText3.setText("卖家："+from);
        bookContentText4.setText("卖家qq："+weixin);
        bookContentText5.setText("卖家微信："+qq);
        bookContentText6.setText("新旧程度："+xinjiu11+"成新");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
