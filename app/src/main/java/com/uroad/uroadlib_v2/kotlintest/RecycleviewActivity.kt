package com.uroad.uroadlib_v2.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.uroad.uroadlib_v2.R
import com.uroad.uroadlib_v2.R.id.recycler_view
import com.uroad.uroadlib_v2.kotlintest.adapter.RecycleAdapter
import kotlinx.android.synthetic.main.activity_recycleview.*

class RecycleviewActivity:AppCompatActivity(){
    var mylist = ArrayList<String>();
    lateinit var adapter:RecycleAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycleview)
        recycler_view.layoutManager= LinearLayoutManager(this);
//        mylist.add("下拉刷新控件");
        adapter=RecycleAdapter(this,mylist)
        recycler_view.adapter=adapter
        loadData()
//        adapter.updateData("下拉刷新控件")
    }

    private fun loadData(){
        mylist.add("下拉刷新控件");
        adapter.notifyDataSetChanged()
    }
}