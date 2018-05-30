package com.uroad.wechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*微信分享工具类*/
public class WeChatShareLib {
    private IWXAPI wxApi;
    private Context context;
    public static final int WXSceneSession = SendMessageToWX.Req.WXSceneSession;  //分享微信好友
    public static final int WXSceneTimeline = SendMessageToWX.Req.WXSceneTimeline; //分享朋友圈
    public static final int WXSceneFavorite = SendMessageToWX.Req.WXSceneFavorite; //分享微信收藏
    private static final int THUMB_SIZE = 150;
    private int mTargetScene = WXSceneSession;  //默认分享微信好友

    private WeChatShareLib(Context context) {
        this.context = context;
        if (wxApi == null) {
            String APP_ID = context.getResources().getString(R.string.wechat_appkey);
            wxApi = WXAPIFactory.createWXAPI(context, APP_ID, true);
            wxApi.registerApp(APP_ID);  //将应用的APP_ID注册到微信
        }
    }

    public static WeChatShareLib from(Context context) {
        return new WeChatShareLib(context);
    }

    /*判断手机客户端时候安装微信*/
    public boolean isWXAppInstalled() {
        return wxApi != null && wxApi.isWXAppInstalled();
    }

    public WeChatShareLib withScene(int scene) {
        this.mTargetScene = scene;
        return this;
    }

    /**
     * 分享文本
     *
     * @param text 文本
     */
    public void shareText(String text) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        //用textobject初始化一个
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = createReq("text", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    /**
     * 分享图片
     *
     * @param bitmap bitmap对象
     */
    public void shareImage(Bitmap bitmap) {
        if (bitmap == null) return;
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = createReq("img", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    /**
     * 分享图片
     *
     * @param path 本地图片路径
     */
    public void shareImage(String path) {
        if (TextUtils.isEmpty(path)) return;
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = createReq("img", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    public void shareMusic(String musicUrl) {
        shareMusic(musicUrl, null);
    }

    public void shareMusic(String musicUrl, Bitmap bitmap) {
        shareMusic(musicUrl, bitmap, null, null);
    }

    public void shareMusic(String musicUrl, Bitmap bitmap, String title, String description) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.description = musicUrl;
        if (!TextUtils.isEmpty(title))
            msg.title = title;
        if (!TextUtils.isEmpty(description))
            msg.description = description;
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_music);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = createReq("music", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    public void shareVideo(String videoUrl) {
        shareVideo(videoUrl, null);
    }

    public void shareVideo(String videoUrl, Bitmap bitmap) {
        shareVideo(videoUrl, bitmap, null, null);
    }

    public void shareVideo(String videoUrl, Bitmap bitmap, String title, String description) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = video;
        if (!TextUtils.isEmpty(title))
            msg.title = title;
        if (!TextUtils.isEmpty(description))
            msg.description = description;
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_video);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        //构造一个Req
        SendMessageToWX.Req req = createReq("video", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    public void shareWebpage(String webpageUrl) {
        shareWebpage(webpageUrl, null);
    }

    public void shareWebpage(String webpageUrl, Bitmap bitmap) {
        shareWebpage(webpageUrl, bitmap, null, null);
    }

    public void shareWebpage(String webpageUrl, Bitmap bitmap, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpage;
        if (!TextUtils.isEmpty(title))
            msg.title = title;
        if (!TextUtils.isEmpty(description))
            msg.description = description;
        if (bitmap != null) {
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
            bitmap.recycle();
            msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
        }
        //构造一个Req
        //构造一个Req
        SendMessageToWX.Req req = createReq("webpage", msg);
        //调用接口发送数据到微信
        wxApi.sendReq(req);
    }

    private SendMessageToWX.Req createReq(String type, WXMediaMessage msg) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Utils.buildTransaction(type);
        req.message = msg;
        req.scene = mTargetScene;
        return req;
    }

}
