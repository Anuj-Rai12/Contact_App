package com.example.roomdatabase.utils

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.BottonLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: BottonLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.botton_layout, container, false)
        binding.btnopencam.setOnClickListener {
            Toast.makeText(activity, "Hello fucker", Toast.LENGTH_SHORT).show()
        }
        binding.btnopengal.setOnClickListener {
            Toast.makeText(activity, "Hello fucker form gallery", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}