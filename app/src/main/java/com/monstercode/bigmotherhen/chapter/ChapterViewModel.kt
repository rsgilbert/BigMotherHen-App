package com.monstercode.bigmotherhen.chapter

import androidx.lifecycle.*
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ChapterViewModel(repository: ChapterRepository) : ViewModel() {
    private var chapterCount = -1

    init {
        viewModelScope.launch {
            chapterCount = repository.chapterCount()
        }
    }

    private val _chapterNumberLiveData = MutableLiveData<Int>()

    private val _navigateToNextChapter = MutableLiveData<Int>()

    val navigateToNextChapter: LiveData<Int>
        get() = _navigateToNextChapter

    private val _navigateToCredits = MutableLiveData<Boolean>()
    val navigateToCredits: LiveData<Boolean>
        get() = _navigateToCredits


    val chapter: LiveData<Chapter> =
        Transformations.switchMap(_chapterNumberLiveData) { chapterNumber: Int ->
            repository.getChapter(chapterNumber)
        }

    fun setChapterNumber(chapterNumber: Int) {
        _chapterNumberLiveData.value = chapterNumber
    }

    fun navigateToNextChapterStart() {
        _chapterNumberLiveData.value?.let { number: Int ->
            Timber.i("Chapter count is $chapterCount, number is $number")
            if (number < chapterCount) {
                _navigateToNextChapter.value = number + 1
            }
            // else we are done reading all the chapters
            // so we should navigate to Credits Fragment
            else navigateToCreditsStart()
        }
    }

    fun navigateToNextChapterComplete() {
        _navigateToNextChapter.value = null
    }


    private fun navigateToCreditsStart() {
        _navigateToCredits.value = true
    }

    fun navigateToCreditsComplete() {
        _navigateToCredits.value = null
    }
}