package com.example.tugasmodul6.databaseroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [dataSong::class],
    version = 1
)
abstract class DataBases : RoomDatabase(){

    abstract fun Dao() : DAO

    companion object {

        @Volatile private var instance : DataBases? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DataBases::class.java,
            "menu.db"
        ).build()

    }
}