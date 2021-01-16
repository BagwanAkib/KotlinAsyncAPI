package com.akib.kotlinasyncapi

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseXMLAPI {
    private val TAG: String = "PARSE_XML_API"
    private var songs = ArrayList<SongEntity>()

    fun parse(xmlData: String): ArrayList<SongEntity> {
        var inEntity = true
        var textValue = ""
        try {
            //instance of parser
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true

            val xmlParser = factory.newPullParser()
            //Parsing xml data
            xmlParser.setInput(xmlData.reader())

            var eventType = xmlParser.eventType

            var song = SongEntity()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xmlParser.name?.toLowerCase()
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == "entry") {
                            inEntity = true
                        }
                    }
                    XmlPullParser.TEXT -> {
                        textValue = xmlParser.text
                    }
                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "parse ending tag $tagName")
                        if (inEntity)
                            when (tagName) {
                                "entry" -> {
                                    songs.add(song)
                                    inEntity = false
                                    song = SongEntity()
                                }
                                "title" -> song.title = textValue
                                "name" -> song.name = textValue
                                "artist" -> song.artist = textValue
                                "updated" -> song.updated = textValue
                                "releaseDate" -> song.releaseDate = textValue
                                "rights" -> song.rights = textValue
                                "image" -> song.image = textValue
                            }
                    }
                }
                eventType = xmlParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return songs
    }
}