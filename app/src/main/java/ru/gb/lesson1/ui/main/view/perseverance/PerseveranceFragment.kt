package ru.gb.lesson1.ui.main.view.perseverance

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import coil.api.load
import ru.gb.lesson1.R
import ru.gb.lesson1.databinding.PerseveranceFragmentBinding
import ru.gb.lesson1.ui.main.model.marsApi.MarsRoverServerResponseDTO
import ru.gb.lesson1.ui.main.utils.hide
import ru.gb.lesson1.ui.main.utils.show
import ru.gb.lesson1.ui.main.utils.showSnackBar
import ru.gb.lesson1.ui.main.viewmodel.AppState
import ru.gb.lesson1.ui.main.viewmodel.PerseveranceViewModel

class PerseveranceFragment : Fragment() {

    companion object {
        fun newInstance() = PerseveranceFragment()
    }

    private var counter: Int = 0
    private  val viewModel: PerseveranceViewModel by lazy {
        ViewModelProvider(this).get(PerseveranceViewModel::class.java)
    }

    private var _binding: PerseveranceFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.perseverance_fragment, container, false)
        _binding = PerseveranceFragmentBinding.bind(view)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPerseveranceData()
        viewModel.liveData.observe(viewLifecycleOwner){
            AppState ->
            renderData(AppState, view)
        }
    }



    private fun renderData(appState: AppState?, view: View) {

        when (appState){
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.SuccessMarsRover -> {
                binding.loadingLayout.hide()
                val serverResponse = appState.serverResponseDTO
                setImageAndDescription(counter, serverResponse)
                initButtons(serverResponse)

            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.main.showSnackBar("Error", "Reload", { viewModel.getPerseveranceData() })
            }
        }
    }

    private fun initButtons(serverResponse: MarsRoverServerResponseDTO) {
        binding.buttonNextCam.setOnClickListener {
            nextCamera(serverResponse)
        }
        binding.buttonPrevCam.setOnClickListener {
            prevCamera(serverResponse)
        }
    }
    private fun nextCamera(serverResponse: MarsRoverServerResponseDTO) {
        if(counter < serverResponse.latest_photos.size){
            setImageAndDescription(counter, serverResponse)
            counter += 1

        } else {
            counter = 0;
            setImageAndDescription(counter, serverResponse)
            counter += 1
        }

    }
    private fun prevCamera(serverResponse: MarsRoverServerResponseDTO) {
        if(counter > 0){
            setImageAndDescription(counter, serverResponse)
            counter -= 1

        } else {
            counter = serverResponse.latest_photos.size - 1;
            setImageAndDescription(counter, serverResponse)
            counter -= 1
        }

    }


    private fun setImageAndDescription(counter: Int,serverResponse: MarsRoverServerResponseDTO ){
        val url = serverResponse.latest_photos[counter].img_src.toString()
        binding.roverCamDescription.text = serverResponse.latest_photos[counter].earth_date.toString()
        binding.roverCamDescription.text = serverResponse.latest_photos[counter].camera.full_name.toString()
        binding.roverCamDate.text = serverResponse.latest_photos[counter].earth_date.toString()
        binding.imageViewMarsCam.load(url) {
            lifecycle(this@PerseveranceFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }


}