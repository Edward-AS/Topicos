package com.example.ejercicionueve

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ventana2 : AppCompatActivity() {
    lateinit var btnRegresar: Button
    lateinit var datos: TextView
    lateinit var mensaje: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventana2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        datos = findViewById(R.id.datos)
        btnRegresar = findViewById(R.id.btnRegresar)

        val bundle = intent.extras
        val datosAlumno = bundle?.getStringArray("datosAlumno")

        mensaje = "Nombre: ${datosAlumno?.get(0).toString()}\n" +
                "Carrera: ${datosAlumno?.get(1).toString()}\n" +
                "Número de Control: ${datosAlumno?.get(2).toString()}"
        datos.text = mensaje

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}