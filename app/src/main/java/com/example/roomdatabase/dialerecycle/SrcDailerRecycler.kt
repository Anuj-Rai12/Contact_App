package com.example.roomdatabase.dialerecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class SrcDailerRecycler(private val function: (MyContact) -> Unit) :RecyclerView.Adapter<SrcViewHolder>() {
    private val arrayList = ArrayList<MyContact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SrcViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DisplayDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.display_detail, parent, false)
        return SrcViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SrcViewHolder, position: Int) {
        var currObj = arrayList[position]
        holder.binding(currObj,function)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun setData(list: List<MyContact>) {
        arrayList.clear()
        arrayList.addAll(list)
    }
}