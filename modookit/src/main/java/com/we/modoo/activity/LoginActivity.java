package com.we.modoo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.we.modoo.ModooHelper;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.login.AuthResult;

import java.util.Map;

public class LoginActivity extends Activity {
    private String TAG = LoginActivity.class.getSimpleName();
    private static LoginCallback mCallback;
    private String mServerAuthorInfo;

    private static final int SDK_AUTH_FLAG = 2;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        // 调用 RichOX 接口绑定支付宝账户
                        if (mCallback != null) {
                            mCallback.loginSuccess(authResult.getAuthCode());
                        }
                    } else {
                        // 其他状态值则为授权失败
                        // 授权失败提示用户提现失败
                        if (mCallback != null) {
                            mCallback.loginFailed(resultStatus);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
            finish();
        }

        ;
    };

    public static void setLoginCallback(LoginCallback callback) {
        mCallback = callback;
    }

    public static void startActivity(String apLoginInfo) {
        Intent intent = new Intent();
        intent.setClass(ModooHelper.getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("auth_info", apLoginInfo);
        ModooHelper.getContext().startActivity(intent);
    }

    private void handleIntentSelf(Intent intent) {
        mServerAuthorInfo = intent.getStringExtra("auth_info");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * authInfo 的获取通过调用RichOX请求授权参数接口获取
         */
        handleIntentSelf(getIntent());
        if (TextUtils.isEmpty(mServerAuthorInfo)) {
            if (mCallback != null) {
                mCallback.loginFailed("no server auth info");
                finish();
            }
        }
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(LoginActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(mServerAuthorInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }


}
