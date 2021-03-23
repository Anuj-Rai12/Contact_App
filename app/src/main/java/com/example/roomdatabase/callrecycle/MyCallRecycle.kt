package com.example.roomdatabase.callrecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyCallRecycle(private val function: (MyContact) -> Unit) :RecyclerView.Adapter<CallViewHolder>() {
    private val arrayList= ArrayList<MyContact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DisplayDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.display_detail, parent, false)
        return CallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        var currObj=arrayList[position]
        holder.binding(currObj,function)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun setCallData(list: List<MyContact>) {
        arrayList.clear()
        arrayList.addAll(list)
    }
}