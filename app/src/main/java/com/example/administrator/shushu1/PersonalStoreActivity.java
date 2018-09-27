package com.example.administrator.shushu1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import static com.example.administrator.shushu1.First.flag1;
import static com.example.administrator.shushu1.LoginActivity.username;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;


public class PersonalStoreActivity extends AppCompatActivity {
    private List<Allbook> bookList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private long firstTime=0;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_store);
        if(flag1==0)
        {
            Toast.makeText(PersonalStoreActivity.this, "请登录！", Toast.LENGTH_LONG).show();
            Intent ds=new Intent(PersonalStoreActivity.this,LoginActivity.class);
            startActivity(ds);
        }
        else {
            initBooks(username);
        }
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        ListView Listview_book_xinxi=(ListView) findViewById(R.id.listView);
        Listview_book_xinxi.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent int_book_xinxi=new Intent(PersonalStoreActivity.this,BookInfo.class);
                Allbook allbook =bookList.get(position);
                int_book_xinxi.putExtra(BookInfo.bookname_f,allbook.getName());
                int_book_xinxi.putExtra(BookInfo.bookprice_f,allbook.getMoney()+"");
                int_book_xinxi.putExtra(BookInfo.newrank_f,allbook.getNewrank()+"");
                int_book_xinxi.putExtra(BookInfo.objectid_f,allbook.getObjectId());
                startActivity(int_book_xinxi);
            }
        });
        ImageButton A =(ImageButton) findViewById(R.id.imageButton4);
        A.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent intent = new Intent(PersonalStoreActivity.this, First.class);
                startActivity(intent);

            }
        });
        ImageButton B =(ImageButton) findViewById(R.id.imageButton7);
        B.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(flag1==0)
                {
                    Intent intent = new Intent(PersonalStoreActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(PersonalStoreActivity.this, Third.class);
                    startActivity(intent);
                }
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_refresh1);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }

    private void initBooks(String username){
        Bmob.initialize(this,"ab042970fe7004c50bc341f8f3efe6a4");
        selectAll(username);
    }

    private void selectAll(String username){
        String bql="select * from allbook where from='"+username+"'";
        BmobQuery<Allbook> query=new BmobQuery<>();
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<Allbook>(){
            @Override
            public void done(BmobQueryResult<Allbook> result, BmobException e) {
                if(e ==null){
                    List<Allbook> list =result.getResults();
                    if(list!=null && list.size()>0){
                        int l=list.size();
                        for(int i=0;i<l;i++) {
                            bookList.add(list.get(i));
                            //Toast.makeText(PersonalStoreActivity.this, "查询数据成功"+list.get(i).getPicture_1().getFileUrl()+" "+list.get(i).getBookname(), Toast.LENGTH_LONG).show();

                        }
                        mybookListadapter adapter=new mybookListadapter(PersonalStoreActivity.this,R.layout.book_item,bookList);
                        ListView listView=(ListView)findViewById(R.id.listView);
                        listView.setAdapter(adapter);



                    }
                    else{
                        bookList=null;
                        Log.i("admin", "查询成功，无数据返回");
                        Toast.makeText(PersonalStoreActivity.this, "您还没有添加图书！", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    bookList=null;
                    Log.i("admin", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                    Toast.makeText(PersonalStoreActivity.this, "查询数据失败"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addbook,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent_add=new Intent(PersonalStoreActivity.this,AddBookActivity.class);

        switch(item.getItemId()){
            case R.id.add_item:
                startActivity(intent_add);

                break;
            default:

        }
        return true;
    }

    public void refresh(){
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
                        initBooks(username);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        }).start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(PersonalStoreActivity.this,"再按一次退出程序书书网",Toast.LENGTH_SHORT).show();
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