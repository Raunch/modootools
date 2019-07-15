package com.raunch.wechattool;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.we.modoo.ModooHelper;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.callback.ShareCallback;
import com.we.modoo.core.LoginType;
import com.we.modoo.core.ShareType;
import com.we.modoo.share.IShare;

import java.io.File;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private static final String TAG = "sdkdemo";
    RadioGroup mSelectGroup;
    private String mImagePath = "";
    private int mShareType = IShare.SHARE_TYPE_TIMELINE;

    ShareCallback mCallback = new ShareCallback() {
        @Override
        public void shareSuccess() {
            Toast.makeText(MainActivity.this, "share success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void shareCancel() {
            Toast.makeText(MainActivity.this, "share cancel", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void shareFailed() {
            Toast.makeText(MainActivity.this, "share failed", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setImagePath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mSelectGroup = (RadioGroup) findViewById(R.id.wechat_select_group);
        mSelectGroup.setOnCheckedChangeListener(this);
        mSelectGroup.check(R.id.wechat_select_session);
    }

    private void setImagePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mImagePath = sdcardPath + "/test/test_icon.png";
        File file = new File(mImagePath);
        if (!file.exists()) {
            Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
            mImagePath = "";
        }
    }

    private boolean hasInitialize() {
        if (TextUtils.isEmpty(mImagePath)) {
            return false;
        } else {
            return true;
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.init) {
            //初始化
            ModooHelper.init(MainActivity.this);
            ModooHelper.setLoginCallback(new LoginCallback() {
                @Override
                public void loginSuccess(String info) {
                    Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void loginCancel(String result) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void loginFailed(String result) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            });
            ModooHelper.registerShareCallback(mCallback);
        } else if (view.getId() ==R.id.login) {
            ModooHelper.login(LoginType.Wechat);
        } else if (view.getId() == R.id.share_image_one) {
            //分享图片，通过资源id方式
            ModooHelper.shareImageByResId(ShareType.WeChat, mShareType, R.mipmap.xuanchuan);
        } else if (view.getId() == R.id.share_image_two) {
            if (!hasInitialize()) {
                Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
                return;
            }
            //分享图片，通过路径方式
            ModooHelper.shareImageByPath(ShareType.WeChat, mShareType, mImagePath);

        } else if (view.getId() == R.id.share_music_one) {
            //分享音乐，通过资源id方式
            ModooHelper.shareMusic(ShareType.WeChat, mShareType, "https://y.qq.com/n/yqq/song/002JbQfb2Lh1uI.html", "心的形状", "《这个杀手不太冷》电影片尾曲", R.mipmap.test_icon);
        } else if (view.getId() == R.id.share_music_two) {
            if (!hasInitialize()) {
                Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
                return;
            }
            //分享音乐，通过资源路径方式
            ModooHelper.shareMusic(ShareType.WeChat, mShareType, "https://y.qq.com/n/yqq/song/002JbQfb2Lh1uI.html", "心的形状", "《这个杀手不太冷》电影片尾曲", mImagePath);
        } else if (view.getId() == R.id.share_video_one) {
            //分享视频，通过资源id方式
            ModooHelper.shareVideo(ShareType.WeChat, mShareType, "https://y.qq.com/n/yqq/mv/v/b0026tkjv58.html", "来不及勇敢", "《昨日青空》电影插曲", R.mipmap.test_icon);
        } else if (view.getId() == R.id.share_video_two) {
            if (!hasInitialize()) {
                Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
                return;
            }
            //分享视频，通过路径方式
            ModooHelper.shareVideo(ShareType.WeChat, mShareType, "https://y.qq.com/n/yqq/mv/v/b0026tkjv58.html", "来不及勇敢", "《昨日青空》电影插曲", mImagePath);
        } else if (view.getId() == R.id.share_webpage_one) {
            //分享网页，通过资源id方式
            ModooHelper.shareWebpage(ShareType.WeChat, mShareType,  "http://zjtx.moyoi.com/", "战箭天下", "一起来射射射", R.mipmap.test_icon);
        } else if (view.getId() == R.id.share_webpage_two) {
            if (!hasInitialize()) {
                Toast.makeText(getBaseContext(), "No image found", Toast.LENGTH_SHORT).show();
                return;
            }
            //分享网页，通过路径方式
            ModooHelper.shareWebpage(ShareType.WeChat, mShareType,  "http://zjtx.moyoi.com/", "战箭天下", "一起来射射射", mImagePath);
        } else if (view.getId() == R.id.share_text) {
            //分享文字
            ModooHelper.shareText(ShareType.WeChat, mShareType, "大家一起来玩战箭天下吧");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(TAG, "checkedId :" + checkedId);
        Log.d(TAG, "id is " + group.getCheckedRadioButtonId());
        switch (group.getCheckedRadioButtonId()) {
            case R.id.wechat_select_session:
                mShareType = IShare.SHARE_TYPE_SESSION;
                break;
            case R.id.wechat_select_timeline:
                mShareType =IShare.SHARE_TYPE_TIMELINE;
                break;
            case R.id.wechat_select_favorite:
                mShareType = IShare.SHARE_TYPE_FAVORITE;
                break;
        }

    }
}
