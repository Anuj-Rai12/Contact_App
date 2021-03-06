package com.example.roomdatabase.myfragments

import android.app.ActionBar
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentFavBinding
import com.example.roomdatabase.favrecycle.MyFavRecycle
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.myviewmodle.MyViewModel


class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var myFavRecycle: MyFavRecycle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setBackgroundDrawable(
                ColorDrawable(resources.getColor(R.color.anujgreen))
            )
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        myViewModelFun()
        myFavRecycleFun()
        getResult()
        myViewModel.actionOp = "FavFragment"
        return binding.root
    }

    private fun myFavRecycleFun() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)
        myFavRecycle = MyFavRecycle { contact: MyContact -> itemSelected(contact) }
        binding.recyclerView.adapter = myFavRecycle
    }

    private fun getResult(string: String = "1") {
        val searchQuery = "%$string%"
        myViewModel.searchMyFav(searchQuery).observe(viewLifecycleOwner, {
            myFavRecycle.setMyData(it)
            if(it.isEmpty())
            {
                binding.favbg.visibility=View.VISIBLE
                binding.favbg.setImageDrawable(resources.getDrawable(R.drawable.ic_avorite))
            }
            else{
                binding.favbg.visibility=View.GONE
            }
            myFavRecycle.notifyDataSetChanged()
        })
    }

    private fun itemSelected(myContact: MyContact) {
        myViewModel.updateOrDelete(myContact)
        gotoDisplayContact()
    }

    private fun myViewModelFun() {
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun gotoDisplayContact() =
        view?.findNavController()?.navigate(R.id.action_favFragment_to_displayContactFragment)
}