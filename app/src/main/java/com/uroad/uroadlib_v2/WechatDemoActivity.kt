package com.uroad.uroadlib_v2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.uroad.wechat.WeChatAuth
import com.uroad.wechat.WeChatPayLib
import com.uroad.wechat.WeChatShareLib
import kotlinx.android.synthetic.main.activity_wechat_demo.*

/**
 *Created by MFB on 2018/5/28.
 */
class WechatDemoActivity : AppCompatActivity(), View.OnClickListener {

    private var mTargetScene = WeChatShareLib.WXSceneSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wechat_demo)
        bt_shareText.setOnClickListener(this)
        bt_shareImage.setOnClickListener(this)
        bt_shareMusic.setOnClickListener(this)
        bt_shareVideo.setOnClickListener(this)
        bt_shareWebpage.setOnClickListener(this)
        bt_pay.setOnClickListener(this)
        bt_auth.setOnClickListener(this)
        radioGroup.setOnCheckedChangeListener { _, checkId ->
            if (checkId == com.uroad.wechat.R.id.rb_wechat) {
                mTargetScene = WeChatShareLib.WXSceneSession
            } else if (checkId == com.uroad.wechat.R.id.rb_wechatmoments) {
                mTargetScene = WeChatShareLib.WXSceneTimeline
            } else if (checkId == com.uroad.wechat.R.id.rb_wechatfavorite) {
                mTargetScene = WeChatShareLib.WXSceneFavorite
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_shareText -> {
                WeChatShareLib.from(this).withScene(mTargetScene)
                        .shareText("用户授权后，开发者将获得从移动应用点击跳转关联小程序，且回到原移动应用的权限")
            }
            R.id.bt_shareImage -> {
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                WeChatShareLib.from(this).withScene(mTargetScene).shareImage(bitmap)
            }
            R.id.bt_shareMusic -> {
                val musicUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3"
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                WeChatShareLib.from(this).withScene(mTargetScene).shareMusic(musicUrl, bitmap,
                        "分享微信音乐", "这是我做微信分享的第一首音乐")
            }
            R.id.bt_shareVideo -> {
                val videoUrl = "http://www.modrails.com/videos/passenger_nginx.mov"
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                WeChatShareLib.from(this).withScene(mTargetScene).shareVideo(videoUrl, bitmap,
                        "分享微信视频", "这是我做微信分享的第一个视频")
            }
            R.id.bt_shareWebpage -> {
                val webpageUrl = "https://www.v2ex.com/amp/t/417282"
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                WeChatShareLib.from(this).withScene(mTargetScene).shareWebpage(webpageUrl, bitmap, "WebPage Title",
                        "微信开放平台就是一个坑，特别是微信分享神坑！")
            }
            R.id.bt_pay -> {
                val param = WeChatPayLib.Param().apply {
                    appId = "wxd930ea5d5a258f4f"
                    partnerId = "1900000109"
                    prepayId = "1101000000140415649af9fc314aa427"
                    packageValue = "Sign=WXPay"
                    nonceStr = "1101000000140429eb40476f8896f4c9"
                    timeStamp = "1398746574"
                    sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B"
                }
                WeChatPayLib.from(this).pay(param)
            }
            R.id.bt_auth -> {
                WeChatAuth.from(this).sendAuthRequest("uroad_auth_test")
            }
        }
    }
}