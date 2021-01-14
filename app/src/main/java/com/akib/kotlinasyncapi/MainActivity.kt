package com.akib.kotlinasyncapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), APIInterface {

    private val TAG = "MAIN_ACTIVITY"
    private val url =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
    private var rvAPIData: RecyclerView? = null
    private var songs = ArrayList<SongEntity>()
    private var apiDataAdapter = SongsAdapter(songs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val asyncTask = AsyncAPIClass(this)
        asyncTask.execute(url)
        rvAPIData = findViewById(R.id.rvAPIData)
        rvAPIData?.layoutManager = LinearLayoutManager(this)
        rvAPIData?.adapter = apiDataAdapter
    }

    override fun onSuccess(message: String, songs: ArrayList<SongEntity>) {
        Log.e(TAG, message)
        Log.e(TAG, "total songs ${songs.size}")
        this.songs.clear()
        this.songs.addAll(songs)
        apiDataAdapter.notifyDataSetChanged()
    }

    override fun onFail(message: String) {
        Log.e(TAG, message)
    }
}