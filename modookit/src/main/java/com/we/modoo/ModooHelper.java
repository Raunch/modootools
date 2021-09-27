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
    private static LoginCallback mLoginCallback;
    private static ShareCallback mShareCallback;

    private static String mApAuthInfo;

    /**
     * 初始化接口
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        mInited = false;
        mContext = context;
        LoginManager.getInstance().init(context);
        ShareManager.getInstance().init(context);
        mInited = true;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 支付宝登陆，需要通过外部方式获取授权请求参数
     * 获取授权请求参数后，调用该接口
     */
    public static void setAPAuthInfo(String authInfo) {
        mApAuthInfo = authInfo;
    }

    public static String getAPAuthInfo() {
        return mApAuthInfo;
    }

    /**
     * 设置登录回调
     * 后续建议使用 login(LoginType type, LoginCallback callback)
     *
     * @param callback 回调接口
     */
    @Deprecated
    public static void setLoginCallback(LoginCallback callback) {
        mLoginCallback = callback;
    }

    /**
     * 登录接口
     * 后续建议使用 login(LoginType type, LoginCallback callback)
     *
     * @param type 登录类型，目前支持微信
     */
    @Deprecated
    public static void login(LoginType type) {
        if (mInited) {
            if (!hasInitLoginCallback()) {
                LogUtil.e(TAG, "please set login callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_login_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            LoginManager.getInstance().login(type, mLoginCallback);
        } else {
            if (mContext == null) {
                LogUtil.e(TAG, "Please first invoke the init");
                return;
            }
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置用户登录
     *
     * @param type     登陆类型，目前支持微信和支付宝
     * @param callback 回调函数
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
     * 设置分享的回调接口
     *
     * @param callback 回调接口
     */
    public static void registerShareCallback(ShareCallback callback) {
        mShareCallback = callback;
    }

    private static boolean hasInitShareCallback() {
        if (mShareCallback == null) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean hasInitLoginCallback() {
        if (mLoginCallback == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 分享文字
     *
     * @param app  应用类型
     * @param type 分享或收藏的目标场景
     * @param text 分享的文字内容
     */
    public static void shareText(ShareType app, int type, String text) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareText(app, type, text, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享图片
     *
     * @param app  应用类型
     * @param type 分享或收藏的目标场景
     * @param path 分享图片的路径
     */
    public static void shareImageByPath(ShareType app, int type, String path) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareImageByPath(app, type, path, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param app  应用类型
     * @param type 分享或收藏的目标场景
     * @param id   分享图片的资源id
     */
    public static void shareImageByResId(ShareType app, int type, int id) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareImageByResId(app, type, id, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param app   应用类型
     * @param type  分享或收藏的目标场景
     * @param bytes 图片 bytes 信息
     */
    public static void shareImageBytes(ShareType app, int type, byte[] bytes) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareImageBytes(app, type, bytes, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享音乐
     *
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享音乐的url
     * @param title       音乐的标题
     * @param description 音乐的描述
     * @param path        缩略图资源路径
     */
    public static void shareMusic(ShareType app, int type, String url, String title, String description, String path) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareMusic(app, type, url, title, description, path, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 视频分享
     *
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享视频的url
     * @param title       视频的标题
     * @param description 视频的描述
     * @param id          缩略图资源id
     */
    public static void shareVideo(ShareType app, int type, String url, String title, String description, int id) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareVideo(app, type, url, title, description, id, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 音乐分享
     *
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享音乐的url
     * @param title       音乐的标题
     * @param description 音乐的描述
     * @param id          缩略图资源id
     */
    public static void shareMusic(ShareType app, int type, String url, String title, String description, int id) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareMusic(app, type, url, title, description, id, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 视频分享
     *
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享视频的url
     * @param title       视频的标题
     * @param description 视频的描述
     * @param path        缩略图资源路径
     */
    public static void shareVideo(ShareType app, int type, String url, String title, String description, String path) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareVideo(app, type, url, title, description, path, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 网页分享
     *
     * @param app         应用类型
     * @param type        分享或收藏的目标场景
     * @param url         分享网页链接
     * @param title       分享网页的标题
     * @param description 分享网页的描述
     * @param path        缩略图资源路径
     */
    public static void shareWebpage(ShareType app, int type, String url, String title, String description, String path) {
        if (mInited) {
            if (!hasInitShareCallback()) {
                LogUtil.e(TAG, "please set share callback first");
                Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_share_callback_not_set"), Toast.LENGTH_SHORT).show();
                return;
            }
            ShareManager.getInstance().shareWebpage(app, type, url, title, description, path, mShareCallback);
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
     */
    public static void shareWebpage(ShareType app, int type, String url, String title, String description, int id) {
        if (mInited) {
            ShareManager.getInstance().shareWebpage(app, type, url, title, description, id, mShareCallback);
        } else {
            Toast.makeText(mContext, ResUtils.getStringId(mContext, "modoo_init_first"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据名称从RES中获取对应资源ID
     *
     * @param name 资源名称
     * @return 资源对应的ID
     */
    public static int getDefaultIconRes(String name) {
        if (mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName()) == 0) {
            return mContext.getResources().getIdentifier(name, "mipmap", mContext.getPackageName());
        } else {
            return mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        }
    }


}
