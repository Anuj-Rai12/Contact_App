package com.example.roomdatabase.favrecycle

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.databinding.FavLayoutBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyFavHolder(val binding: FavLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun binding(myContact: MyContact, randomDigits: Int, function: (MyContact) -> Unit) {
        binding.mycolorlayout.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                MainActivity.myColor[randomDigits]
            )
        )
        binding.myfavtext.text =
            myContact.firstName[0].toUpperCase().toString() + myContact.lastName[0].toLowerCase().toString()
        binding.mycolorlayout.setOnClickListener {
            function(myContact)
        }
    }
}