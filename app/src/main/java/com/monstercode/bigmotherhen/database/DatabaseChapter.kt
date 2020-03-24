package com.monstercode.bigmotherhen.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monstercode.bigmotherhen.domain.Chapter

@Entity
data class DatabaseChapter constructor(
    @PrimaryKey
    val number: Int,
    val title: String,
    val content: String,
    val picture: String
) {
    fun asDomainModel() : Chapter {
        return Chapter(number = number, title = title, content = content, picture = picture)
    }
}

/**
 * Map DatabaseChapter to domain object
 */
fun List<DatabaseChapter>.asDomainModel(): List<Chapter> {
    return map { chapter ->
        chapter.asDomainModel()
    }
}