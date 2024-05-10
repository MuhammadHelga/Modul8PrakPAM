package com.example.tugasmodul6

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasmodul6.databaseroom.DataBases
import com.example.tugasmodul6.databaseroom.dataSong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class songAdapter(
    private val context: Context,
    private val data: MutableList<dataSong>
) : RecyclerView.Adapter<songAdapter.songViewHolder>(){
    val db by lazy {
        DataBases(context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): songAdapter.songViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        return songViewHolder(view)
    }

    class song
    override fun onBindViewHolder(holder: songAdapter.songViewHolder, position: Int) {
//        val song: Song = data.get(position)
        val currentItem = data[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, viewSong::class.java)
            intent.putExtra("judul", currentItem.judul)
            intent.putExtra("penyanyi", currentItem.penyanyi)
            context.startActivities(arrayOf(intent))
        }

        holder.delSong.setOnClickListener {
            val itemSong = holder.adapterPosition
            if (itemSong != RecyclerView.NO_POSITION) {
                CoroutineScope(Dispatchers.IO).launch {
                    val del = data[itemSong]
                    db.Dao().delSong(del)
                    withContext(Dispatchers.Main) {
                        data.removeAt(itemSong)
                        notifyItemRemoved(itemSong)
                    }
                }
            }
        }
        holder.judul.text = currentItem.judul
        holder.penyanyi.text = currentItem.penyanyi


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(listSong: List<dataSong>) {
        data.clear()
        data.addAll(listSong)
        notifyDataSetChanged()
    }

    inner class songViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judul: TextView = itemView.findViewById(R.id.outJudul)
        val penyanyi: TextView = itemView.findViewById(R.id.outPenyanyi)
        val delSong: ImageButton = itemView.findViewById(R.id.deleteSong)
    }
}