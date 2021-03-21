package com.example.roomdatabase.myviewmodle

import android.app.Application
import android.graphics.Bitmap
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.mycontactdb.MyContactBolier
import com.example.roomdatabase.repos.MyContactRepo
import com.example.roomdatabase.utils.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    //Activity/Fragment Checker
    var actionOp:String?=null
    //Repository linking boz of to avoiding many instances
    private val repo: MyContactRepo
    val allTheData: LiveData<List<MyContact>>

    init {
        val dao = MyContactBolier.getInstance(application).dataAccessObj
        repo = MyContactRepo(dao)
        allTheData = repo.dao
    }

    //UpdateOrDelete bool
    private var updateOrDelete: Boolean = false
    private lateinit var myContact: MyContact

    //Messages
    private var _snackbarmsg = MutableLiveData<Event<String>>()
    val snackbarmsg: LiveData<Event<String>>
        get() = _snackbarmsg


    val inputLastName = MutableLiveData<String?>()

    val inputFirstName = MutableLiveData<String?>()

    val phoneNo = MutableLiveData<String?>()

    var bitmap: Bitmap? = null

    init {
        initial()
    }

    fun setData(view: View) {
        if (updateOrDelete) {
            //update
            initial()
        } else {
            if (inputFirstName.value.isNullOrEmpty() || inputLastName.value.isNullOrEmpty() || phoneNo.value.isNullOrEmpty() || !phoneNo.value!!.isDigitsOnly() || bitmap == null) {
                _snackbarmsg.value = Event("Profile is not Created")
                initial()
                return
            }
            insert(
                MyContact(
                    0,
                    "0",
                    inputFirstName.value!!,
                    inputLastName.value!!,
                    phoneNo.value!!,
                    bitmap!!
                )
            )
            _snackbarmsg.value = Event("Profile is Created Successfully")
            initial()
        }
    }

    fun deleteAllData() {
        if (updateOrDelete) {
            delete(myContact)
            _snackbarmsg.value = Event("Profile is Deleted successfully")
            initial()
        } else {
            deleteAll()
            _snackbarmsg.value = Event("Profile is Deleted successfully")
            initial()
        }
    }

    fun searchMyRes(string: String): LiveData<List<MyContact>> {
        return repo.searchResult(string)
    }

    fun searchMyFav(string: String): LiveData<List<MyContact>> {
        return repo.searchFav(string)
    }

    fun updateOrDelete(myContact: MyContact) {
        inputFirstName.value = myContact.firstName
        inputLastName.value = myContact.lastName
        phoneNo.value = myContact.phoneNumber
        bitmap = myContact.profilePicture
        updateOrDelete = true
        this.myContact = myContact
    }

    fun fav(view: View) = if (myContact.favor == "0") {
        favOrUpdate("1")
        myContact.favor = "1"
        _snackbarmsg.value = Event("This is you Favorite profile")
    } else {
        favOrUpdate("0")
        myContact.favor = "0"
        _snackbarmsg.value = Event("Favorite profile is Removed")
    }

    fun edit(view: View) {
        _snackbarmsg.value = Event("You have click Edit")
    }

    private fun insert(myContact: MyContact): Job = viewModelScope.launch {
        repo.insertContact(myContact)
    }

    private fun delete(myContact: MyContact): Job = viewModelScope.launch {
        repo.deleteSingleData(myContact)
    }

    private fun update(myContact: MyContact): Job = viewModelScope.launch {
        repo.updateContact(myContact)
    }

    private fun deleteAll(): Job = viewModelScope.launch {
        repo.deleteAllData()
    }

    private fun favOrUpdate(int: String) {
        update(
            MyContact(
                myContact.id,
                int,
                myContact.firstName,
                myContact.lastName,
                myContact.phoneNumber,
                myContact.profilePicture
            )
        )
    }

    fun initial() {
        inputFirstName.value = null
        inputLastName.value = null
        phoneNo.value = null
        bitmap = null
        updateOrDelete = false
    }
}