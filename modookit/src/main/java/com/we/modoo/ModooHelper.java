package com.we.modoo;

import android.content.Context;
import android.widget.Toast;

import com.we.modoo.callback.LoginCallback;
import com.we.modoo.callback.ShareCallback;
import com.we.modoo.core.LoginManager;
import com.we.modoo.core.LoginType;
import com.we.modoo.core.ShareManager;
import com.we.modoo.core.ShareType;
import com.we.modoo.utils.LogUtil;
import com.we.modoo.utils.ResUtils;


public class ModooHelper {
    private static String TAG = "ModooHelper";
    private static boolean mInited = false;
    private static Context mContext;

    /**
     * 初始化接口
     * @param context 上下文
     */
    public static void init(Context context) {
        mInited = false;
        mContext = context;
        LoginManager.getInstance().init(context);
        ShareManager.getInstance().init(context);
        mInited = true;
    }

    /**
     *  登录接口
     * @param type 登录类型，目前支持微信
     * @param callback 回调接口
     */
    public static void login(LoginType type, LoginCallback callback) {
        if (mInited) {
            LoginManager.getInstance().login(type, callback);
        } else {
            if (mContext == null) {
                LogUtil.e(TAG, "Please first invoke the init");
                return;
            }
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享文字
     *
     * @param app      应用类型
     * @param type     分享或收藏的目标场景
     * @param text     分享的文字内容
     * @param callback 分享回调接口
     */
    public static void shareText(ShareType app, int type, String text, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareText(app, type, text, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享图片
     * @param app      应用类型
     * @param type     分享或收藏的目标场景
     * @param path     分享图片的路径
     * @param callback 分享回调接口
     */
    public static void shareImageByPath(ShareType app, int type, String path, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareImageByPath(app, type, path, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param app      应用类型
     * @param type     分享或收藏的目标场景
     * @param id       分享图片的资源id
     * @param callback 分享回调接口
     */
    public static void shareImageByResId(ShareType app, int type, int id, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareImageByResId(app, type, id, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享音乐
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享音乐的url
     * @param title       音乐的标题
     * @param description 音乐的描述
     * @param path        缩略图资源路径
     * @param callback    分享回调接口
     */
    public static void shareMusic(ShareType app, int type, String url, String title, String description, String path, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareMusic(app, type, url, title, description, path, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 视频分享
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享视频的url
     * @param title       视频的标题
     * @param description 视频的描述
     * @param id          缩略图资源id
     * @param callback    分享回调接口
     */
    public static void shareVideo(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareVideo(app, type, url, title, description, id, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 音乐分享
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享音乐的url
     * @param title       音乐的标题
     * @param description 音乐的描述
     * @param id          缩略图资源id
     * @param callback    分享回调接口
     */
    public static void shareMusic(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareMusic(app, type, url, title, description, id, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 视频分享
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享视频的url
     * @param title       视频的标题
     * @param description 视频的描述
     * @param path        缩略图资源路径
     * @param callback    分享回调接口
     */
    public static void shareVideo(ShareType app, int type, String url, String title, String description, String path, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareVideo(app, type, url, title, description, path, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 网页分享
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享网页链接
     * @param title       分享网页的标题
     * @param description 分享网页的描述
     * @param path        缩略图资源路径
     * @param callback    分享回调接口
     */
    public static void shareWebpage(ShareType app, int type, String url, String title, String description, String path, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareWebpage(app, type, url, title, description, path, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享网页链接
     * @param title       分享网页的标题
     * @param description 分享网页的描述
     * @param id          缩略图资源id
     * @param callback    分享回调接口
     */
    public static void shareWebpage(ShareType app, int type, String url, String title, String description, int id, ShareCallback callback) {
        if (mInited) {
            ShareManager.getInstance().shareWebpage(app, type, url, title, description, id, callback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }


}
