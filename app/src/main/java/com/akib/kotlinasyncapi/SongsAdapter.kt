package com.akib.kotlinasyncapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SongsAdapter(private val songs: ArrayList<SongEntity>) :
    RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.title.text = song.title
        holder.name.text = song.name
        holder.artist.text = song.artist
        holder.rights.text = song.rights
        holder.releaseDate.text = song.releaseDate
        holder.txtUpdate.text = song.updated?.subSequence(0, 10)
        if (song.image.isNotEmpty())
            Picasso.with(holder.ivIcon.context).load(song.image).into(holder.ivIcon)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val name: TextView = itemView.findViewById(R.id.txtName)
        val artist: TextView = itemView.findViewById(R.id.txtArtist)
        val rights: TextView = itemView.findViewById(R.id.txtRights)
        val releaseDate: TextView = itemView.findViewById(R.id.txtReleaseDate)
        val txtUpdate: TextView = itemView.findViewById(R.id.txtUpdate)
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
    }
}