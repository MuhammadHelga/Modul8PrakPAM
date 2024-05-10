package com.example.tugasmodul6.databaseroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class dataSong(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "judul")
    val judul: String,
    @ColumnInfo(name = "penyanyi")
    val penyanyi: String
)
