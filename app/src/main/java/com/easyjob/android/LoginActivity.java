package com.easyjob.android;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mob.MobSDK;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_phone, et_captcha;
    private Button bt_captcha, bt_login;
    private String phone;//手机号
    private CountDownTimer countDownTimer;//倒计时
    private int TIME=120;//120s


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobSDK.init(this, "28df11c5166d2", "949af7998c76f77468cce921e41a9f81");
        setContentView(R.layout.activity_login);
        et_captcha = (EditText) findViewById(R.id.et_captcha);
        et_phone = (EditText) findViewById(R.id.et_phone);
        bt_captcha = (Button) findViewById(R.id.bt_captcha);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_captcha.setOnClickListener(this);
        bt_login.setOnClickListener(this);

        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                boolean b = new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                Toast.makeText(getApplicationContext(), "获取验证码成功", Toast.LENGTH_SHORT).show();
                                // TODO 处理成功得到验证码的结果

                            } else {
                                Toast.makeText(getApplicationContext(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                phone = et_phone.getText().toString();
                                Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,ChooseActivity.class);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                                finish();

                                // TODO 处理验证码验证通过的结果
                            } else {
                                // TODO 处理错误的结果
                                Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
// 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码
    @Override
    public void onClick(View v) {
        phone = et_phone.getText().toString();
        switch (v.getId()) {
            case R.id.bt_captcha:
                //判断手机号码是否正确
                Intent intent=new Intent(LoginActivity.this,ChooseActivity.class);
                intent.putExtra("phone","13642591627");
                startActivity(intent);
                ///////////////////////////////////////////TODO 测试用，记得删除
                if (!judgePhone(phone)) {
                    return;
                }
                //发送短信验证
                SMSSDK.getVerificationCode("86", phone);
                countDownTimer=new CountDownTimer(TIME*1000,1000) {
                   @Override
                   public void onTick(long TIME) {
                       bt_captcha.setClickable(false);//验证按钮不可点击，倒计时
                       bt_captcha.setText("已发送(" + TIME / 1000 + ")");
                   }

                   @Override
                   public void onFinish() {
                       bt_captcha.setClickable(true);
                       bt_captcha.setText("重新获取验证码");
                   }
               }.start();
                break;
            case R.id.bt_login:
                //将收到的验证码和手机号再次提交核对
                SMSSDK.submitVerificationCode("86", phone, et_captcha.getText().toString());
                break;
        }
    }


        //判断手机号是否正确
        private  boolean judgePhone(String phone) {
        if(isMatchLength(phone,11)
                &&isMoblieNo(phone)){
            return true;
        }
        Toast.makeText(this,"手机号格式错误",Toast.LENGTH_SHORT).show();
        return false;
        }
        public static boolean isMatchLength(String str,int length){
        if(str.isEmpty()){
            return false;
        }
        else{
            return str.length()==length?true:false; }
        }

        public static boolean isMoblieNo(String phone){
        String telRegex="[1][358]\\d{9}";
        if(TextUtils.isEmpty(phone)){
            return false;
        }
        else
            return phone.matches(telRegex);
        }
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}

