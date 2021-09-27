package com.we.modoo.core;

import android.content.Context;
import android.util.Log;

import com.we.modoo.callback.LoginCallback;
import com.we.modoo.login.APLogin;
import com.we.modoo.login.ILogin;
import com.we.modoo.login.WechatLogin;

public class LoginManager {
    private static final String TAG = "LoginManager";
    private Context mContext;
    private static LoginManager mInstance;

    private ILogin mLogin;

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new LoginManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
    }

    private void createLoginInstance(LoginType type) {
        if (type.equals(LoginType.Wechat)) {
            mLogin = WechatLogin.getInstance();
        } else if (type.equals(LoginType.AliPay)) {
            mLogin = APLogin.getInstance();
        }
    }

    public void login(LoginType type, LoginCallback callback) {
        createLoginInstance(type);
        if (mLogin != null) {
            mLogin.init(mContext);
            if (mLogin.hasInit()) {
                mLogin.login(callback);
            }
        } else {
            Log.e(TAG, "login instance is null");
        }
    }

}
