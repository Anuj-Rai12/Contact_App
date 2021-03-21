package com.example.roomdatabase.repos

import androidx.lifecycle.LiveData
import com.example.roomdatabase.mycontactdb.MyContact
import com.example.roomdatabase.mycontactdb.MyContactDao

class MyContactRepo(val myContactDao: MyContactDao) {
    val dao = myContactDao.displayAllData()

    suspend fun insertContact(myContact: MyContact) {
        myContactDao.insertContact(myContact)
    }

    suspend fun updateContact(myContact: MyContact) {
        myContactDao.updateContact(myContact)
    }

    suspend fun deleteSingleData(myContact: MyContact) {
        myContactDao.deleteOneContact(myContact)
    }

    suspend fun deleteAllData() {
        myContactDao.deleteAllContact()
    }

    fun searchResult(searchQuery: String): LiveData<List<MyContact>> {
        return myContactDao.searchResult(searchQuery)
    }
    fun searchFav(searchQuery: String): LiveData<List<MyContact>> {
        return myContactDao.searchFav(searchQuery)
    }
}