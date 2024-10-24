package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var gyroSensor: Sensor? = null
    lateinit var lightText : TextView
    lateinit var pressureText: TextView
    lateinit var gyroImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val list : List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d("RRR","size=${list.size}")
        Log.d("RRR",list.joinToString("\n"))

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        lightText = findViewById(R.id.textView)

        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        pressureText = findViewById(R.id.pressureView)

        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        gyroImage = findViewById(R.id.imageView)

        if(lightSensor!= null) {
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        if(pressureSensor!=null) {
            sensorManager.registerListener(this,pressureSensor,SensorManager.SENSOR_DELAY_GAME)
        }
        if(gyroSensor!=null) {
            sensorManager.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_GAME)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightSensor == null) {
            lightText.text = "No light sensor!"
        } else if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            lightText.text = "${event.values[0]} Lm"
        }
        if(pressureSensor == null) {
            pressureText.text = "No pressure sensor!"
        } else if(event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            pressureText.text = "${event.values[0]*0.75} mm.rt.st."
        }
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            gyroImage.rotation = event.values[2]*10
            gyroImage.rotationX = event.values[0]*10
            gyroImage.rotationY = event.values[1]*10
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
      //
    }
}