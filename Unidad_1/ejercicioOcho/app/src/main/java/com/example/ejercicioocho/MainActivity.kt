package com.example.ejercicioocho

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun abrirApp(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        }
        else {
            Toast.makeText(this, "La aplicación no se encuentra instalada", Toast.LENGTH_SHORT).show()
        }
    }

    fun ejercicio1(view: View) {
        abrirApp("com.example.helloworld")
    }

    fun ejercicio2(view: View) {
        abrirApp("com.example.trabajodos")
    }

    fun ejercicio3(view: View) {
        abrirApp("com.example.ejercicio_tres")
    }

    fun ejercicio4(view: View) {
        abrirApp("com.example.ejerciciocuatro")
    }

    fun ejercicio5(view: View) {
        abrirApp("com.example.ejerciciocinco")
    }

    fun ejercicio6(view: View) {
        abrirApp("com.example.ejercicioseis")
    }

    fun ejercicio7(view: View) {
        abrirApp("com.example.ejerciciosiete")
    }
}
