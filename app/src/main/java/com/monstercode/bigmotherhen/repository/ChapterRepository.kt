package com.monstercode.bigmotherhen.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.monstercode.bigmotherhen.database.ChapterDao
import com.monstercode.bigmotherhen.database.asDomainModel
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.network.*
import kotlinx.coroutines.withTimeout
import timber.log.Timber

/**
 * Repository for fetching data from network and room.
 */

class ChapterRepository(private val chapterDao: ChapterDao) {
    val chapterList: LiveData<List<Chapter>> = Transformations.map(chapterDao.chapterListLiveData) {
        it.asDomainModel()
    }

    fun getChapter(number: Int) : LiveData<Chapter> = Transformations.map(chapterDao.getChapter(number)) {
        it.asDomainModel()
    }

    suspend fun refreshChapters() {
        try {
            val chapters: List<NetworkChapter> = getNetworkService().fetchChapters()
            chapterDao.insertAll(NetworkChapterList(chapters).asDatabaseModel())
        } catch (cause: Throwable) {
            Timber.e("Unable to refresh: $cause")
            throw RefreshError("Unable to fetch chapters", cause)
        }
    }
}

/**
 * Thrown when there was an error fetching data
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class RefreshError(message: String, cause: Throwable?) : Throwable(message, cause)
