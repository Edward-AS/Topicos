package com.example.ejerciciosiete

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ventana2 : AppCompatActivity() {
    lateinit var btnRegresar: Button
    lateinit var saludo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventana2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegresar = findViewById(R.id.btnRegresar)
        saludo = findViewById(R.id.saludo)

        var nombre = intent.getStringExtra("saludoNombre")

        nombre = "Hola $nombre, cómo estás?"

        saludo.text = nombre

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}