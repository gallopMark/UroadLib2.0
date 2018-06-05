package com.uroad.uroadlib_v2

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.uroad.uroadlib_v2.R.id.lv_menu

import com.uroad.uroadlib_v2.baselib.common.BaseActivity
import com.uroad.uroadlib_v2.kotlintest.RecycleviewActivity
import com.uroad.uroadlib_v2.push.XGPushActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setUp(savedInstanceState: Bundle?) {
        setBaseContentLayout(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)  //隐藏返回键
        initListener()
        loadData()
    }

    private fun initListener() {
        lv_menu.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> itemClick(position) }
    }

    private fun loadData() {
        val menus = resources.getStringArray(R.array.main_menus)
        val adapter = ArrayAdapter(this, R.layout.item_main_menu, menus)
        lv_menu.adapter = adapter
    }

    private fun itemClick(position: Int) {
        when (position) {
            0//基础库Demo
            -> {
            }
            1//地图库Demo
            -> {
            }
            2//定位库Demo
            -> {
            }
            3//导航库Demo
            -> {
            }
            4//搜索库Demo
            -> {
            }
            5//语音库Demo
            -> {
            }
            6//微信库Demo
            -> startActivity(Intent(this, WechatDemoActivity::class.java))
            7//QQ库Demo
            -> startActivity(Intent(this, TecentQQDemoActivity::class.java))
            8//支付宝库Demo
            -> {
            }
            9//ETC蓝牙库Demo
            -> {
            }
            10//ETCNFC库Demo
            -> {
            }
            11//下拉刷新库Demo
            -> {
            }
            12//图片选择库Demo
            -> {
            }
            13//信鸽推送Demo
            -> startActivity(Intent(this, XGPushActivity::class.java))
            14 -> startActivity(Intent(this, RecycleviewActivity::class.java))
            15  //网络库Demo
            -> startActivity(Intent(this, RxHttpDemoActivity::class.java))
        }
    }
}
