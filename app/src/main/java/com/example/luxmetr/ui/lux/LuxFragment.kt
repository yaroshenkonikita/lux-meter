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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

        sharedViewModel.luxValue.observe(viewLifecycleOwner) { it ->
            "Lux Value: $it".also { luxValueTextView.text = it }
        }

        sharedViewModel.luxStatus.observe(viewLifecycleOwner) { it ->
            "Status: $it".also { luxStatusTextView.text = it }
        }

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        handler.post(updateLuxData)

        return root
    }

//    override fun onResume() {
//        super.onResume()
//        lightSensor?.also { sensor ->
//            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
//        }
//        handler.post(updateLuxData)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        sensorManager.unregisterListener(this)
//        handler.removeCallbacks(updateLuxData)
//    }

    override fun onDestroyView() {
        sensorManager.unregisterListener(this)
        handler.removeCallbacks(updateLuxData)
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
}