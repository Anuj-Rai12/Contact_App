package com.example.roomdatabase.myfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

    companion object {
        val myColor =
            listOf<Int>(
                R.color.anujgreen,
                R.color.anujcherryred,
                R.color.purple_500,
                R.color.black,
                R.color.teal_700,
                R.color.anujpurbles,
                R.color.anujorange
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav, container, false)
        myViewModelFun()
        myFavRecycleFun()
        getResult("1")
        return binding.root
    }

    private fun myFavRecycleFun() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)
        myFavRecycle = MyFavRecycle { contact: MyContact -> itemSelected(contact) }
        binding.recyclerView.adapter = myFavRecycle
    }

    private fun getResult(string: String) {
        val searchQuery = "%$string%"
        myViewModel.searchMyFav(searchQuery).observe(viewLifecycleOwner, {
            myFavRecycle.setMyData(it)
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