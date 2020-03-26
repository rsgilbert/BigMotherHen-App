package com.monstercode.bigmotherhen.network

import com.monstercode.bigmotherhen.database.DatabaseChapter

data class NetworkChapterList(val chapters: List<NetworkChapter>)

data class NetworkChapter(
    val number: Int,
    val title: String,
    val content: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkChapterList.asDatabaseModel(): List<DatabaseChapter> {
    return chapters.map { chapter ->
        DatabaseChapter(
            number = chapter.number,
            title = chapter.title,
            content = chapter.content
        )
    }
}