package com.example.roomdatabase.myfragments

import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentContactBinding
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.myviewmodle.MyViewModel
import com.example.roomdatabase.recyclecontact.MyContactRecycle

class ContactFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentContactBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var myContactRecycle: MyContactRecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setBackgroundDrawable(
                ColorDrawable(resources.getColor(R.color.anujgreen))
            )
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        myViewModelFun()
        myRecycleViewFun()
        myViewModel.actionOp = "ContactFragment"
        binding.addContact.setOnClickListener { gotoCreateContact() }
        return binding.root
    }

    private fun myRecycleViewFun() {
        binding.displayRecyclerView.layoutManager = LinearLayoutManager(activity)
        myContactRecycle = MyContactRecycle { selected: MyContact -> itemSelected(selected) }
        binding.displayRecyclerView.adapter = myContactRecycle
        myViewModel.allTheData.observe(viewLifecycleOwner, {
            myContactRecycle.setAllTheData(it)
            myContactRecycle.notifyDataSetChanged()
        })
    }

    private fun myViewModelFun() {
        myViewModel =
            ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun gotoCreateContact() =
        view?.findNavController()?.navigate(R.id.action_contactFragment_to_createContactFragment)

    private fun gotoDisplayContact() =
        view?.findNavController()?.navigate(R.id.action_contactFragment_to_displayContactFragment)

    private fun itemSelected(myContact: MyContact) {
        myViewModel.updateOrDelete(myContact)
        gotoDisplayContact()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_dot_mnu, menu)
        val search = menu.findItem(R.id.searchmyval)
        val del = menu.findItem(R.id.deleteAll)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        del.setOnMenuItemClickListener {
            myViewModel.deleteAllData()
            Toast.makeText(activity, "All Contacts Are Deleted", Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            getResult(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            getResult(newText)
        }
        return true
    }

    private fun getResult(string: String) {
        val searchQuery = "%$string%"
        myViewModel.searchMyRes(searchQuery).observe(viewLifecycleOwner, {
            myContactRecycle.setAllTheData(it)
            myContactRecycle.notifyDataSetChanged()
        })
    }
}

