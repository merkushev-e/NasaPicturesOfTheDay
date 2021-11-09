package ru.gb.lesson1.ui.main.view.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.gb.lesson1.R
import ru.gb.lesson1.databinding.MainFragmentBinding
import ru.gb.lesson1.ui.main.utils.hide
import ru.gb.lesson1.ui.main.utils.show
import ru.gb.lesson1.ui.main.utils.showSnackBar
import ru.gb.lesson1.ui.main.view.view.BottomNavigationDrawerFragment
import ru.gb.lesson1.ui.main.view.MainActivity
import ru.gb.lesson1.ui.main.view.settings.SettingsFragment
import ru.gb.lesson1.ui.main.viewmodel.AppState
import ru.gb.lesson1.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setBottomAppBar(view)
        viewModel.getData()
        viewModel.liveData.observe(viewLifecycleOwner, { AppState ->
            renderData(AppState, view)
        })
        chipGroupInit()

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    private fun chipGroupInit() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.today ->{
                    viewModel.getData()
                }
                R.id.yesterday ->{
                    viewModel.getYesterdayData()
                }
                R.id.before_yesterday ->{
                    viewModel.getBeforeYesterdayData()
                }
            }
        }

    }


    private fun renderData(appState: AppState, view: View) {
        when (appState) {
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Success -> {
                binding.loadingLayout.hide()
                val serverResponseData = appState.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    binding.main.showSnackBar("Server Error", "Reload", { viewModel.getData() })
                } else {
                    image_view.load(url) {
                        lifecycle(this@MainFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    with(view) {
                        findViewById<TextView>(R.id.bottom_sheet_description)
                            .text = serverResponseData.explanation
                        findViewById<TextView>(R.id.bottom_sheet_description_header)
                            .text = serverResponseData.title
                    }
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.main.showSnackBar("Error", "Reload", { viewModel.getData() })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


//    private fun setBottomAppBar(view: View) {
//        val context = activity as MainActivity
//
////        fab.setOnClickListener {
////            if (isMain) {
////                isMain = false
////                bottom_app_bar.navigationIcon = null
////                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
////                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
////                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
////            } else {
////                isMain = true
////                bottom_app_bar.navigationIcon =
////                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
////                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
////                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
////                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
////            }
////        }
//    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
