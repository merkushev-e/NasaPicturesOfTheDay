package ru.gb.lesson1.ui.main.view.mars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.mars_fragment.*
import ru.gb.lesson1.R

import ru.gb.lesson1.databinding.MarsFragmentBinding


class MarsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }

    private var _binding: MarsFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.mars_fragment, container, false)
        _binding = MarsFragmentBinding.bind(view)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }
}
