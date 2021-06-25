package com.example.mydata.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mydata.R
import com.example.mydata.databinding.FragmentHomeBinding
import com.example.mydata.util.UIState
import com.google.android.gms.common.util.Hex
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("fragment null")
    private var code: String? = ""
    private var image: String? = ""
    private var result = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        code = arguments?.getString("code").toString()
        image = arguments?.getString("image").toString()
        // result = arguments?.getBoolean("code")!!

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        //    Toast.makeText(requireContext(), code, Toast.LENGTH_SHORT).show()


        if (code != "null") {
            binding.textHome.text = code
        }

        if (image != "null") {
            binding.textHome.text = "Seria number : $image"
        }


//        binding.textHome.isVisible = result


//        homeViewModel.checkBarCode(code)
//        homeViewModel.checkImageBarCode(image)

/*        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.barcodeFlow.collect {

                when (it) {
                    is UIState.SUCCESS -> {
                        Log.d("barcode", it.data)
                    }
                    is UIState.ERROR -> {
                        Log.d("barcode", it.message)
                    }
                    is UIState.LOADING -> {
                        Log.d("barcode", it.toString())
                    }
                    is UIState.EMPTY -> {
                        Log.d("barcode", it.toString())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.imageFlow.collect {

                when (it) {
                    is UIState.SUCCESS -> {
                        Log.d("image_bar_code", it.data)
                    }
                    is UIState.ERROR -> {
                        Log.d("image_bar_code", it.message)
                    }
                    is UIState.LOADING -> {
                        Log.d("image_bar_code", it.toString())
                    }
                    is UIState.EMPTY -> {
                        Log.d("image_bar_code", it.toString())
                    }
                }
            }
        }*/


        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }
}