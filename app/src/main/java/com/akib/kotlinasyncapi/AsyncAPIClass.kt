package com.akib.kotlinasyncapi

import android.os.AsyncTask
import java.io.IOException
import java.net.URL

class AsyncAPIClass(private val activityCallBack: APIInterface) :
    AsyncTask<String, String, ArrayList<SongEntity>>() {

    override fun onPreExecute() {
        activityCallBack.onStartAPI()
    }

    override fun onPostExecute(songs: ArrayList<SongEntity>?) {
        super.onPostExecute(songs)
        if (songs != null) {
            activityCallBack.onSuccess("API data total ${songs.size} records received!", songs)
        }
    }

    override fun doInBackground(vararg url: String?): ArrayList<SongEntity>? {
        try {
            return ParseXMLAPI().parse(getURLData(url[0]!!))
        } catch (e: IOException) {
            e.printStackTrace()
            activityCallBack.onFail(e.message!!)
        } catch (e: Exception) {
            e.printStackTrace()
            activityCallBack.onFail(e.message!!)
        }
        return null
    }

    private fun getURLData(url: String): String {
        return URL(url).readText()
    }
}