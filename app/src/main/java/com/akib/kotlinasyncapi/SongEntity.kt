package com.akib.kotlinasyncapi

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