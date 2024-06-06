package com.example.luxmetr.ui.lux

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.luxmetr.R
import com.example.luxmetr.databinding.FragmentLuxBinding
import com.example.luxmetr.ui.shared.SharedViewModel

class LuxFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentLuxBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val handler = Handler(Looper.getMainLooper())

    private var luxValue: Int = 0
    private var init: Boolean = false

    private val updateLuxData = object : Runnable {
        override fun run() {
            sharedViewModel.setLuxValue(luxValue)
            handler.postDelayed(this, 20)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val luxValueTextView: TextView = binding.textLuxValue
        val luxStatusTextView: TextView = binding.textLuxStatus
        val roomTypeSpinner: Spinner = binding.roomTypeSpinner
        val luxStateTextView: TextView = binding.textLuxState

        sharedViewModel.luxValue.observe(viewLifecycleOwner) { it ->
            "Lux Value: $it".also { luxValueTextView.text = it }
            updateLuxState(it, roomTypeSpinner.selectedItem.toString(), luxStateTextView)
        }

        sharedViewModel.luxStatus.observe(viewLifecycleOwner) { it ->
            "Status: $it".also { luxStatusTextView.text = it }
        }

        roomTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateLuxState(luxValue, roomTypeSpinner.selectedItem.toString(), luxStateTextView)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (!init) {
            lightSensor?.also { sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
            handler.post(updateLuxData)
            init = true
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            luxValue = event.values[0].toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something if sensor accuracy changes
    }

    private fun updateLuxState(luxValue: Int, roomType: String, stateTextView: TextView) {
        val state = when (roomType) {
            "Living Room" -> when {
                luxValue < 100 -> "Dim"
                luxValue < 300 -> "Normal"
                else -> "Bright"
            }
            "Bedroom" -> when {
                luxValue < 50 -> "Dim"
                luxValue < 150 -> "Normal"
                else -> "Bright"
            }
            "Office" -> when {
                luxValue < 200 -> "Dim"
                luxValue < 500 -> "Normal"
                else -> "Bright"
            }
            "Kitchen" -> when {
                luxValue < 150 -> "Dim"
                luxValue < 400 -> "Normal"
                else -> "Bright"
            }
            else -> "Unknown"
        }
        "State: $state".also { stateTextView.text = it }
    }
}
