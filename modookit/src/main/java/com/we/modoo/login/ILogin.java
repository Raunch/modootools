package com.we.modoo.login;

import android.content.Context;

import com.we.modoo.callback.LoginCallback;

public interface ILogin {
    void init(Context context);
    boolean hasInit();
    void login(LoginCallback callback);
}
