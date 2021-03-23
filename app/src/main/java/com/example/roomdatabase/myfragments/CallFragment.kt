package com.example.roomdatabase.myfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentCallBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import com.example.roomdatabase.utils.startAnimation


class CallFragment : Fragment() {
    private lateinit var binding: FragmentCallBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var animation: Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call, container, false)
        myViewModelFun()
        myViewModel.actionOp="CallFragment"
        animation = AnimationUtils.loadAnimation(activity, R.anim.green_expolsion).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }
        gotoDialer()
        return binding.root
    }
    private fun gotoDialer() {
        binding.apply {
            mydialer.setOnClickListener {
                (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
                mydialer.isVisible = false
                greenboom.isVisible = true
                greenboom.startAnimation(animation) {
                    callDialer()
                }
            }
        }
    }
    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }
    private fun callDialer() =
        view?.findNavController()?.navigate(R.id.action_callFragment_to_dailerFragment)
}