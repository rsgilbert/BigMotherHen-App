package com.monstercode.bigmotherhen.list


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.monstercode.bigmotherhen.R
import com.monstercode.bigmotherhen.database.getDatabase
import com.monstercode.bigmotherhen.databinding.FragmentListBinding
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import timber.log.Timber

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        val listViewModel: ListViewModel = getListViewModel()
        binding.listViewModel = listViewModel
        binding.chapterList.adapter =
            ListChapterAdapter(ListChapterAdapter.OnClickListener { chapter: Chapter ->
                listViewModel.startNavigateToChapter(chapter)
            })

        listViewModel.navigateToChapter.observe(viewLifecycleOwner, Observer { chapter: Chapter? ->
            chapter?.let { nonNullChapter: Chapter ->
                val action =
                    ListFragmentDirections.actionListFragmentToChapterFragment2(nonNullChapter.number)
                findNavController().navigate(action)
                listViewModel.navigateToChapterComplete()
            }
        })
        return binding.root

    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getListViewModel(): ListViewModel {
        val activity = requireNotNull(this.activity)
        val database = getDatabase(activity)
        val repository = ChapterRepository(database.chapterDao)
        return ViewModelProviders.of(activity, ListViewModel.FACTORY(repository))
            .get(ListViewModel::class.java)
    }

}
