package com.we.modoo.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.we.modoo.callback.ShareCallback;
import com.we.modoo.utils.LogUtil;
import com.we.modoo.utils.ResUtils;

import java.io.File;

public class WechatShare implements IShare {

    private static final String TAG = "WechatShare";

    private static final int THUMB_SIZE = 150;

    private static final int TARGET_SCENE = SendMessageToWX.Req.WXSceneTimeline;

    private Context mContext;
    private IWXAPI mApi;
    private boolean mInited;

    private static WechatShare mInstance;

    private ShareCallback mShareCallback;

    public synchronized static WechatShare getInstance() {
        if (mInstance == null) {
            synchronized (WechatShare.class) {
                if (mInstance == null) {
                    mInstance = new WechatShare();
                }
            }
        }
        return mInstance;
    }

    public WechatShare() {
        LogUtil.d(TAG, "create wechat share");
    }

    /**
     * 微信API初始化
     * @param context 上下文
     */
    public void init(Context context) {
        LogUtil.d(TAG, "wechat share init here");
        mInited = false;
        mContext = context;
        final String appId = ResUtils.getString(context, "wx_app_id");
        if (!TextUtils.isEmpty(appId)) {
            mApi = WXAPIFactory.createWXAPI(mContext, appId, false);
            mApi.registerApp(appId);
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // 将该app注册到微信
                    LogUtil.d(TAG, "update weixin");
                    mApi.registerApp(appId);
                }
            }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
            mInited = true;
        } else {
            LogUtil.e(TAG, "get app id failed");
            mInited = false;
        }
    }

    @Override
    public boolean hasInited() {
        return mInited;
    }

    public ShareCallback getShareCallback() {
        return mShareCallback;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private int getWechatShareType(int type) {
        int shareType = SendMessageToWX.Req.WXSceneTimeline;
        switch (type) {
            case IShare.SHARE_TYPE_SESSION:
                shareType = SendMessageToWX.Req.WXSceneSession;
                break;
            case IShare.SHARE_TYPE_TIMELINE:
                shareType = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case IShare.SHARE_TYPE_FAVORITE:
                shareType = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                shareType = SendMessageToWX.Req.WXSceneTimeline;
        }
        return shareType;
    }

    private boolean isFileExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 文字类型分享
     * @param type 分享或收藏的目标场景
     * @param text 分享的文字内容
     * @param callback 分享后回调接口
     */
    @Override
    public void shareText(int type, String text, ShareCallback callback) {
        mShareCallback = callback;
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = getWechatShareType(type);

        mApi.sendReq(req);
    }

    /**
     * 图片类型分享
     * @param type 分享或收藏的目标场景
     * @param path 分享图片的路径
     * @param callback 分享后回调接口
     */
    @Override
    public void shareImageByPath(int type, String path, ShareCallback callback) {
        mShareCallback = callback;
        if (!isFileExist(path)) {
            LogUtil.e(TAG,"share image by path has no file ++++++++++++++++++++++++++++++++++++++++++++");
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     *  图片类型分享
     * @param type 分享或收藏的目标场景
     * @param id 分享图片的资源id
     * @param callback 分享后回调接口
     */
    @Override
    public void shareImageByResId(int type, int id, ShareCallback callback) {
        mShareCallback = callback;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    public void shareImageBytes(int type, byte[] bytes, ShareCallback callback) {
        mShareCallback = callback;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 音乐类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享音乐的url
     * @param title 音乐的标题
     * @param description 音乐的描述
     * @param path 分享后显示缩略图资源路径
     * @param callback 分享后回调接口
     */
    @Override
    public void shareMusic(int type ,String url, String title, String description, String path, ShareCallback callback) {
        mShareCallback = callback;
        WXMusicObject music = new WXMusicObject();
        music.musicLowBandUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;

        if (!isFileExist(path)) {
            LogUtil.e(TAG,"share music by path has no file ++++++++++++++++++++++++++++++++++++++++++++");
            return;
        }

        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 视频类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享视频的url
     * @param title 视频的标题
     * @param description 视频的描述
     * @param id 分享后显示缩略图资源id
     * @param callback 分享后回调接口
     */
    @Override
    public void shareVideo(int type, String url, String title, String description, int id, ShareCallback callback) {
        mShareCallback = callback;
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = url;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 音乐类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享音乐的url
     * @param title 音乐的标题
     * @param description 音乐的描述
     * @param id 分享后显示缩略图资源id
     * @param callback 分享后回调接口
     */
    @Override
    public void shareMusic(int type, String url, String title, String description, int id, ShareCallback callback) {
        mShareCallback = callback;
        WXMusicObject music = new WXMusicObject();
        music.musicLowBandUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;

        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 视频类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享视频的url
     * @param title 视频的标题
     * @param description 视频的描述
     * @param path 分享后显示缩略图资源路径
     * @param callback 分享后回调接口
     */
    @Override
    public void shareVideo(int type, String url, String title, String description, String path, ShareCallback callback) {
        mShareCallback = callback;
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = url;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;

        if (!isFileExist(path)) {
            LogUtil.e(TAG,"share music by path has no file ++++++++++++++++++++++++++++++++++++++++++++");
            return;
        }
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 网页类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享网页链接
     * @param title 分享网页的标题
     * @param description 分享网页的描述
     * @param path 分享后显示缩略图资源路径
     * @param callback 分享后回调接口
     */
    @Override
    public void shareWebpage(int type, String url, String title, String description, String path, ShareCallback callback) {
        mShareCallback = callback;
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        if (!isFileExist(path)) {
            LogUtil.e(TAG,"share music by path has no file ++++++++++++++++++++++++++++++++++++++++++++");
            return;
        }
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

    /**
     * 网页类型分享
     * @param type 分享或收藏的目标场景
     * @param url 分享网页链接
     * @param title 分享网页的标题
     * @param description 分享网页的描述
     * @param id 分享后显示缩略图资源id
     * @param callback 分享后回调接口
     */
    @Override
    public void shareWebpage(int type, String url, String title, String description, int id, ShareCallback callback) {
        mShareCallback = callback;
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();

        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        //msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = getWechatShareType(type);
        mApi.sendReq(req);
    }

}
