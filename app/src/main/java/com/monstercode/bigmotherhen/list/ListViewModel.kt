package com.monstercode.bigmotherhen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import com.monstercode.bigmotherhen.repository.RefreshError
import com.monstercode.bigmotherhen.util.singleArgViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class ListViewModel(private val repository: ChapterRepository) : ViewModel() {
    init {
        refreshChapters()
    }

//    var chapters =  listOf(Chapter(1, "d", "f", "k"), Chapter(2, "kd", "ff", "ak"))
    var chapters = repository.chapterList

    private val _navigateToChapter = MutableLiveData<Chapter>()
    val navigateToChapter: LiveData<Chapter>
        get() = _navigateToChapter


    private val _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar : LiveData<Boolean>
        get() = _showSnackBar


    private fun refreshChapters() {
        viewModelScope.launch {
            try {
                repository.refreshChapters()
            } catch (e: RefreshError) {
                Timber.e("Error refreshing: ${e.message}")
                showSnackBarStart()
            }
        }
    }

    fun startNavigateToChapter(chapter: Chapter) {
        _navigateToChapter.value = chapter
    }

    fun navigateToChapterComplete() {
        _navigateToChapter.value = null
    }

    fun showSnackBarStart() {
        _showSnackBar.value = true
    }

    fun showSnackBarComplete() {
        _showSnackBar.value = null
    }
    companion object {
        /**
         * Factory for creating [ListViewModel]
         *
         * @param arg the repository to pass to [ListViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::ListViewModel)
    }
}