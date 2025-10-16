package com.example.exposicion

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var stepsAtStart: Float = -1f
    private var stepsToday: Int = 0

    private lateinit var tvStepsCount: TextView
    private lateinit var tvWeatherTemp: TextView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvWeatherLocation: TextView
    private lateinit var tvAdvice: TextView
    private lateinit var btnRefreshWeather: Button
    private lateinit var progressBar: ProgressBar

    //API Key de OpenWeatherMap
    private val apiKey = "6ab8ec33292cc1667422e0de434b15ec"

    //Ciudad
    private val city = "San Juan del Rio,MX"

    companion object {
        private const val REQUEST_ACTIVITY_RECOGNITION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del layout
        tvStepsCount = findViewById(R.id.tvStepsCount)
        tvWeatherTemp = findViewById(R.id.tvWeatherTemp)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvWeatherLocation = findViewById(R.id.tvWeatherLocation)
        tvAdvice = findViewById(R.id.tvAdvice)
        btnRefreshWeather = findViewById(R.id.btnRefreshWeather)
        progressBar = findViewById(R.id.progressLoading)

        // Inicia el sensor de pasos
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Solicitar permiso de reconocimiento de actividad
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                REQUEST_ACTIVITY_RECOGNITION
            )
        } else {
            startStepSensor()
        }

        // Evento del botón
        btnRefreshWeather.setOnClickListener {
            fetchWeatherData()
        }

        // Carga inicial del clima
        fetchWeatherData()
    }

    // --- SENSOR DE PASOS ---
    private fun startStepSensor() {
        stepCounterSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        } ?: run {
            Toast.makeText(this, "Sensor de pasos no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if (stepsAtStart == -1f) {
                stepsAtStart = event.values[0]
            }
            stepsToday = (event.values[0] - stepsAtStart).toInt()
            tvStepsCount.text = stepsToday.toString()

            // Actualizar consejo
            updateAdvice()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se usa
    }

    override fun onResume() {
        super.onResume()
        stepCounterSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // --- PERMISOS ---
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACTIVITY_RECOGNITION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startStepSensor()
        } else {
            Toast.makeText(this, "Permiso denegado, no se contarán los pasos", Toast.LENGTH_SHORT).show()
        }
    }

    // --- API DEL CLIMA ---
    private fun fetchWeatherData() {
        progressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric&lang=es")
                        .readText()
                val jsonObj = JSONObject(response)
                val main = jsonObj.getJSONObject("main")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getDouble("temp")
                val condition = weather.getString("description")
                val cityName = jsonObj.getString("name")

                withContext(Dispatchers.Main) {
                    tvWeatherTemp.text = "${temp.toInt()}°C"
                    tvWeatherCondition.text = condition.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    }
                    tvWeatherLocation.text = cityName
                    progressBar.visibility = View.GONE

                    updateAdvice(temp, condition)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Error al obtener clima",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // --- CONSEJO SEGÚN CLIMA Y PASOS ---
    private fun updateAdvice(temp: Double = Double.NaN, condition: String = "") {
        var advice = ""

        val normalizedCondition = condition.lowercase()

        advice = when {
            !temp.isNaN() && temp < 5 -> "Hace mucho frío, mejor quédate en casa"
            normalizedCondition.contains("lluvia") -> "Parece que lloverá, lleva paraguas si sales."
            normalizedCondition.contains("tormenta") -> "Hay tormenta eléctrica ⚡, quédate en casa."
            !temp.isNaN() && temp > 35 -> "Hace demasiado calor, hidrátate bien si sales."
            stepsToday >= 8000 -> "¡Excelente! Ya alcanzaste tu meta diaria de pasos"
            else -> "El clima se ve bien, sal a caminar un poco"
        }

        tvAdvice.text = advice
    }
}
