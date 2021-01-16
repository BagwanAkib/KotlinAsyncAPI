package com.akib.kotlinasyncapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), APIInterface {

    private val TAG = "MAIN_ACTIVITY"
    private val songUrl =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
    private val albumUrl =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topalbums/limit=%d/xml"
    private var url = songUrl
    private var records: Int = 10
    private var rvAPIData: RecyclerView? = null
    private var songs = ArrayList<SongEntity>()
    private val URL_STRING = "url string"
    private val URL_RECORDS = "url records"
    private var apiDataAdapter = SongsAdapter(songs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(URL_STRING).toString()
            records = savedInstanceState.getInt(URL_RECORDS)
        }
        executeAPI()
        setContentView(R.layout.activity_main)
        rvAPIData = findViewById(R.id.rvAPIData)
        rvAPIData?.layoutManager = LinearLayoutManager(this)
        rvAPIData?.adapter = apiDataAdapter
        updateTitle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_STRING, url)
        outState.putInt(URL_RECORDS, records)
    }

    private fun executeAPI() {
        val asyncTask = AsyncAPIClass(this)
        asyncTask.execute(url.format(records))
    }

    private fun updateTitle() {
        findViewById<TextView>(R.id.txt_heading).text =
            if (url != albumUrl)
                "API Songs with $records"
            else
                "API Album with $records"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var isChanged = false
        when (item.itemId) {
            R.id.mn_songs -> {
                if (songUrl != url) {
                    url = songUrl
                    isChanged = true
                }
            }
            R.id.mn_albam -> {
                if (albumUrl != url) {
                    url = albumUrl
                    isChanged = true
                }
            }
            R.id.mn_10_records -> {
                if (records != 10) {
                    records = 10
                    isChanged = true
                }
            }
            R.id.mn_25_records -> {
                if (records != 25) {
                    records = 25
                    isChanged = true
                }
            }
            R.id.mn_refresh -> {
                isChanged = true
            }
        }
        if (isChanged) {
            executeAPI()
            updateTitle()
        }
        return true
    }

    override fun onSuccess(message: String, songs: ArrayList<SongEntity>) {
        Log.e(TAG, message)
        Log.e(TAG, "total songs ${songs.size}")
        this.songs.clear()
        this.songs.addAll(songs)
        apiDataAdapter.notifyDataSetChanged()
        Toast.makeText(this, "API Call Complete", Toast.LENGTH_LONG).show()
    }

    override fun onFail(message: String) {
        val toast: Toast = Toast.makeText(this, "API Call Complete", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.show()
        Log.e(TAG, message)
    }

    override fun onStartAPI() {
        val toast: Toast = Toast.makeText(this, "API Call start", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}