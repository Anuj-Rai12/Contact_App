package com.example.roomdatabase.mycontactdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(myContact: MyContact): Long

    @Update
    suspend fun updateContact(myContact: MyContact)

    @Delete
    suspend fun deleteOneContact(myContact: MyContact)

    @Query("delete From My_Contact_Detail")
    suspend fun deleteAllContact()

    @Query("Select *From My_Contact_Detail")
    fun displayAllData(): LiveData<List<MyContact>>

    @Query("Select *From My_Contact_Detail where FirstName Like:searchQuery or LastName Like:searchQuery or PhoneNumber Like :searchQuery")
    fun searchResult(searchQuery: String): LiveData<List<MyContact>>

    @Query("Select *From My_Contact_Detail where MyFavorite Like:searchFavQuery")
    fun searchFav(searchFavQuery: String): LiveData<List<MyContact>>


    @Query("Select *From My_Contact_Detail where CallHistory !=\"0\"")
    fun getCallHistory(): LiveData<List<MyContact>>
}