> KotlinAsyncAPI

> Start Async task

```kotlin
    private val url =
    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
    val asyncTask = AsyncAPIClass(this)
    asyncTask.execute(url)

```
> Entity

```kotlin
class SongEntity {
    var title = ""
    var updated: String? = null
    var name = ""
    var artist = ""
    var rights = ""
    var releaseDate = ""
    override fun toString(): String {
        return "SongEntity(updated=$updated, title='$title', name='$name', artist='$artist', rights='$rights', releaseDate='$releaseDate')"
    }

}
```

> AsyncAPIClass


```kotlin

class AsyncAPIClass(private val activityCallBack: APIInterface) :
    AsyncTask<String, String, ArrayList<SongEntity>>() {
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

```



> Parser 


```kotlin

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
```