package com.example.roomdatabase.dialerecycle

import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class SrcViewHolder(private val binding: DisplayDetailBinding): RecyclerView.ViewHolder(binding.root) {
    fun binding(currObj: MyContact, function: (MyContact) -> Unit) {
        binding.userfirst.text = currObj.firstName[0].toUpperCase().toString()
        binding.myrecyphone.text = currObj.firstName
        binding.mylayout.setOnClickListener {
            function(currObj)
        }
    }
}