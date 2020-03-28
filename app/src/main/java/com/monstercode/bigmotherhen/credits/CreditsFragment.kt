package com.monstercode.bigmotherhen.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.monstercode.bigmotherhen.R
import com.monstercode.bigmotherhen.databinding.FragmentCreditsBinding

class CreditsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreditsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_credits, container, false)
        return binding.root
    }
}