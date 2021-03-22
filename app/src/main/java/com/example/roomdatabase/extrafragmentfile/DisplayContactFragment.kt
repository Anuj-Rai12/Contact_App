package com.example.roomdatabase.extrafragmentfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDisplayContactBinding
import com.example.roomdatabase.myviewmodle.MyViewModel

class DisplayContactFragment : Fragment() {
    private lateinit var binding: FragmentDisplayContactBinding
    private lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_display_contact, container, false)
        myViewModelFun()
        myViewModel.snackbarmsg.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()
                ?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        })
        val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
        binding.myfolationcall.startAnimation(blink)
        setBackGround()
        binding.myfolationcall.setOnClickListener { calls() }
        binding.myfolationcall2.setOnClickListener { calls() }
        binding.share.setOnClickListener {
            val str = binding.firstname.text.toString() + " " + binding.Lastname.text.toString()
            val phoneNumber = binding.myphoneno.text.toString()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey there phone number of $str is $phoneNumber"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.deleteaccount.setOnClickListener {
            myViewModel.deleteAllData()
            when (myViewModel.actionOp) {
                "ContactFragment" -> {
                    it.findNavController()
                        .navigate(R.id.action_displayContactFragment_to_contactFragment)
                }
                "FavFragment" -> {
                    it.findNavController()
                        .navigate(R.id.action_displayContactFragment_to_favFragment)
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

    private fun myViewModelFun() {
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.myvariable = myViewModel
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
                Log.i("Permission", "Thank you ,Now you can Call")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.mnu_dot_contact, menu)
        val editMnu = menu.findItem(R.id.edithis)
        editMnu.setOnMenuItemClickListener {
            view?.findNavController()?.navigate(R.id.action_displayContactFragment_to_upadeFragment)
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}