package com.monstercode.bigmotherhen.list

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import com.monstercode.bigmotherhen.util.singleArgViewModelFactory
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ChapterRepository) : ViewModel() {
    init {
        refreshChapters()
    }

    val chapters =  listOf(Chapter(1, "d", "f", "k"), Chapter(2, "kd", "ff", "ak"))



    private fun refreshChapters() {
        viewModelScope.launch {
//            repository.refreshChapters()
        }
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