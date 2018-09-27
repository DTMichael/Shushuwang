package com.example.administrator.shushu1;

/**
 * Created by Administrator on 2017/6/12.
 */
import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.List;

public class mybookListadapter extends ArrayAdapter<Allbook> {
    private int resourceID;
    public  mybookListadapter(Context context,int textViewResourceId,List<Allbook> objects){
        super(context,textViewResourceId,objects);
        resourceID=textViewResourceId;
    }
    public Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){

        final Allbook book=getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.bookImage=(ImageView)view.findViewById(R.id.mybook_image);
            viewHolder.bookName=(TextView)view.findViewById(R.id.mybook_name);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //根据表中图片的url地址来得到图片（Bitmap类型）
                final Bitmap bitmap=getbitmap(book.getImage().getFileUrl());
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                // 设置想要的大小
                int newWidth = 360;
                int newHeight = 480;
                // 计算缩放比例
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 得到新的图片
                final Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                viewHolder.bookImage.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("*********************************");
                        viewHolder.bookImage.setImageBitmap(newbm);//将图片放到视图中去
                    }
                });
            }
        }).start();
        viewHolder.bookName.setText(book.getName());
        return view;
    }
    class ViewHolder{
        ImageView bookImage;
        TextView bookName;
    }
}
