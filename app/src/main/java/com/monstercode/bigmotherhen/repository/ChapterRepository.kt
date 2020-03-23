package com.monstercode.bigmotherhen.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.monstercode.bigmotherhen.database.ChapterDao
import com.monstercode.bigmotherhen.database.asDomainModel
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.network.Service
import com.monstercode.bigmotherhen.network.asDatabaseModel
import com.monstercode.bigmotherhen.network.getNetworkService
import kotlinx.coroutines.withTimeout

/**
 * Repository for fetching data from network and room.
 */

class ChapterRepository(private val chapterDao: ChapterDao) {
    val chapters: LiveData<List<Chapter>> = Transformations.map(chapterDao.chapterListLiveData) {
        it.asDomainModel()
    }

    suspend fun refreshChapters() {
        try {
            val chapters = withTimeout(5_000) {
                getNetworkService().fetchChapters()
            }
            chapterDao.insertAll(chapters.asDatabaseModel())
        } catch (cause: Throwable) {
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
