package com.example.roomdatabase.myfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentContactBinding
import com.example.roomdatabase.myviewmodle.MyViewModel
import com.example.roomdatabase.recyclecontact.MyContactRecycle

class ContactFragment : Fragment() {
    private lateinit var binding: FragmentContactBinding
    private lateinit var myViewModel:MyViewModel
    private lateinit var myContactRecycle:MyContactRecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        myViewModelFun()
        myRecycleViewFun()
        binding.addContact.setOnClickListener { gotoCreateContact() }
        return binding.root
    }

    private fun myRecycleViewFun() {
        binding.displayRecyclerView.layoutManager=RecyclerView.LinearLayoutManager
    }

    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }
    private fun gotoCreateContact() =
        view?.findNavController()?.navigate(R.id.action_contactFragment_to_createContactFragment)

}