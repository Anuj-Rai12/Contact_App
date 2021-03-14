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
import androidx.navigation.findNavController
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call, container, false)
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
                mydialer.isVisible = false
                greenboom.isVisible = true
                greenboom.startAnimation(animation) {
                    callDailer()
                }
            }
        }
    }

    private fun callDailer() =
        view?.findNavController()?.navigate(R.id.action_callFragment_to_dailerFragment)
}