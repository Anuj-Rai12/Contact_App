package com.example.roomdatabase.favrecycle

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FavLayoutBinding
import com.example.roomdatabase.mycontactdb.MyContact

class MyFavHolder(val binding: FavLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun binding(myContact: MyContact, function: (MyContact) -> Unit) {
        binding.mycolorlayout.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.anujcherryred
            )
        )
        binding.myfavtext.text =
            myContact.firstName[0].toString() + myContact.lastName[0].toString()
        binding.mymenu.setOnClickListener {
            function(myContact)
        }
    }
}