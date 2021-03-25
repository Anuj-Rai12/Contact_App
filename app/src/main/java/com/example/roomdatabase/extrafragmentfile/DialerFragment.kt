package com.example.roomdatabase.extrafragmentfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDailerBinding
import com.example.roomdatabase.dialerecycle.SrcDailerRecycler
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.myviewmodle.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialerFragment : Fragment() {
    private lateinit var binding: FragmentDailerBinding
    private lateinit var srcDailerRecycler: SrcDailerRecycler
    private lateinit var myViewModel: MyViewModel
    private var checkIt: Boolean = false
    private var count: Int = 0
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
        SrcDailerRecyclerFun()
        val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
        binding.mycallbutton.startAnimation(blink)
        binding.mycallbutton.setOnClickListener {
            count++
            calls()
        }
        return binding.root
    }

    private fun SrcDailerRecyclerFun() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        srcDailerRecycler = SrcDailerRecycler { selection: MyContact -> itemSelection(selection) }
        binding.recyclerView.adapter = srcDailerRecycler
    }

    private fun goToDisplay() =
        view?.findNavController()?.navigate(R.id.action_dailerFragment_to_displayContactFragment)

    private fun itemSelection(myContact: MyContact) {
        myViewModel.updateOrDelete(myContact)
        goToDisplay()
    }

    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private suspend fun statusBar() {
        delay(150)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun calls() {
        val myPhone = binding.myphoneno.text
        getResult(binding.myphoneno.text.toString())
        if (myPhone != null) {
            if (!checkIt) {
                //Toast.makeText(activity, "unknown false", Toast.LENGTH_SHORT).show()
                checkIt = false
                callCheck(myPhone)
                count=0
            } else {
                //Toast.makeText(activity, "number found true", Toast.LENGTH_SHORT).show()
                checkIt = false
                if (count > 1) {
                  //  Toast.makeText(activity, "$count is ", Toast.LENGTH_SHORT).show()
                    callCheck(myPhone)
                    count=0
                }
                return
            }
        }
    }

    private fun callCheck(myPhone: Editable) {
        if (checkCallPermission() && myPhone.isNotEmpty() && myPhone.length >= 2) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data =
                Uri.parse("tel:+${binding.codepicker.selectedCountryCode + myPhone}")
            startActivity(intent)
        } else
            Toast.makeText(activity, "Call not allowed", Toast.LENGTH_SHORT).show()
    }

    private fun checkCallPermission() = (context?.let {
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.CALL_PHONE
        )
    } == PackageManager.PERMISSION_GRANTED)

    private fun checkResult(string: String, myContact: MyContact): Boolean = try {
        string == myContact.phoneNumber
    } catch (e: NoSuchElementException) {
        Toast.makeText(activity, "Hit, No Such Function Exits", Toast.LENGTH_SHORT).show()
        false
    }

    private fun getResult(string: String) {
        val searchQuery = "%$string%"
        myViewModel.searchMyRes(searchQuery).observe(viewLifecycleOwner, {
            srcDailerRecycler.setData(it)
            checkIt = if (it.isNotEmpty())
                checkResult(string, it.first())
            else
                false
            srcDailerRecycler.notifyDataSetChanged()
        })
    }
}