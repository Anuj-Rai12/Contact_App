package com.example.roomdatabase.mycontactdb

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomdatabase.MainActivity.Companion.MYCONTACTDETAIL

@Entity(tableName = MYCONTACTDETAIL)
data class MyContact(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "FirstName")
    var firstName: String,

    @ColumnInfo(name = "LastName")
    var lastName: String,

    @ColumnInfo(name = "PhoneNumber")
    var phoneNumber: String,

    @ColumnInfo(name = "ProfilePicture")
    var profilePicture: Bitmap
)