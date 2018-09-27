package com.example.administrator.shushu1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.administrator.shushu1.Bangding.yongqq;
import static com.example.administrator.shushu1.Bangding.yongweixin;
import static com.example.administrator.shushu1.LoginActivity.lianxifangshi;
import static com.example.administrator.shushu1.LoginActivity.username;


public class AddBookActivity extends AppCompatActivity {
    private BmobFile bmobFile;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture=null;
    private Uri imageUri=null;
    private String imagePath="abcdefg";
    private EditText book_name;
    private EditText book_price;
    private EditText book_newrank;
    private EditText book_liangxifangshi;
    private Button book_add,back,chooseFromAlbum,takePhoto;

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this,"ab042970fe7004c50bc341f8f3efe6a4");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        back=(Button)findViewById(R.id.back_zhu);
        book_add=(Button)findViewById(R.id.book_add);
        takePhoto = (Button) findViewById(R.id.take_photo);
        book_name=(EditText)findViewById(R.id.book_name);
        book_price=(EditText)findViewById(R.id.book_price);
        book_newrank=(EditText)findViewById(R.id.book_newrank);
        book_liangxifangshi=(EditText)findViewById(R.id.book_lianxifangshi) ;
        chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建File对象，用于存储拍照后的图片
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(AddBookActivity.this, "com.example.administrator.fileprovider", outputImage);
                }
                bmobFile = new BmobFile(outputImage);
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });
        book_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name, price,rank,lianxi;
                if(book_name.getText().length()==0||book_price.getText().length()==0||book_newrank.getText().length()==0||book_liangxifangshi.getText().length()==0){
                    new AlertDialog.Builder(AddBookActivity.this)
                            .setTitle("错误！")
                            .setMessage("输入不能为空！")
                            .setPositiveButton("确定", null)
                            .show();
                }
                else{
                    if(imageUri==null&&imagePath=="abcdefg"){
                        new AlertDialog.Builder(AddBookActivity.this)
                                .setTitle("错误！")
                                .setMessage("没有图片！")
                                .setPositiveButton("确定", null)
                                .show();
                    }
                    else{
                        /*new AlertDialog.Builder(MainActivity.this)
                                .setTitle("错误！")
                                .setMessage(imageUri.getPath()+"???"+imagePath)
                                .setPositiveButton("确定", null)
                                .show();*/
                        name = book_name.getText().toString();
                        price = book_price.getText().toString();
                        rank = book_newrank.getText().toString();
                        lianxi=book_liangxifangshi.getText().toString();
                        if(lianxifangshi.equals(""))
                        {

                        }
                        else
                        {
                            book_liangxifangshi.setText(lianxifangshi);
                        }
                        if(!ifInt(price)){
                            new AlertDialog.Builder(AddBookActivity.this)
                                    .setTitle("错误！")
                                    .setMessage("价格输入不合法！")
                                    .setPositiveButton("确定", null)
                                    .show();
                            book_price.setText("");
                        }
                        if(!ifRankLegal(rank)){
                            new AlertDialog.Builder(AddBookActivity.this)
                                    .setTitle("错误！")
                                    .setMessage("新旧程度输入不合法！")
                                    .setPositiveButton("确定", null)
                                    .show();
                            book_newrank.setText("");
                        }
                        else{
                            bmobFile.uploadblock(new UploadFileListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {

                                        new AlertDialog.Builder(AddBookActivity.this)
                                                .setTitle("成功！")
                                                .setMessage("上传成功！")
                                                .setPositiveButton("确定", null)
                                                .show();

                                        Allbook allbook=new Allbook();
                                        allbook.setNewrank(Integer.parseInt(rank));
                                        allbook.setImage(bmobFile);
                                        allbook.setMoney(Integer.parseInt(price));
                                        allbook.setFrom(username);
                                        allbook.setName(name);
                                        allbook.setLianxi(lianxi);
                                        allbook.setQq(yongqq);
                                        allbook.setWeixin(yongweixin);
                                        addToAllbook(allbook);
                                    }
                                    else {
                                        new AlertDialog.Builder(AddBookActivity.this)
                                                .setTitle("失败！")
                                                .setMessage("上传失败，原因：" + e.getMessage())
                                                .setPositiveButton("确定", null)
                                                .show();
                                    }
                                }
                                @Override
                                public void onProgress(Integer value) {
                                    ProgressDialog progressDialog=new ProgressDialog(AddBookActivity.this);
                                    progressDialog.setTitle("正在上传，请稍等...");
                                    progressDialog.setMessage("进度："+value);
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();
                                }

                            });

                        }
                    }

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(AddBookActivity.this,PersonalStoreActivity.class);
                startActivity(intent_back);
            }
        });

    }

    public void addToAllbook(Allbook allbook){
        allbook.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    new AlertDialog.Builder(AddBookActivity.this)
                            .setMessage("添加图书成功！")
                            .setPositiveButton("确定", null)
                            .show();
                    Intent intent_back=new Intent(AddBookActivity.this,PersonalStoreActivity.class);
                    startActivity(intent_back);
                }
                else{
                    new AlertDialog.Builder(AddBookActivity.this)
                            .setTitle("添加失败！")
                            .setMessage("添加图书失败，原因：" + e.getMessage()+e.getErrorCode())
                            .setPositiveButton("确定", null)
                            .show();
                    Intent intent_back=new Intent(AddBookActivity.this,PersonalStoreActivity.class);
                    startActivity(intent_back);
                }
            }
        });
    }



    public boolean ifInt(String x){
        int len=x.length();
        if(len>3){
            return false;
        }
        boolean flag=true;
        for(int i=0;i<len;i++){
            if(x.charAt(i)>'9'||x.charAt(i)<'0'){
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
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }

        bmobFile = new BmobFile(new File(imagePath));
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        bmobFile = new BmobFile(new File(imagePath));
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imgPath) {
        if (imgPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            picture.setImageBitmap(bitmap);
        }
        else {
            Toast.makeText(AddBookActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}