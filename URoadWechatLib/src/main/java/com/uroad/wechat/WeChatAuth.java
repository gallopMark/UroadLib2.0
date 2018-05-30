package com.uroad.wechat;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*微信授权登录*/
public class WeChatAuth {
    private IWXAPI wxApi;

    private WeChatAuth(Context context) {
        if (wxApi == null) {
            String APP_ID = context.getResources().getString(R.string.wechat_appkey);
            wxApi = WXAPIFactory.createWXAPI(context, APP_ID, true);
            wxApi.registerApp(APP_ID);  //将应用的APP_ID注册到微信
        }
    }

    public static WeChatAuth from(Context context) {
        return new WeChatAuth(context);
    }

    /*判断手机客户端时候安装微信*/
    public boolean isWXAppInstalled() {
        return wxApi != null && wxApi.isWXAppInstalled();
    }

    public void sendAuthRequest(String state) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = state;
        wxApi.sendReq(req);
    }
}
