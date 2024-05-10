package com.example.tugasmodul6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class viewSong : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_song)
        val bundle: Bundle? = intent.extras

        val viewJudul: TextView = findViewById(R.id.vJudul)
        val viewPenyanyi: TextView = findViewById(R.id.vPenyanyi)
        val backbtn: Button = findViewById(R.id.backHome)

        backbtn.setOnClickListener {
            finish()
        }

        viewJudul.text = bundle?.getString("judul")
        viewPenyanyi.text = bundle?.getString("penyanyi")
    }
}