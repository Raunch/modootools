package com.we.modoo.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.we.modoo.login.WechatLogin;
import com.we.modoo.share.WechatShare;
import com.we.modoo.utils.LogUtil;
import com.we.modoo.utils.ResUtils;

/**
 * Created by shun on 2017/12/18.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = "WXEntryActivity";
    private IWXAPI api;

    private final int WECHAT_TPYE_LOGIN = 1;
    private final int WECHAT_TYPE_SHARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "on create here");
        int id = getApplication().getResources().getIdentifier("wx_app_id", "string", getPackageName());
        String appId = getResources().getString(id);
        api = WXAPIFactory.createWXAPI(this, appId, true);
        api.registerApp(appId);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            LogUtil.d(TAG, "go here err");
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d(TAG, "wx onReq");
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d(TAG, "the type is " + resp.getType());
        LogUtil.d(TAG, "the errCode is " + resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case WECHAT_TPYE_LOGIN:
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_login_wexin_success"), Toast.LENGTH_SHORT).show();
                        WechatLogin.getInstance().handleResult(true, ((SendAuth.Resp) resp).code);
                        break;
                    case WECHAT_TYPE_SHARE:
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_share_wexin_success"), Toast.LENGTH_SHORT).show();
                        WechatShare.getInstance().getShareCallback().shareSuccess();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                switch (resp.getType()) {
                    case WECHAT_TPYE_LOGIN:
                        LogUtil.d(TAG, "wexin login cancel");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_login_wexin_cancel"), Toast.LENGTH_SHORT).show();
                        WechatLogin.getInstance().handleResult(false, "cancel");
                        break;
                    case WECHAT_TYPE_SHARE:
                        LogUtil.d(TAG, "wexin share cancel");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_share_wexin_cancel"), Toast.LENGTH_SHORT).show();
                        WechatShare.getInstance().getShareCallback().shareCancel();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                switch (resp.getType()) {
                    case WECHAT_TPYE_LOGIN:
                        LogUtil.d(TAG, "wexin login denied");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_login_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatLogin.getInstance().handleResult(false, "denied");
                        break;
                    case WECHAT_TYPE_SHARE:
                        LogUtil.d(TAG, "wexin share denied");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_share_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatShare.getInstance().getShareCallback().shareFailed();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                switch (resp.getType()) {
                    case WECHAT_TPYE_LOGIN:
                        LogUtil.d(TAG, "wexin login unsupport");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_login_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatLogin.getInstance().handleResult(false, "unsupport");
                        break;
                    case WECHAT_TYPE_SHARE:
                        LogUtil.d(TAG, "wexin share unsupport");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_share_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatShare.getInstance().getShareCallback().shareFailed();
                        break;
                }
                break;
            default:
                switch (resp.getType()) {
                    case WECHAT_TPYE_LOGIN:
                        LogUtil.d(TAG, "wexin login unknown");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_login_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatLogin.getInstance().handleResult(false, "failed");
                        break;
                    case WECHAT_TYPE_SHARE:
                        LogUtil.d(TAG, "wexin share unknown");
                        Toast.makeText(this, ResUtils.getStringId(this, "modoo_share_wexin_failed"), Toast.LENGTH_SHORT).show();
                        WechatShare.getInstance().getShareCallback().shareFailed();
                        break;
                }
        }
        finish();
    }
}

