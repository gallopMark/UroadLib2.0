package com.uroad.mobileqq;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/*qq分享工具类*/
public class TencentLib {
    private Activity context;
    private Tencent mTencent;

    private TencentLib(Activity context) {
        this.context = context;
        String APP_ID = context.getResources().getString(R.string.app_id);
        mTencent = Tencent.createInstance(APP_ID, context.getApplicationContext());
    }

    public static TencentLib from(Activity context) {
        return new TencentLib(context);
    }

    public Tencent getTencent() {
        return mTencent;
    }

    public boolean isQQInstalled() {
        return mTencent.isQQInstalled(context);
    }

    /*授权登录*/
    public void doLogin(String scope, BaseUiListener listener) {
        mTencent.login(context, scope, listener);
    }

    /**
     * QQ纯文字分享(QQsdk不支持纯文字分享功能，这里做特殊处理)
     **/
    public void shareTextToQQ(String text) {
        if (mTencent.isQQInstalled(context)) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.tencent.mobileqq",
                    "com.tencent.mobileqq.activity.JumpActivity");
            intent.setComponent(componentName);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain"); // 纯文本
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /*分享图文消息*/
    public void shareToQQ(Param param, BaseUiListener listener) {
        if (TextUtils.isEmpty(param.TITLE) && TextUtils.isEmpty(param.IMAGE_URL) && TextUtils.isEmpty(param.SUMMARY)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, param.TITLE);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, param.SUMMARY);
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, param.TARGET_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, param.IMAGE_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, param.APPNAME);
        mTencent.shareToQQ(context, bundle, listener);
    }

    /*分享本地图片*/
    public void shareLocalImageToQQ(String appName, String imagePath, BaseUiListener listener) {
        Bundle bundle = new Bundle();
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imagePath);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(context, bundle, listener);
    }

    /*分享网络图片*/
    public void shareImageUrlToQQ(String appName, String imageUrl, BaseUiListener listener) {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(context, bundle, listener);
    }

    /*分享音乐*/
    public void shareMusicToQQ(Param param, BaseUiListener listener) {
        final Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, param.TITLE);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, param.SUMMARY);
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, param.TARGET_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, param.IMAGE_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, param.AUDIO_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, param.APPNAME);
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(context, bundle, listener);
    }

    /*分享应用*/
    public void shareAppToQQ(Param param, BaseUiListener listener) {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, param.TITLE);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, param.SUMMARY);
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, param.IMAGE_URL);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, param.APPNAME);
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(context, bundle, listener);
    }

    /*分享到QQ空间
     * 完善了分享到QZone功能，分享类型参数Tencent.SHARE_TO_QQ_KEY_TYPE，目前只支持图文分享
     * */
    public void shareToQzone(Param param, BaseUiListener listener) {
        //分享类型
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, param.TITLE);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, param.SUMMARY);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, param.TARGET_URL);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, param.IMAGE_URLS);
        mTencent.shareToQzone(context, params, listener);
    }

    public static class Param {
        public String TARGET_URL;  //这条分享消息被好友点击后的跳转URL。
        public String TITLE;  //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        public String LOCAL_URL;  //本地图片路径
        public String IMAGE_URL; //分享的图片URL
        public String SUMMARY; //分享的消息摘要，最长50个字
        public String APPNAME;  //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        public String AUDIO_URL;  //音乐链接
        public ArrayList<String> IMAGE_URLS;   //分享QQ空间图片集合
    }
}
