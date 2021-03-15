package com.example.roomdatabase.extrafragmentfile


import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDailerBinding

class DialerFragment : Fragment() {
    private lateinit var binding: FragmentDailerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dailer, container, false)
         val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
        binding.first.startAnimation(blink)
        return binding.root
    }
}