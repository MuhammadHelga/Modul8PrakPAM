package com.example.tugasmodul6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasmodul6.databaseroom.DataBases
import com.example.tugasmodul6.databaseroom.dataSong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapterSong: songAdapter
    private val datas: MutableList<dataSong> = mutableListOf()
    val db by lazy { DataBases(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ListSong: RecyclerView = findViewById(R.id.rvList)
        val addListButton: Button = findViewById(R.id.addList)
        val inputJudul: EditText = findViewById(R.id.inJudul)
        val inputPenyanyi: EditText = findViewById(R.id.inPenyanyi)
        val btnAddSong: Button = findViewById(R.id.addSong)
//        dataSong.addAll(getDataSong())
        adapterSong =  songAdapter(this, datas)
        ListSong.adapter = adapterSong
        ListSong.layoutManager = LinearLayoutManager(this)
        val trans: ImageView = findViewById(R.id.transparant)
        val layin: ImageView = findViewById(R.id.bgLay)
        val linearInput: LinearLayout = findViewById(R.id.inLinear)

        addListButton.setOnClickListener {
            trans.visibility = View.VISIBLE
            layin.visibility = View.VISIBLE
            layin.slidetrans(1000, 0)
            linearInput.visibility = View.VISIBLE
            linearInput.slidetrans(1000, 0)
            btnAddSong.setOnClickListener {
                val inJudul = inputJudul.text.toString()
                val inPenyanyi = inputPenyanyi.text.toString()

                if(inJudul.isNotEmpty() && inPenyanyi.isNotEmpty()) {
//                    val songs = Song()
//                    songs.judul = inJudul
//                    songs.penyanyi = inPenyanyi
//                    dataSong.add(songs)
//                    adapterSong.notifyItemInserted(dataSong.size-1)
                    CoroutineScope(Dispatchers.IO).launch {
                        db.Dao().addSong(
                            dataSong(0, inJudul, inPenyanyi)
                        )
                        val listes = db.Dao().getSong()
                        withContext(Dispatchers.Main) {
                            adapterSong.setData(listes)
                        }
                    }
                    inputJudul.text.clear()
                    inputPenyanyi.text.clear()
                    trans.visibility = View.INVISIBLE
                    layin.visibility = View.INVISIBLE
                    linearInput.visibility = View.GONE
                } else {
                    Toast.makeText(this, "Tidak boleh ada data yang kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val listes = db.Dao().getSong()
            withContext(Dispatchers.Main) {
                adapterSong.setData(listes)
            }
        }
    }

//    private fun getDataSong(): ArrayList<Song> {
//        val judul = resources.getStringArray(R.array.judul)
//        val penyanyi = resources.getStringArray(R.array.penyanyi)
//        val data = ArrayList<Song>()
//        for(i in judul.indices) {
//            val song = Song()
//            song.judul = judul[i]
//            song.penyanyi = penyanyi[i]
//            data.add(song)
//        }
//        return data
//    }

    fun View.slidetrans(animTime: Long, startOffset: Long) {
        val transition = AnimationUtils.loadAnimation(context, R.anim.transition_lay).apply {
            duration = animTime
            interpolator = FastOutSlowInInterpolator()
            this.startOffset = startOffset
        }
        startAnimation(transition)
    }
}