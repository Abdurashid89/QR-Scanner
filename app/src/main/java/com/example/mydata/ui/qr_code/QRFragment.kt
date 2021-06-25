package com.example.mydata.ui.qr_code

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bobekos.bobek.scanner.BarcodeView
import com.example.mydata.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import uz.ssd.egaz.ui.scanner.RedRectOverlay

@AndroidEntryPoint
class QRFragment : Fragment() {

    private val viewModel: QRViewModel by viewModels()
    lateinit var barcodeView: BarcodeView
    lateinit var tv: TextView

    lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()
        if (!isCameraPermissionGranted()) {
            requestCameraPermission()
        } else {
            cameraScanner()
        }
    }

    private fun cameraScanner() {
        disposable = barcodeView.setBeepSound(true)
            .drawOverlay(RedRectOverlay(context))
            .getObservable()
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe {
                activity?.runOnUiThread {
//                    Toast.makeText(requireContext(), it.rawValue, Toast.LENGTH_SHORT).show()
                    /*  val barcode = Regex(pattern = """\d+""")
                          .findAll(input = it.rawValue)

                      val result = StringBuilder()
                      for (matchedText in barcode) {
                          if (matchedText.value.length == 17){
                              result.append(matchedText.value + " ")
                          }
                      }*/

                    val bundle = Bundle()
                    bundle.putString("code", it.rawValue)
                    bundle.putBoolean("result", true)
                    findNavController().navigate(R.id.navigation_home, bundle)

                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(viewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        barcodeView = root.findViewById(R.id.barcodeView)
        /* QRViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.sendQrCode("")


/*        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.text.collect {
                when (it) {
                    is Info.SUCCESS -> {
                    }
                    is Info.ERROR -> {
                    }
                    is Info.LOADING -> {
                    }
                    is Info.EMPTY -> {
                    }
                }
            }
        }*/
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (!isCameraPermissionGranted()) {
                Toast.makeText(requireContext(), "permissin denied", Toast.LENGTH_SHORT).show()
            } else {
                cameraScanner()
            }

        }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }


    companion object {
        val REQUEST_CAMERA_PERMISSION = 101
    }
}