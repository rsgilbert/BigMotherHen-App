package com.monstercode.bigmotherhen.network

import com.monstercode.bigmotherhen.database.DatabaseChapter
import com.monstercode.bigmotherhen.domain.Chapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkChapterList(val chapters: List<NetworkChapter>)

@JsonClass(generateAdapter = true)
data class NetworkChapter(
    val number: Int,
    val title: String,
    val content: String,
    val picture: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkChapterList.asDatabaseModel(): List<DatabaseChapter> {
    return chapters.map { chapter ->
        DatabaseChapter(
            number = chapter.number,
            title = chapter.title,
            content = chapter.content,
            picture = chapter.picture
        )
    }
}