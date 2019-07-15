package com.we.modoo.core;

import android.content.Context;

import com.we.modoo.callback.ShareCallback;
import com.we.modoo.share.IShare;
import com.we.modoo.share.WechatShare;
import com.we.modoo.utils.LogUtil;

public class ShareManager {
    private final static String TAG = "ShareManager";
    private Context mContext;
    private static ShareManager mInstance;
    private IShare mShare;

    private ShareManager() {

    }

    public static ShareManager getInstance() {
        if (mInstance == null) {
            synchronized (ShareManager.class) {
                if (mInstance == null) {
                    mInstance = new ShareManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
    }

    private void createShareInstance(ShareType app) {
        if (app.equals(ShareType.WeChat)) {
            mShare = WechatShare.getInstance();
        }
    }

    private boolean hasPluginInited(ShareType app) {
        if (mShare == null) {
            createShareInstance(app);
        }
        mShare.init(mContext);
        return mShare.hasInited();
    }

    public void shareText (ShareType app, int type, String text, ShareCallback callback) {
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareText(type, text, callback);

    }

    public void shareImageByPath(ShareType app, int type, String path, ShareCallback callback) {
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareImageByPath(type, path, callback);
    }

    public void shareImageByResId(ShareType app, int type, int id, ShareCallback callback){
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareImageByResId(type, id, callback);
    }

    public void shareMusic(ShareType app, int type ,String url, String title, String description, String path, ShareCallback callback){
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareMusic(type, url, title, description, path, callback);
    }

    public void shareVideo(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback) {
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareVideo(type, url ,title, description, id, callback);
    }

    public void shareMusic(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback){
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareMusic(type, url, title, description, id, callback);
    }

    public void shareVideo(ShareType app, int type, String url, String title, String description, String path, ShareCallback callback){
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareVideo(type, url, title, description, path, callback);
    }

    public void shareWebpage(ShareType app, int type, String url, String title, String description, String path, ShareCallback callback){
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareWebpage(type, url, title, description, path, callback);
    }

    public void shareWebpage(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback) {
        if (!hasPluginInited(app)) {
            LogUtil.e(TAG, "share init err");
            return;
        }
        mShare.shareWebpage(type, url, title, description, id, callback);
    }


}
