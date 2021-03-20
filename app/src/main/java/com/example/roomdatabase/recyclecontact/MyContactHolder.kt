package com.example.roomdatabase.recyclecontact

import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyContactHolder(val binding: DisplayDetailBinding) : RecyclerView.ViewHolder(binding.root) {
    fun binding(currObj: MyContact, function: (MyContact) -> Unit) {
        binding.myrecyprofile.setImageBitmap(currObj.profilePicture)
        binding.myrecyphone.text=currObj.firstName+""+currObj.lastName
        binding.mylayout.setOnClickListener {
            function(currObj)
        }
    }
}