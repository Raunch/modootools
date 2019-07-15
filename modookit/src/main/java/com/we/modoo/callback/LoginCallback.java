package com.we.modoo.callback;

public interface LoginCallback {
    /**
     * 登录成功
     * @param info 返回的登录信息
     */
    void loginSuccess(String info);

    /**
     * 登录取消
     * @param result 原因
     */
    void loginCancel(String result);

    /**
     * 登录失败
     * @param result 原因
     */
    void loginFailed(String result);
}
