package com.example.luxmetr.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.luxmetr.databinding.FragmentGraphBinding
import com.example.luxmetr.ui.shared.SharedViewModel

class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        sharedViewModel.luxStatus.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Observe lux data and update graph
        sharedViewModel.luxValue.observe(viewLifecycleOwner) {
            updateGraph()
        }

        return root
    }

    private fun updateGraph() {
        sharedViewModel.luxValue.value?.let {
            binding.graphView.setData(sharedViewModel.getLuxData())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
