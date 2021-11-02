package ru.gb.lesson1.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import ru.gb.lesson1.R
import ru.gb.lesson1.databinding.SettingsFragmentBinding

const val NIGHT_MODE_KEY = "NIGHT_MODE_KEY"
class SettingsFragment : Fragment() {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNightModeOption()
    }

    private fun initNightModeOption() {
        binding.darkModeSwitch.isChecked = requireActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(NIGHT_MODE_KEY, false)

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            sharedPreferences?.let {
                val editor = it.edit()
                editor.putBoolean(NIGHT_MODE_KEY, isChecked)
                editor.apply()
            }

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}