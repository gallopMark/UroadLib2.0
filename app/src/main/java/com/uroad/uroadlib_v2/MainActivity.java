package com.uroad.uroadlib_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            case 1://图片库Demo

                break;
            case 2://数据库Demo

                break;
            case 3://高德库Demo

                break;
            case 4://分享库Demo

                break;
            case 5://ETC库Demo

                break;

        }
    }
}
