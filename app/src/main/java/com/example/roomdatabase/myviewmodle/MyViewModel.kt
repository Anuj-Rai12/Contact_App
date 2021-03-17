package com.example.roomdatabase.myviewmodle

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.EventLog
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import androidx.room.Update
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.mycontactdb.MyContactBolier
import com.example.roomdatabase.repos.MyContactRepo
import com.example.roomdatabase.utils.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {
    //repository linking boz of to avoiding many instances
    private val repo: MyContactRepo
    val allTheData: LiveData<List<MyContact>>

    init {
        val dao = MyContactBolier.getInstance(application).dataAccessObj
        repo = MyContactRepo(dao)
        allTheData = repo.dao
    }

    //Messages
    private var _snackbarmsg = MutableLiveData<Event<String>>()
    val snackbarmsg: LiveData<Event<String>>
        get() = _snackbarmsg


    val inputLastName = MutableLiveData<String?>()

    val inputFirstName = MutableLiveData<String?>()

    val phoneNo = MutableLiveData<String?>()

    var bitmap: Bitmap? = null
    fun setData(view: View) {
        if (inputFirstName.value.isNullOrEmpty() || inputLastName.value.isNullOrEmpty() || phoneNo.value.isNullOrEmpty() || !phoneNo.value!!.isDigitsOnly()) {
            return
        }
        insert(
            MyContact(
                0,
                inputFirstName.value!!,
                inputFirstName.value!!,
                phoneNo.value!!,
                bitmap!!
            )
        )
        _snackbarmsg.value = Event("Profile is Created Successfully")
    }

    private fun insert(myContact: MyContact): Job = viewModelScope.launch {
        repo.insertContact(myContact)
    }

    private fun delete(myContact: MyContact): Job = viewModelScope.launch {
        repo.updateContact(myContact)
    }

    private fun update(myContact: MyContact): Job = viewModelScope.launch {
        repo.updateContact(myContact)
    }

    private fun deleteAll(): Job = viewModelScope.launch {
        repo.deleteAllData()
    }

}