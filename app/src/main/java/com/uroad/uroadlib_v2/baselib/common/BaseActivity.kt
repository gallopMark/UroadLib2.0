package com.uroad.uroadlib_v2.baselib.common

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.uroad.uroadlib_v2.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_base.*

/**
 *Created by MFB on 2018/6/4.
 */
abstract class BaseActivity : AppCompatActivity() {
    private val rxDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //强制竖屏
        setContentView(R.layout.activity_base)
        setToolbar()
        setUp(savedInstanceState)
        initData()
        setListener()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        //显示NavigationIcon,这个方法是ActionBar的方法.Toolbar没有这个方法
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setNavigationIcon(resID: Int) {
        toolbar.setNavigationIcon(resID)
    }

    fun setToolTitle(title: CharSequence) {
        toolbar.let {
            it.title = ""
            setSupportActionBar(it)
        }
        val tvTitle = findViewById<TextView>(R.id.toolbar_title)
        tvTitle?.text = title
    }

    open fun setBaseContentLayout(layoutId: Int) {
        layoutInflater.inflate(layoutId, container, true)
    }

    open fun setBaseContentLayout(contentView: View?) {
        contentView?.let { container.addView(it) }
    }

    open fun setUp(savedInstanceState: Bundle?) {}

    open fun initData() {}

    open fun setListener() {}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun addDisposable(d: Disposable?) {
        d?.let { rxDisposables.add(it) }
    }

    // 封装跳转
    fun openActivity(c: Class<*>) {
        openActivity(c, null)
    }

    // 跳转 传递数据 bundel
    fun openActivity(c: Class<*>, bundle: Bundle?) {
        openActivity(c, bundle, null)
    }

    fun openActivity(c: Class<*>, bundle: Bundle?, uri: Uri?) {
        val intent = Intent(this, c)
        bundle?.let { intent.putExtras(it) }
        uri?.let { intent.data = it }
        startActivity(intent)
    }

    fun openActivity(intent: Intent) {
        openActivity(intent, null)
    }

    fun openActivity(intent: Intent, bundle: Bundle?) {
        openActivity(intent, bundle, null)
    }

    fun openActivity(intent: Intent, bundle: Bundle?, uri: Uri?) {
        bundle?.let { intent.putExtras(it) }
        uri?.let { intent.data = uri }
        startActivity(intent)
    }

    fun openActivityForResult(c: Class<*>, requestCode: Int) {
        openActivityForResult(c, null, requestCode)
    }

    fun openActivityForResult(c: Class<*>, bundle: Bundle?, requestCode: Int) {
        openActivityForResult(c, bundle, null, requestCode)
    }

    fun openActivityForResult(c: Class<*>, bundle: Bundle?, uri: Uri?, requestCode: Int) {
        val intent = Intent(this, c)
        bundle?.let { intent.putExtras(it) }
        uri?.let { intent.data = it }
        startActivityForResult(intent, requestCode)
    }

    fun openActivityForResult(intent: Intent, requestCode: Int) {
        openActivityForResult(intent, null, requestCode)
    }

    fun openActivityForResult(intent: Intent, bundle: Bundle?, requestCode: Int) {
        openActivityForResult(intent, bundle, null, requestCode)
    }

    fun openActivityForResult(intent: Intent, bundle: Bundle?, uri: Uri?, requestCode: Int) {
        bundle?.let { intent.putExtras(it) }
        uri?.let { intent.data = it }
        startActivityForResult(intent, requestCode)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDisposables.dispose()
    }
}