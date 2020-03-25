package com.monstercode.bigmotherhen.chapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.monstercode.bigmotherhen.R
import com.monstercode.bigmotherhen.database.getDatabase
import com.monstercode.bigmotherhen.databinding.FragmentChapterBinding
import com.monstercode.bigmotherhen.databinding.FragmentListBinding
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import timber.log.Timber

class ChapterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChapterBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_chapter, container, false)
        val chapterViewModel: ChapterViewModel = getChapterViewModel()
        binding.chapterViewModel = chapterViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // set appbar title
        chapterViewModel.chapter.observe(viewLifecycleOwner, Observer { chapter: Chapter? ->
            chapter?.let { nonNullChapter: Chapter ->
                setAppBarTitle(nonNullChapter.title)
            }
        })
        return binding.root
    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getChapterViewModel(): ChapterViewModel {
        val activity = requireNotNull(this.activity)
        val database = getDatabase(activity)
        val repository = ChapterRepository(database.chapterDao)
        val arguments = ChapterFragmentArgs.fromBundle(arguments!!)
        val chapterNumber = arguments.chapterNumber
        val chapterViewModelFactory = ChapterViewModelFactory(repository)
        val chapterViewModel = ViewModelProviders.of(activity, chapterViewModelFactory)
            .get(ChapterViewModel::class.java)
        chapterViewModel.setChapterNumber(chapterNumber)
        return chapterViewModel
    }

    private fun setAppBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }


}
