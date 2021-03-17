package com.example.roomdatabase.extrafragmentfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentCreateContactBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import com.example.roomdatabase.utils.BottomSheetFragment
import com.example.roomdatabase.utils.BottomSheetFragment.Companion.bitmapop

class CreateContactFragment : Fragment() {
    private lateinit var binding: FragmentCreateContactBinding
    private lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_contact, container, false)
        myViewModelFun()
        myBottomView()
        myViewModel.snackbarmsg.observe(viewLifecycleOwner,{
            it.getContentIfNotHandled()
                ?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        })
        bitmapop.observe(viewLifecycleOwner, {
            myViewModel.bitmap = it
            binding.myprofile.setImageBitmap(it)
        })
        if (myViewModel.bitmap == null) myViewModel.bitmap = MainActivity.getmybitmap
        return binding.root
    }

    private fun myBottomView() {
        val bottomSheetDialogFragment = BottomSheetFragment()
        binding.myfolationAdd.setOnClickListener {
            bottomSheetDialogFragment.show(parentFragmentManager, "BottomSheetFragment")
        }
    }

    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.myvariable = myViewModel
        binding.lifecycleOwner = this
    }
}