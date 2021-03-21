package com.example.roomdatabase.favrecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FavLayoutBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyFavRecycle(private val function: (MyContact) -> Unit) :RecyclerView.Adapter<MyFavHolder>() {
    private var arrayList=ArrayList<MyContact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FavLayoutBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fav_layout, parent, false)
        return MyFavHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFavHolder, position: Int) {
        var currObj=arrayList[position]
        holder.binding(currObj,function)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun setMyData(list: List<MyContact>)
    {
        arrayList.clear()
        arrayList.addAll(list)
    }
}