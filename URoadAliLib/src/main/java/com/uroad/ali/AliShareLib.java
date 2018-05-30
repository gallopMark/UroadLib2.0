package com.uroad.ali;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APImageObject;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APTextObject;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;

import java.io.File;

/*支付宝分享工具类*/
public class AliShareLib {
    private IAPApi api;
    //分享到支付宝好友
    public static final int ZFBSceneSession = SendMessageToZFB.Req.ZFBSceneSession;
    //分享到支付宝生活圈
    public static final int ZFBSceneTimeLine = SendMessageToZFB.Req.ZFBSceneTimeLine;
    private int mTargetScene = -1;

    private AliShareLib(Context context) {
        if (api == null) {
            String appId = context.getResources().getString(R.string.app_key);
            api = APAPIFactory.createZFBApi(context.getApplicationContext(), appId);
        }
    }

    public AliShareLib from(Context context) {
        return new AliShareLib(context);
    }

    public AliShareLib withScene(int scene) {
        if (!isAlipayIgnoreChannel()) {
            return this;
        }
        mTargetScene = scene;
        return this;
    }

    /**
     * 从支付宝9.9.5版本（VersionCode为101）开始，在您的App内，您无需判断分享场景
     * ，调用起支付宝之后会有列表让用户选择分享到的渠道；
     * 在支付宝9.9.5版本之前，如果需要，你可以通过场景码来区分分享到支付宝聊天还是支付宝动态，
     * 如果不传递场景码，默认是分享到支付宝聊天。
     */
    private boolean isAlipayIgnoreChannel() {
        return api.getZFBVersionCode() >= 101;
    }

    /*文本信息分享*/
    public void shareText(String text) {
        //初始化一个APTextObject对象
        APTextObject textObject = new APTextObject();
        textObject.text = text;
        //用APTextObject对象初始化一个APMediaMessage对象
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = textObject;
        //构造一个Req
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        if (mTargetScene != -1) req.scene = mTargetScene;
        req.message = mediaMessage;
        req.transaction = buildTransaction("text");
        //调用api接口发送消息到支付宝
        api.sendReq(req);
    }

    /*分享本地图片*/
    public void shareLocalImage(String path) {
        if (TextUtils.isEmpty(path) || !new File(path).exists()) return;
        APImageObject imageObject = new APImageObject();
        imageObject.imagePath = path;
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = imageObject;
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        if (mTargetScene != -1) req.scene = mTargetScene;
        req.message = mediaMessage;
        req.transaction = buildTransaction("image");
        api.sendReq(req);
    }

    /*分享bitmap图像*/
    public void shareBmpImage(Bitmap bmp) {
        if (bmp == null) return;
        APImageObject imageObject = new APImageObject(bmp);
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = imageObject;
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        if (mTargetScene != -1) req.scene = mTargetScene;
        req.message = mediaMessage;
        req.transaction = buildTransaction("image");
        bmp.recycle();
        api.sendReq(req);
    }

    /*分享图片链接*/
    public void shareOnlineImage(String url) {
        APImageObject imageObject = new APImageObject();
        imageObject.imageUrl = url;
        APMediaMessage mediaMessage = new APMediaMessage();
        mediaMessage.mediaObject = imageObject;
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        if (mTargetScene != -1) req.scene = mTargetScene;
        req.message = mediaMessage;
        req.transaction = buildTransaction("image");
        api.sendReq(req);
    }

    /*分享网页*/
    public void shareWebPage(String webpageUrl, String title, String description, String thumbUrl) {
        APWebPageObject webPageObject = new APWebPageObject();
        webPageObject.webpageUrl = webpageUrl;
        APMediaMessage webMessage = new APMediaMessage();
        if (!TextUtils.isEmpty(title)) webMessage.title = title;
        if (!TextUtils.isEmpty(description)) webMessage.description = description;
        webMessage.mediaObject = webPageObject;
        if (!TextUtils.isEmpty(thumbUrl)) webMessage.thumbUrl = thumbUrl;
        SendMessageToZFB.Req req = new SendMessageToZFB.Req();
        if (mTargetScene != -1) req.scene = mTargetScene;
        req.message = webMessage;
        req.transaction = buildTransaction("webpage");
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
