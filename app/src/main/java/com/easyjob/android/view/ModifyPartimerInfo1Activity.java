package com.easyjob.android.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.easyjob.android.R;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.Partimer_Info;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ModifyPartimerInfo1Activity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 1;     //请求码
    private static final int RESIZE_REQUEST_CODE = 2;
    private EditText et_partimer_name,et_partimer_age,et_partimer_education,et_partimer_height,
            et_partimer_phone,et_partimer_email,et_partimer_introduciton;
    private Button bt_save_partimer_info;
    private ImageButton ib_partimer_avatar;
    private RadioButton sex_male,sex_female;
    private RadioGroup rg_sex;
    private String name,age,education,height,phone,email,sex,introduction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_partimer_info1);
        initView();
    }
    private void initView(){
        Bmob.initialize(this, Info.getAppid());
        et_partimer_name = (EditText) findViewById(R.id.et_partimer_name);
        et_partimer_age = (EditText) findViewById(R.id.et_partimer_age);
        et_partimer_education = (EditText) findViewById(R.id.et_partimer_education);
        et_partimer_height = (EditText) findViewById(R.id.et_partimer_height);
        et_partimer_phone = (EditText) findViewById(R.id.et_partimer_phone);
        et_partimer_email = (EditText) findViewById(R.id.et_partimer_email);
        bt_save_partimer_info = (Button) findViewById(R.id.bt_save_partimer_info);
        et_partimer_introduciton = (EditText) findViewById(R.id.et_partimer_introduction);
        ib_partimer_avatar = (ImageButton) findViewById(R.id.ib_partimer_avatar);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        sex_male = (RadioButton) findViewById(R.id.sex_male);
        sex_female = (RadioButton) findViewById(R.id.sex_female);
        et_partimer_phone.setText(Info.getP_phone());
        sex_male.setChecked(true);
        sex="男";
        ib_partimer_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");          //以图片格式打开
                intent.setAction(Intent.ACTION_PICK);   //设为默认图库打开
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });


        bt_save_partimer_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtaintext();   //获取文本
                //判断输入非空
                if(!judgetextnotnull(name,age,education,height,phone,email)){
                    return;
                }
                add_partimer_info(); // 添加信息到数据库
            }
        });
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.sex_male:
                        sex = sex_male.getText().toString();
                        break;
                    case R.id.sex_female:
                        sex = sex_female.getText().toString();
                }
            }
        });
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
                        String imageUri=cursor.getString(index);
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
            ib_partimer_avatar.setBackground(drawable);
        }
    }

    public void resizeAvatar(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");     //跳转到系统自带的裁剪界面
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
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
                if (e == null) {
                    Info.setP_avatar(avatar.getFileUrl());
                    relevantPartimerInfo();
                } else {
                    Toast.makeText(ModifyPartimerInfo1Activity.this, "上传头像失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            private void relevantPartimerInfo() {
                Partimer_Info p1 = new Partimer_Info();
                p1.setP_avatar(avatar);
                p1.update(Info.getP_id(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ModifyPartimerInfo1Activity.this, "上传头像成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }

            private void obtaintext() {

                name = et_partimer_name.getText().toString();
                Info.setP_name(name);
                age = et_partimer_age.getText().toString();
                education = et_partimer_education.getText().toString();
                height = et_partimer_height.getText().toString();
                phone = et_partimer_phone.getText().toString();
                email = et_partimer_email.getText().toString();
                introduction = et_partimer_introduciton.getText().toString();


            }

            private boolean judgetextnotnull(String name, String age, String education, String height, String phone, String email) {
                if (name.isEmpty() || age.isEmpty() || education.isEmpty() || height.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ModifyPartimerInfo1Activity.this, "请把信息输入齐整", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            }

            private void add_partimer_info() {
                Partimer_Info p = new Partimer_Info();
                p.setP_name(name);
                p.setP_age(age + "岁");
                p.setP_education(education);
                p.setP_height(height + "cm");
                p.setP_phone(phone);
                p.setP_email(email);
                p.setP_sex(sex);
                p.setP_introduction(introduction);
                p.update(Info.getP_id(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ModifyPartimerInfo1Activity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ModifyPartimerInfo1Activity.this, PartimerActivity.class);
                            startActivity(intent);
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });


        }
    }
