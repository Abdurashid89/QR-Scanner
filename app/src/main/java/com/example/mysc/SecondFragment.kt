package com.example.mysc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mysc.databinding.FragmentSecondBinding
import com.example.mysc.extension.showMessage
import com.example.mysc.scanner.RedRectOverlay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    private val binding get() = _binding!!
    lateinit var disposable: Disposable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = binding.barcodeView.setBeepSound(true)
            .drawOverlay(RedRectOverlay(context))
            .getObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                activity?.runOnUiThread {
                    if (it.rawValue.length >= 14) {
                        showMessage(it.rawValue)

                    }
                }
            }
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}