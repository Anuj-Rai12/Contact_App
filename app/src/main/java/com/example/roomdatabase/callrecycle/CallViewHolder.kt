package com.example.roomdatabase.callrecycle

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.DisplayDetailBinding
import com.example.roomdatabase.mycontactdb.MyContact

class CallViewHolder(private val binding: DisplayDetailBinding):RecyclerView.ViewHolder(binding.root) {
    fun binding(currObj: MyContact, function: (MyContact) -> Unit) {
            binding.userfirst.text = currObj.firstName[0].toUpperCase().toString()
            binding.myrecyphone.text = currObj.firstName+"("+currObj.phonecall+") times"
            binding.mylayout.setOnClickListener {
                function(currObj)
            }
        }
    }
