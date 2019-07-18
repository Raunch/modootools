package com.we.modoo.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.utils.LogUtil;

public class WechatLogin implements ILogin{
    private final String TAG = "WechatLogin";

    private  String mAppID;
    private static WechatLogin mInstance;
    private IWXAPI mApi;
    private boolean mInited;

    private LoginCallback mCallback;

    private WechatLogin() {

    }

    public static WechatLogin getInstance() {
        if (mInstance == null) {
            synchronized (WechatLogin.class) {
                if (mInstance == null) {
                    mInstance = new WechatLogin();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        try {
            int appId = context.getResources().getIdentifier("wx_app_id", "string", context.getPackageName());
            mAppID = context.getResources().getString(appId);
            mApi = WXAPIFactory.createWXAPI(context, mAppID, true);
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // 将该app注册到微信
                    LogUtil.d(TAG, "update weixin");
                    mApi.registerApp(mAppID);
                }
            }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
            mApi.registerApp(mAppID);
            mInited = true;
            LogUtil.d(TAG, "wx login init ok");
        } catch (Exception e) {
            mInited = false;
            LogUtil.e(TAG, "wx login init fail");
        }
    }

    @Override
    public boolean hasInit() {
        return mInited;
    }

    @Override
    public void login(LoginCallback callback) {
        mCallback = callback;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_game_login";
        mApi.sendReq(req);
        LogUtil.d(TAG, "request login ok");
    }

    public void handleResult(boolean success, String result) {
        if (success) {
            mCallback.loginSuccess(result);
        } else {
            mCallback.loginFailed(result);
        }
    }
}
