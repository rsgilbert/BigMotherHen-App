package com.monstercode.bigmotherhen.chapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monstercode.bigmotherhen.repository.ChapterRepository

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 */
class ChapterViewModelFactory(
    private val repository: ChapterRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChapterViewModel::class.java)) {
            return ChapterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
