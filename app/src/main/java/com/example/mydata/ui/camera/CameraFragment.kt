package com.example.mydata.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mydata.R
import com.example.mydata.databinding.FragmentDashboardBinding
import com.example.mydata.databinding.FragmentHomeBinding
import com.example.mydata.ui.qr_code.QRFragment
import com.example.mydata.util.PathUtil
import com.example.mydata.util.checkPermission
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

@AndroidEntryPoint
class CameraFragment : Fragment() {

    private val cameraViewModel: CameraViewModel by viewModels()

    private var mCameraSource by Delegates.notNull<CameraSource>()
    private var textRecognizer by Delegates.notNull<TextRecognizer>()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("fragment null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        /* cameraViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/
        //   checkPermission(Manifest.permission.CAMERA) { imagePicker() }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

//        val textView = view.findViewById(R.id.surface_camera_preview)
        textRecognizer = TextRecognizer.Builder(requireContext()).build()
        if (!textRecognizer.isOperational) {
           // toast("failed")
            return
        }

        mCameraSource = CameraSource.Builder(requireContext(), textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()

        binding.surfaceCameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

                try {
                    if (isCameraPermissionGranted()) {
                        mCameraSource.start(binding.surfaceCameraPreview.holder)
                    } else {
                        requestCameraPermission()
                    }
                } catch (e: Exception) {
                    toast("Error:  ${e.message}")
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mCameraSource.stop()
            }


        })



        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems

                if (items.size() <= 0) {
                    return
                }

                binding.surfaceCameraPreview.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }
                     toast(stringBuilder.toString())
                    regexPasportSeria(stringBuilder.toString())
                }
            }
        })
    }

    private fun regexPasportSeria(seria: String) {
        val regex = "[A-Z]{2}[0-9]{7}".toRegex()
        val ser = regex.find(seria)

       if (ser?.value?.length ==9){
           val bundle = Bundle()
           bundle.putString("image", ser.value)
           findNavController().navigate(R.id.navigation_home, bundle)
           println(ser.value)
       }
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }


    private fun imagePicker() {
        ImagePicker.with(this)
            .cameraOnly()
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(100 ?: 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 100) {
            val uri = data?.data ?: return
            Log.d("ImageResult", "$uri")
            val path = PathUtil.getPath(context, uri)


            val bundle = Bundle()
            bundle.putString("image", path)
            findNavController().navigate(R.id.navigation_home, bundle)
        }

    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            QRFragment.REQUEST_CAMERA_PERMISSION
        )
    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

}