package com.monstercode.bigmotherhen.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import timber.log.Timber

class ChapterViewModel(repository: ChapterRepository) : ViewModel() {

    private val _chapterNumberLiveData = MutableLiveData<Int>()

    val chapter: LiveData<Chapter> =
        Transformations.switchMap(_chapterNumberLiveData) { chapterNumber: Int ->
            repository.getChapter(chapterNumber)
        }

    fun setChapterNumber(chapterNumber: Int) {
        _chapterNumberLiveData.value = chapterNumber
    }

}