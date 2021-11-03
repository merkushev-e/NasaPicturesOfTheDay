package ru.gb.lesson1.ui.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import ru.gb.lesson1.R
import ru.gb.lesson1.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme()

        setContentView(binding.root)

        initBottomNavigationView()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun initBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commit()
                    true
                }
                R.id.explore ->{
                    Toast.makeText(this, "Explore", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.settings ->{
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentTheme() {
        if (getPreferences(Context.MODE_PRIVATE).getBoolean(NIGHT_MODE_KEY, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}