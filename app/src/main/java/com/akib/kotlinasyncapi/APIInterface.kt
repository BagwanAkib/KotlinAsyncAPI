package com.akib.kotlinasyncapi

interface APIInterface {
    fun onStartAPI()
    fun onSuccess(message: String, songs: ArrayList<SongEntity>)
    fun onFail(message: String)
}