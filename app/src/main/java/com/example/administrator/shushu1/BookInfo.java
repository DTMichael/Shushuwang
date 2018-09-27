package com.example.administrator.shushu1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zhuyi on 2017/6/12.
 */

public class BookInfo extends AppCompatActivity
{
    public static final String bookname_f="book_name";
    public static final String bookprice_f="book_price";
    public static final String newrank_f="newrank";
    public static final String objectid_f="asd";
    private static String objid="12";
    public boolean ifInt(String x){
        int len=x.length();
        if(len>=3){
            return false;
        }
        boolean flag=true;
        for(int i=0;i<len;i++){
            if(x.charAt(i)>'9'||x.charAt(i)<='0'){
                flag=false;
                break;
            }
        }
        return flag;
    }
    public boolean ifRankLegal(String x){
        boolean flag=true;
        if(ifInt(x)){
            int k=Integer.parseInt(x);
            if(k>10){
                flag=false;
            }
        }
        else{
            flag=false;
        }
        return flag;
    }

    private void deleteBook(String objid){
        Allbook newbook=new Allbook();
        newbook.setObjectId(objid);
        newbook.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(BookInfo.this,"成功删除这本书！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(BookInfo.this,PersonalStoreActivity.class);
                    startActivity(intent);
                }
                else{

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Intent intent=getIntent();

        String bookname=intent.getStringExtra(bookname_f);
        String bookprice=intent.getStringExtra(bookprice_f);
        final String newrank=intent.getStringExtra(newrank_f);
        objid=intent.getStringExtra(objectid_f);


        TextView bookname_vi=(TextView) findViewById(R.id.bookname_zhu);
        TextView bookprice_vi=(TextView) findViewById(R.id.bookprice_zhu);
        TextView newrank_vi=(TextView) findViewById(R.id.newrank_zhu);


        bookname_vi.setText(bookname);
        bookprice_vi.setText(bookprice);
        newrank_vi.setText(newrank);
        Toast.makeText(BookInfo.this,"书名:"+ bookname, Toast.LENGTH_LONG).show();

        Button back=(Button) findViewById(R.id.back_personal);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookInfo.this,PersonalStoreActivity.class);
                startActivity(intent);
            }
        });

        Button Delete=(Button)findViewById(R.id.delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BookInfo.this)
                        .setMessage("确定要删除这本书吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteBook(objid);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        Button upload_in=(Button) findViewById(R.id.upload_info);
        upload_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent int_book_xinxi=new Intent(bookinfo.this,Upload_new.class);
                EditText up_bookname=(EditText)findViewById(R.id.bookname_zhu);
                EditText up_bookprice=(EditText)findViewById(R.id.bookprice_zhu);
                EditText up_booknewrank=(EditText)findViewById(R.id.newrank_zhu);



                if(up_bookname.getText().length()==0||up_bookprice.getText().length()==0||up_booknewrank.getText().length()==0)
                {
                    Log.i("bmob","数据错误");
                    new AlertDialog.Builder(BookInfo.this)
                            .setTitle("警告")
                            .setMessage("数据不能为空！")
                            .setPositiveButton("确定", null)
                            .show();
                }
                else {
                    String bookname = up_bookname.getText().toString();
                    String bookprice = up_bookprice.getText().toString();
                    String booknewrank = up_booknewrank.getText().toString();

                    Pattern pa = Pattern.compile("[0-9]*");
                    Matcher m_bookprice = pa.matcher(bookprice);
                    //Matcher m_newrank=pa.matcher(newrank);
                    if (!m_bookprice.matches()) {
                        Log.i("bmob", "数据错误");
                        new AlertDialog.Builder(BookInfo.this)
                                .setTitle("警告")
                                .setMessage("请正确输入价格（数字）！")
                                .setPositiveButton("确定", null)
                                .show();
                    }
                    else if(!ifRankLegal(booknewrank))
                    {
                        Log.i("bmob", "数据错误");
                        new AlertDialog.Builder(BookInfo.this)
                                .setTitle("警告")
                                .setMessage("请正确输入新旧程度（数字1-10）！")
                                .setPositiveButton("确定", null)
                                .show();
                    }
                    else {

                        Allbook newallbook=new Allbook();
                        newallbook.setName(bookname);
                        newallbook.setNewrank(Integer.parseInt(booknewrank));
                        newallbook.setMoney(Integer.parseInt(bookprice));

                        newallbook.update(objid, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "更新成功");
                                    new AlertDialog.Builder(BookInfo.this)
                                            .setTitle("提示")
                                            .setMessage("修改成功！")
                                            .setPositiveButton("确定", null)
                                            .show();
                                } else {
                                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                    new AlertDialog.Builder(BookInfo.this)
                                            .setTitle("提示")
                                            .setMessage("修改失败！")
                                            .setPositiveButton("确定", null)
                                            .show();
                                }
                            }
                        });
                    }
                }


            }
        });


    }

}
