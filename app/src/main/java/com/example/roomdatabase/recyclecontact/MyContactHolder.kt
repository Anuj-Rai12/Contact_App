package com.example.roomdatabase.recyclecontact

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyContactHolder(val binding: DisplayDetailBinding) : RecyclerView.ViewHolder(binding.root) {
    fun binding(currObj: MyContact,randomDigits:Int,function: (MyContact) -> Unit) {
        binding.userfirst.text=currObj.firstName[0].toUpperCase().toString()
        binding.myrecyphone.text=currObj.firstName+""+currObj.lastName
        binding.mylayout.setOnClickListener {
            function(currObj)
        }
    }
}