package com.example.administrator.shushu1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.shushu1.R;

import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/5/12.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<Allbook> mBookList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookIamge;
        TextView bookName;
        public ViewHolder(View view)
        {
            super(view);
            cardView=(CardView) view;
            bookIamge=(ImageView) view.findViewById(R.id.book_image);
            bookName=(TextView) view.findViewById(R.id.book_name);
        }
    }
    public BookAdapter(List<Allbook> bookList)
    {
        mBookList=bookList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.book,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position=holder.getAdapterPosition();
                Allbook allbook =mBookList.get(position);
                Intent intent=new Intent(mContext,Bookxinxi.class);
                intent.putExtra(Bookxinxi.Bookname,allbook.getName());
                intent.putExtra(Bookxinxi.Bookfrom,allbook.getFrom());
                intent.putExtra(Bookxinxi.Bookurl,allbook.getUrl());
                if(allbook.getLianxi()==null) {
                    intent.putExtra(Bookxinxi.Lianxi,"无");
                }
                else
                {
                    intent.putExtra(Bookxinxi.Lianxi,allbook.getLianxi());
                }

                intent.putExtra(Bookxinxi.Money,String.valueOf(allbook.getMoney()));

                intent.putExtra(Bookxinxi.Xinjiu,String.valueOf(allbook.getNewrank()));

                if(allbook.getWeixin()==null) {
                    intent.putExtra(Bookxinxi.Weixin, "无");
                }
                else
                {
                    intent.putExtra(Bookxinxi.Weixin, allbook.getWeixin());
                }
                if(allbook.getQq()==null) {
                    intent.putExtra(Bookxinxi.Weixin, "无");
                }
                else
                {
                    intent.putExtra(Bookxinxi.QQ,allbook.getQq());
                }


                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Allbook book=mBookList.get(position);
        holder.bookName.setText(book.getName());
        Glide.with(mContext).load(book.getUrl()).into(holder.bookIamge);
    }
    @Override
    public int getItemCount()
    {
        return mBookList.size();
    }
}
;