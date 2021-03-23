package com.example.roomdatabase.extrafragmentfile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentUpadeBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpadeBinding
    private lateinit var myViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upade, container, false)
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.myvariable = myViewModel
        binding.lifecycleOwner = this
        setBackGround()
        myBottomView()
        myViewModel.snackbarmsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        })
        if (myViewModel.bitmap == null) myViewModel.bitmap = MainActivity.getmybitmap
        binding.createacc.setOnClickListener {
            myViewModel.updateData()
            when (myViewModel.actionOp) {
                "ContactFragment" -> {
                    it.findNavController()
                        .navigate(R.id.action_upadeFragment_to_contactFragment)
                }
                "FavFragment" -> {
                    it.findNavController()
                        .navigate(R.id.action_upadeFragment_to_favFragment)
                }
                else->{
                    it.findNavController()
                        .navigate(R.id.action_upadeFragment_to_callFragment)
                }
            }
        }
        return binding.root
    }

    private fun setBackGround() {
        binding.myprofile.setImageBitmap(myViewModel.bitmap)
        val background = BitmapDrawable(resources, myViewModel.bitmap)
        binding.mylayoutback.background = background
    }
    private suspend fun getBitmap(): Bitmap? {
        binding.progressBar.visibility = View.VISIBLE
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://picsum.photos/800/800")
            .build()
        return try {
            val result = (loading.execute(request) as SuccessResult).drawable
            binding.progressBar.visibility = View.GONE
            (result as BitmapDrawable).bitmap
        } catch (e: Exception) {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(activity, "Oops Something went wrong", Toast.LENGTH_SHORT).show()
            MainActivity.getmybitmap
        }
    }
    private suspend fun setLayoutImg(bitImg: Deferred<Bitmap?>)
    {
        val background = BitmapDrawable(resources, bitImg.await())
        binding.mylayoutback.background = background
    }
    private fun myBottomView() {
        binding.myfolationAdd.setOnClickListener {
            lifecycleScope.launch {
                val deferred=async {  getBitmap()}
                if (deferred.await()!=null) {
                    binding.myprofile.setImageBitmap(deferred.await())
                    setLayoutImg(deferred)
                    myViewModel.bitmap = deferred.await()
                }
            }
        }
    }
}