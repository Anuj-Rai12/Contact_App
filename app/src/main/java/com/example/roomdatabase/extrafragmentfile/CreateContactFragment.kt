package com.example.roomdatabase.extrafragmentfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentCreateContactBinding
import com.example.roomdatabase.utils.BottomSheetFragment

class CreateContactFragment : Fragment() {
    private lateinit var binding: FragmentCreateContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_contact, container, false)
        val bottomSheetDialogFragment = BottomSheetFragment()
        binding.myfolationAdd.setOnClickListener {
            bottomSheetDialogFragment.show(parentFragmentManager, "BottomSheetFragment")
        }
        return binding.root
    }
}