package com.uroad.uroadlib_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.uroad.uroadlib_v2.kotlintest.RecycleviewActivity;
import com.uroad.uroadlib_v2.push.XGPushActivity;

public class MainActivity extends AppCompatActivity {
    private ListView lvMenu;
    private String[] menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        loadData();
    }

    private void initView() {
        lvMenu = findViewById(R.id.lv_menu);
    }

    private void initListener() {
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClick(position);
            }
        });
    }

    private void loadData() {
        menus = getResources().getStringArray(R.array.main_menus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_main_menu, menus);
        lvMenu.setAdapter(adapter);
    }

    private void itemClick(int position) {
        switch (position) {
            case 0://基础库Demo

                break;
            case 1://地图库Demo

                break;
            case 2://定位库Demo

                break;
            case 3://导航库Demo

                break;
            case 4://搜索库Demo

                break;
            case 5://语音库Demo

                break;
            case 6://微信库Demo
                startActivity(new Intent(this, WechatDemoActivity.class));
                break;
            case 7://QQ库Demo
                startActivity(new Intent(this, TecentQQDemoActivity.class));
                break;
            case 8://支付宝库Demo

                break;
            case 9://ETC蓝牙库Demo

                break;
            case 10://ETCNFC库Demo

                break;
            case 11://下拉刷新库Demo

                break;
            case 12://图片选择库Demo

                break;
            case 13://信鸽推送Demo
                startActivity(new Intent(this, XGPushActivity.class));
                break;
            case 14:
                startActivity(new Intent(this, RecycleviewActivity.class));
                break;
            case 15:  //网络库Demo
               // startActivity(new Intent(this, HttpDemoActivity.class));
                break;
        }
    }
}
