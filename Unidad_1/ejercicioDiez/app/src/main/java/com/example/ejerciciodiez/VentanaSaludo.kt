package com.example.ejerciciodiez

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VentanaSaludo : AppCompatActivity() {
    private lateinit var btnSalir: Button
    private lateinit var saludo: TextView
    private lateinit var mensaje: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventana_saludo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnSalir = findViewById(R.id.btnSalirSaludo)
        saludo = findViewById(R.id.saludo)

        val nombre = intent.getStringExtra("nombre")

        mensaje = "Hola $nombre, cómo estás?"
        saludo.text = mensaje
    }

    fun salir(view: View) {
        finish()
    }
}