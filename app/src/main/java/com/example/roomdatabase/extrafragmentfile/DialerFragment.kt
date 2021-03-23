package com.example.roomdatabase.extrafragmentfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDailerBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialerFragment : Fragment() {
    private lateinit var binding: FragmentDailerBinding
    private lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        CoroutineScope(Main).launch {
            statusBar()
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dailer, container, false)
        myViewModelFun()
        val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
        binding.mycallbutton.startAnimation(blink)
        binding.mycallbutton.setOnClickListener {
            calls()
            myViewModel.bitmap=MainActivity.getmybitmap
            val str=binding.myphoneno.text.toString()
            myViewModel.addDetailCall(str)
        }
        return binding.root
    }
    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }
    private suspend fun statusBar()
    {
        delay(150)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    private fun calls()
    {
        if(checkCallPermission())
        {
            val intent=Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:+${binding.codepicker!!.selectedCountryCode+binding.myphoneno.text}"))
            startActivity(intent)
        }
        else
            Toast.makeText(activity, "Call permission not given", Toast.LENGTH_SHORT).show()
    }
    private fun gotCallHistory()=view?.findNavController()?.navigate(R.id.action_dailerFragment_to_callFragment)

    private fun checkCallPermission() = (context?.let {
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.CALL_PHONE
        )
    } == PackageManager.PERMISSION_GRANTED)

}