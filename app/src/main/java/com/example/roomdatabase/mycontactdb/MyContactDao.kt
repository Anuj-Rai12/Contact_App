package com.example.roomdatabase.mycontactdb

import android.media.MediaPlayer
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

}