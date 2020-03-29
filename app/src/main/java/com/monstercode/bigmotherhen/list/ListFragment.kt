package com.monstercode.bigmotherhen.list


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import com.monstercode.bigmotherhen.R
import com.monstercode.bigmotherhen.database.getDatabase
import com.monstercode.bigmotherhen.databinding.FragmentListBinding
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import com.monstercode.bigmotherhen.util.getLastSeenChapterNumber
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


        // set RecyclerView adapter
        binding.chapterList.adapter =
            ListChapterAdapter(ListChapterAdapter.OnClickListener { chapter: Chapter ->
                listViewModel.startNavigateToChapter(chapter)
            })


        listViewModel.navigateToChapter.observe(this) { chapter: Chapter? ->
            chapter?.let { nonNullChapter: Chapter ->
                val action =
                    ListFragmentDirections.actionListFragmentToChapterFragment(nonNullChapter.number)
                findNavController().navigate(action)
                listViewModel.navigateToChapterComplete()
            }
        }

        // show snackBar if no connection and no contacts
        listViewModel.showSnackBar.observe(this) { shouldShow : Boolean? ->
            if(shouldShow == true) {
                Snackbar.make(binding.root, "No internet", Snackbar.LENGTH_LONG).show()
                listViewModel.showSnackBarComplete()
            }
        }

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
