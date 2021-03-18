package com.example.roomdatabase.mycontactdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomdatabase.MainActivity
@Database(entities = [MyContact::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyContactBolier: RoomDatabase() {
    abstract val dataAccessObj: MyContactDao

    companion object {
        @Volatile
        private var INSTANCE: MyContactBolier? = null
        fun getInstance(context: Context): MyContactBolier {
            synchronized(this) {
                var instance = INSTANCE
                if (instance != null) {
                        return instance
                }

                    synchronized(this){
                        val oldinstance = Room.databaseBuilder(
                            context.applicationContext,
                            MyContactBolier::class.java,
                            "${MainActivity.MYCONTACTDETAIL}"
                        ).build()
                        INSTANCE=oldinstance
                        return oldinstance
                    }
                }

            }
        }

    }