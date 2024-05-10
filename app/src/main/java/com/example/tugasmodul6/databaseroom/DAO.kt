package com.example.tugasmodul6.databaseroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {
    @Insert
    fun addSong(song: dataSong)

    @Delete
    fun delSong(song: dataSong)

    @Query("SELECT * FROM dataSong")
    fun getSong(): List<dataSong>
}