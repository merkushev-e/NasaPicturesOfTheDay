package ru.gb.lesson1.ui.main.view.mars

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.gb.lesson1.ui.main.view.curiosity.CuriosityFragment
import ru.gb.lesson1.ui.main.view.perseverance.PerseveranceFragment


class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    companion object{
        private const val PERSEVERANCE_FRAGMENT = 0
        private const val CURIOSITY_FRAGMENT = 1
    }

    private val fragments = arrayOf(PerseveranceFragment(),CuriosityFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[PERSEVERANCE_FRAGMENT]
            1 -> fragments[CURIOSITY_FRAGMENT]
            else -> fragments[PERSEVERANCE_FRAGMENT]
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Perseverance"
            1 -> "Curiosity"
            else -> "Perseverance"
        }
    }
}