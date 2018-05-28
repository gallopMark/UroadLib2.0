package com.uroad.uroadlib_v2.kotlintest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uroad.uroadlib_v2.R

class RecycleAdapter(private val context: Context, private val mylist: ArrayList<String>) : RecyclerView.Adapter<RecycleAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        Log.e("RecycleAdapter size :",mylist.size.toString())
        return mylist.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("RecycleAdapter  item",mylist.get(position))
         holder.tv_name.text=mylist.get(position);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycleview,null));
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        var tv_name: TextView
        constructor(view: View) : super(view) {
            tv_name=view.findViewById(R.id.tv_item)
        }
    }

    public fun updateData(item:String){
        mylist.add(item);
        notifyDataSetChanged();
    }
}