package com.example.roomdatabase.myfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentCallBinding
import com.example.roomdatabase.utils.startAnimation


class CallFragment : Fragment() {
    private lateinit var binding: FragmentCallBinding
    private lateinit var animation: Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call, container, false)
        animation = AnimationUtils.loadAnimation(activity, R.anim.green_expolsion).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        return binding.root
    }

    private fun gotoDialer() {
        binding.apply {
            mydialer.setOnClickListener {
                mydialer.isVisible=false
                greenboom.isVisible=true
                greenboom.startAnimation(animation,{})
            }
        }
    }

}