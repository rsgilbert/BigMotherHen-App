package com.monstercode.bigmotherhen.chapter

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.monstercode.bigmotherhen.R
import com.monstercode.bigmotherhen.database.getDatabase
import com.monstercode.bigmotherhen.databinding.FragmentChapterBinding
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository
import com.monstercode.bigmotherhen.util.saveLastSeenChapter
import timber.log.Timber

class ChapterFragment : Fragment() {
    private lateinit var chapterViewModel: ChapterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChapterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_chapter, container, false
        )
        chapterViewModel = getChapterViewModel()
        binding.chapterViewModel = chapterViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // set appbar title
        chapterViewModel.chapter.observe(viewLifecycleOwner, Observer { chapter: Chapter? ->
            chapter?.let { nonNullChapter: Chapter ->
                setAppBarTitle(nonNullChapter)
                saveLastSeenChapter(nonNullChapter.number, context)
            }
            if(chapter == null) {

            }
        })

        // navigate to next chapter
        chapterViewModel.navigateToNextChapter.observe(
            viewLifecycleOwner,
            Observer { number: Int? ->
                number?.let {nonNullNumber: Int ->
                    val action =
                        ChapterFragmentDirections.actionChapterFragmentSelf(nonNullNumber)
                    findNavController().navigate(action)
                    chapterViewModel.navigateToNextChapterComplete()
                }
            })

        // navigate to credits
        chapterViewModel.navigateToCredits.observe(viewLifecycleOwner, Observer { shouldNavigate: Boolean? ->
            if(shouldNavigate == true) {
                val action = ChapterFragmentDirections.actionChapterFragmentToCreditsFragment()
                findNavController().navigate(action)
                chapterViewModel.navigateToCreditsComplete()
            }
        })

        setHasOptionsMenu(true)
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

    private fun setAppBarTitle(chapter: Chapter) {
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.appbar_title, chapter.number, chapter.title)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chapter_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigate_next -> {
                chapterViewModel.navigateToNextChapterStart()
                true
            }
            else -> NavigationUI.onNavDestinationSelected(
                item,
                view!!.findNavController()
            ) || super.onOptionsItemSelected(item)
        }
    }

}
