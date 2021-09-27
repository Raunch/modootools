package com.we.modoo.login;

import android.content.Context;
import android.text.TextUtils;

import com.we.modoo.ModooHelper;
import com.we.modoo.activity.LoginActivity;
import com.we.modoo.callback.LoginCallback;

public class APLogin implements ILogin {

    private static APLogin mInstance;

    private LoginCallback mLoginCallback;

    public static APLogin getInstance() {
        if (mInstance == null) {
            synchronized (APLogin.class) {
                if (mInstance == null) {
                    mInstance = new APLogin();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void init(Context context) {
        // do nothing
    }

    @Override
    public boolean hasInit() {
        return true;
    }

    @Override
    public void login(LoginCallback callback) {
        mLoginCallback = callback;
        LoginActivity.setLoginCallback(callback);
        if (TextUtils.isEmpty(ModooHelper.getAPAuthInfo())) {
            callback.loginFailed("ap auth info net set");
            return;
        }
        LoginActivity.startActivity(ModooHelper.getAPAuthInfo());
    }
}
