package com.example.roomdatabase.extrafragmentfile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentCreateContactBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import com.example.roomdatabase.utils.BottomSheetFragment
import com.example.roomdatabase.utils.BottomSheetFragment.Companion.bitmapop
import kotlinx.coroutines.launch

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
    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://picsum.photos/200/300")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
    private fun myBottomView() {
        binding.myfolationAdd.setOnClickListener {
            lifecycleScope.launch {
                binding.myprofile.setImageBitmap(getBitmap())
                myViewModel.bitmap=getBitmap()
            }
        }
    }

    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.myvariable = myViewModel
        binding.lifecycleOwner = this
    }
}