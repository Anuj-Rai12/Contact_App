package com.example.roomdatabase.extrafragmentfile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.telephony.SignalStrength
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDisplayContactBinding
import com.example.roomdatabase.myviewmodle.MyViewModel

class DisplayContactFragment : Fragment() {
    private lateinit var binding: FragmentDisplayContactBinding
    private lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_display_contact, container, false)
        myViewModelFun()
        myViewModel.snackbarmsg.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        })
        val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
        binding.myfolationcall.startAnimation(blink)
        binding.myprofile.setImageBitmap(myViewModel.bitmap)
        binding.myfolationcall.setOnClickListener { calls() }
        binding.share.setOnClickListener {
            val str = binding.firstname.text.toString() + binding.Lastname.text.toString()
            val phoneNumber = binding.myphoneno.text.toString()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey There phone number of $str is $phoneNumber"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.deleteaccount.setOnClickListener { myViewModel.deleteAllData() }
        return binding.root
    }

    private fun myViewModelFun() {
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.myvariable = myViewModel
        binding.myfolationcall.setOnClickListener { calls() }
        binding.lifecycleOwner = this
    }

    private fun calls() {
        if (checkCallPermission()) {
            val dial = "tel:${binding.myphoneno.text}"
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
        } else
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
            }
    }
    private fun checkCallPermission() = (context?.let {
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.CALL_PHONE
        )
    } == PackageManager.PERMISSION_GRANTED)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Thank you ,Now you can Call", Toast.LENGTH_SHORT).show()
            }
        }
    }
}