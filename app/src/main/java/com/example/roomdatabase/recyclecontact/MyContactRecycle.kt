package com.example.roomdatabase.recyclecontact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyContactRecycle() : RecyclerView.Adapter<MyContactHolder>() {
    private var myAllMyContact= ArrayList<MyContact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContactHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DisplayDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.display_detail, parent, false)
        return MyContactHolder(binding)
    }

    override fun onBindViewHolder(holder: MyContactHolder, position: Int) {
        var currObj=myAllMyContact[position]
        holder.binding(currObj)
    }

    override fun getItemCount(): Int {
        return myAllMyContact.size
    }

    fun setAllTheData(myContact: List<MyContact>) {
        myAllMyContact.clear()
        myAllMyContact.addAll(myContact)
    }
}