package com.easyjob.android;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.CookieManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ModifyRecruiterInfoActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 1;     //请求码
    private static final int RESIZE_REQUEST_CODE = 2;
    private EditText et_recruiter_company,et_recruiter_phone,et_recruiter_email,et_recruiter_introduction,et_recruiter_address;
    private ImageButton im_recruiter_avator;
    private Button bt_save_company_info;
    private String address,phone,company,email,profile;
    private String imageUri;
//    private Handler handler = new Handler(){
//        public void handleMessage(Message msg){
//            switch (msg.what){
//                case RESIZE_REQUEST_CODE:
//                    Picasso.with(ModifyRecruiterInfoActivity.this).load(Info.ravatar).into(im_recruiter_avator);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
    protected void onResume(){
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.with(ModifyRecruiterInfoActivity.this).load(Info.ravatar).fit().into(im_recruiter_avator);
            }
        }, 1500);

        initView();
        showInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recruiter_info);
    }


    private void initView(){
        Bmob.initialize(this,Info.appid);
        getSupportActionBar().setTitle("修改信息");
        et_recruiter_address = (EditText) findViewById(R.id.et_recruiter_address);
        et_recruiter_company = (EditText) findViewById(R.id.et_recruiter_company);
        et_recruiter_email = (EditText) findViewById(R.id.et_recruiter_email);
        et_recruiter_introduction = (EditText) findViewById(R.id.et_recruiter_introduction);
        et_recruiter_phone = (EditText) findViewById(R.id.et_recruiter_phone);
        im_recruiter_avator = (ImageButton) findViewById(R.id.im_recruiter_avator);
        bt_save_company_info = (Button) findViewById(R.id.bt_save_company_info);

        im_recruiter_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");          //以图片格式打开
                intent.setAction(Intent.ACTION_PICK);   //设为默认图库打开
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
        bt_save_company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtaintext();
                if(!judgetextnotnull(address,company,email,profile,phone)){
                    return;
                }

                add_cruiter_info(); // 添加信息到数据库
                saveLocal();    //保存到本地
                Intent intent = new Intent(ModifyRecruiterInfoActivity.this, CompanyInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showInfo() {
        et_recruiter_company.setText(Info.rcompany);
        et_recruiter_address.setText(Info.raddress);
        et_recruiter_email.setText(Info.remail);
        et_recruiter_phone.setText(Info.rphone);
        et_recruiter_introduction.setText(Info.rprofile);


        final ImageView imageView = new ImageView(ModifyRecruiterInfoActivity.this);
        Picasso.with(ModifyRecruiterInfoActivity.this).load(Info.ravatar).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                im_recruiter_avator.setBackground(imageView.getDrawable());
            }

            @Override
            public void onError() {

            }
        });
    }

    private void obtaintext(){
        address=et_recruiter_address.getText().toString();
        company=et_recruiter_company.getText().toString();
        email=et_recruiter_email.getText().toString();
        profile=et_recruiter_introduction.getText().toString();
        phone=et_recruiter_phone.getText().toString();
    }
    private boolean judgetextnotnull(String address,String company,String email,String introduction,String phone){
        if(address.isEmpty()||company.isEmpty()||email.isEmpty()||introduction.isEmpty()||phone.isEmpty()){
            Toast.makeText(this,"请把信息输入齐整", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void add_cruiter_info(){
        Recruiter_Info r = new Recruiter_Info();
        r.setRecruiter_address(address);
        r.setRecruiter_company(company);
        r.setRecruiter_email(email);
        r.setRecruiter_profile(profile);
        r.setRecruiter_phone(phone);
        r.update(Info.recruiter_id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ModifyRecruiterInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void saveLocal(){
        Info.raddress=et_recruiter_address.getText().toString();
        Info.rcompany=et_recruiter_company.getText().toString();
        Info.remail=et_recruiter_email.getText().toString();
        Info.rprofile=et_recruiter_introduction.getText().toString();
        Info.rphone=et_recruiter_phone.getText().toString();
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri avatarUri=data.getData();      //获取图片uri
                    resizeAvatar(avatarUri);            //裁剪
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data!= null) {

                        showAvatar(data);
                        Bundle extras = data.getExtras();
                        Bitmap photo = extras.getParcelable("data");
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), photo, null,null));
                        String []avatar={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                        Cursor cursor=this.getContentResolver().query(uri, avatar, null, null, null);
                        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        imageUri=cursor.getString(index);

                        upload(imageUri);

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void showAvatar(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {

            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(),photo);
            im_recruiter_avator.setBackground(drawable);
        }
    }

    public void resizeAvatar(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");     //跳转到系统自带的裁剪界面
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale",true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);

    }
    private void upload(final String imgpath){
        final BmobFile avatar = new BmobFile(new File(imgpath));
        avatar.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    relevantRecruiterInfo();
                    Info.ravatar=avatar.getFileUrl();
                    Toast.makeText(ModifyRecruiterInfoActivity.this ,"上传头像成功:" + avatar.getFileUrl(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ModifyRecruiterInfoActivity.this ,"上传头像失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }



            private void relevantRecruiterInfo(){
                Recruiter_Info r2 =new Recruiter_Info();
                r2.setRecruiter_avatar(avatar);
                r2.update(Info.recruiter_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }//////////////////////////


}
