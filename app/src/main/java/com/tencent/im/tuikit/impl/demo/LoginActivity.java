package com.tencent.im.tuikit.impl.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;
import com.tencent.qcloud.tuicore.util.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private TextView loginBtn;
    private EditText userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        userID = findViewById(R.id.userID);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在用户 UI 点击登录的时候调用
                // context 必须传 Application 对象，否则部分图片无法加载
                String userSig = GenerateTestUserSig.genTestUserSig(userID.getText().toString().trim());

                TUILogin.login(DemoApplication.instance(), GenerateTestUserSig.SDKAPPID, userID.getText().toString(), userSig, new TUICallback() {
                    @Override
                    public void onError(final int code, final String desc) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtil.toastLongMessage(desc + ", errCode = " + code + ", errInfo = " + desc);
                            }
                        });
                        Log.e("TAG", "imLogin errorCode = " + code + ", errorInfo = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}