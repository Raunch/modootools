package com.we.modoo.share;

import android.content.Context;

import com.we.modoo.callback.ShareCallback;

public interface IShare {
    int SHARE_TYPE_SESSION = 0;
    int SHARE_TYPE_TIMELINE = 1;
    int SHARE_TYPE_FAVORITE = 2;

    void init(Context context);
    boolean hasInited();
    void shareText(int type, String text, ShareCallback callback);
    void shareImageByPath(int type, String path, ShareCallback callback);
    void shareImageByResId(int type, int id, ShareCallback callback);
    void shareMusic(int type ,String url, String title, String description, String path, ShareCallback callback);
    void shareVideo(int type, String url, String title, String description, int id, ShareCallback callback);
    void shareMusic(int type, String url, String title, String description, int id, ShareCallback callback);
    void shareVideo(int type, String url, String title, String description, String path, ShareCallback callback);
    void shareWebpage(int type, String url, String title, String description, String path, ShareCallback callback);
    void shareWebpage(int type, String url, String title, String description, int id, ShareCallback callback);
}
