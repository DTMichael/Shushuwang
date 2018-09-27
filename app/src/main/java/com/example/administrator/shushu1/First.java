package com.example.administrator.shushu1;

import android.graphics.Color;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.socketio.callback.StringCallback;

public class First extends AppCompatActivity {

    private List<Allbook> bookList = new ArrayList<>();
    private BookAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText editText;
    private List<Sou> souList=new ArrayList<>();
    private SousuoAdapter sousuoAdapter;
    public   int flag;
    public static int flag1=0;
    private long firstTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Bmob.initialize(this, "ab042970fe7004c50bc341f8f3efe6a4");
        huancun();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE,Color.BLACK);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refreshBook();
            }
        });
        ImageButton B =(ImageButton) findViewById(R.id.imageButton3);
        B.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(flag1==0)
                {
                    Intent intent = new Intent(First.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(First.this, Third.class);
                    startActivity(intent);
                }
            }
        });
        ImageButton A =(ImageButton) findViewById(R.id.imageButton2);
        A.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                    Intent intent = new Intent(First.this, PersonalStoreActivity.class);
                    startActivity(intent);

            }
        });
        ImageButton C =(ImageButton) findViewById(R.id.imageButton5);
        C.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                String name=editText.getText().toString();
                    bookList.clear();
                    souList.clear();
                    sousuoxianshi();
                    huancun1(name);
                if(flag==1)
                {
                    Sou sou = new Sou();
//注意：不能调用gameScore.setObjectId("")方法
                    sou.setName(name);
                    sou.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e)
                        {
                            if (e == null) {


                            } else {

                            }
                        }
                    });
                }

            }
        });

        editText=(EditText)findViewById(R.id.sousuo);
        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //s:变化后的所有字符
                souList.clear();
                if(String.valueOf(s).equals(""))
                {
                    sousuoxianshi();
                    bookList.clear();
                    huancun();
                }
                else
                {
                    sou(String.valueOf(s));
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

        });

    }
    private void huancun()
    {
        String bql = "select * from allbook order by createdAt DESC";
        new BmobQuery<Allbook>().doSQLQuery(bql, new SQLQueryListener<Allbook>() {
            @Override
            public void done(BmobQueryResult<Allbook> result, BmobException e) {
                if (e == null) {
                    List<Allbook> list = (List<Allbook>) result.getResults();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++)
                        {
                            Allbook b = list.get(i);
                            b.setUrl(downloadFile(b.getImage()));
                            bookList.add(b);
                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
                        GridLayoutManager layoutManager = new GridLayoutManager(First.this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new BookAdapter(bookList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        final File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener()
        {


            @Override
            public void onStart()
            {

            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null)
                {


                }
                else
                {

                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
            }

        });
        return saveFile.getPath();
    }
    private void refreshBook()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bookList.clear();
                        huancun();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();
    }
    private int huancun1(String name)
    {
        String bql = "select * from allbook where name='"+name+"'order by createdAt DESC";
        new BmobQuery<Allbook>().doSQLQuery(bql, new SQLQueryListener<Allbook>() {

            @Override
            public void done(BmobQueryResult<Allbook> result, BmobException e) {
                if (e == null) {
                    flag=1;
                    List<Allbook> list = (List<Allbook>) result.getResults();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++)
                        {
                            Allbook b = list.get(i);
                            b.setUrl(downloadFile(b.getImage()));
                            bookList.add(b);
                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
                        GridLayoutManager layoutManager = new GridLayoutManager(First.this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new BookAdapter(bookList);
                        recyclerView.setAdapter(adapter);
                    } else
                    {
                        flag=0;
                        Toast.makeText(getApplicationContext(), "没有该书的信息", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(),  "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return flag;
    }
    public void sou(String name)
    {
        souList.clear();
        String bql = "select * from Sou  order by createdAt DESC limit 8";

        new BmobQuery<Sou>().doSQLQuery(bql, new SQLQueryListener<Sou>() {

            @Override
            public void done(BmobQueryResult<Sou> result, BmobException e) {
                if (e == null) {
                    List<Sou> list = (List<Sou>) result.getResults();
                    if (list != null ) {
                        for (int i = 0; i < list.size(); i++)
                        {
                            Sou b = list.get(i);
                           souList.add(b);
                        }
                        sousuoxianshi();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "没有该书的信息", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),  "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void sousuoxianshi()
    {
        sousuoAdapter=new SousuoAdapter(First.this,R.layout.sousuo,souList);
        ListView listView=(ListView)findViewById(R.id.sousuolist);
        listView.setAdapter(sousuoAdapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(First.this,"再按一次退出书书网",Toast.LENGTH_SHORT).show();
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
