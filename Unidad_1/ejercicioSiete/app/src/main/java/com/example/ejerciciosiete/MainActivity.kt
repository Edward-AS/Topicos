package com.example.ejerciciosiete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnAceptar: Button
    lateinit var nombre: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnAceptar = findViewById(R.id.btnAceptar)
        nombre = findViewById(R.id.nombre)

        btnAceptar.setOnClickListener {
            if (nombre.text.toString() != "") {
                val intent = Intent(this, Ventana2::class.java)
                intent.putExtra("saludoNombre", nombre.text.toString())
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Ingrese su nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

/*
Kotlin

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key_data", "Mi dato a enviar") // Añade un dato
        startActivity(intent) // Inicia la segunda Activity

En la Activity de destino: Usa getIntent().getStringExtra() (o el tipo de dato correspondiente) para recuperar el dato.
Kotlin

        val datoRecibido = intent.getStringExtra("key_data") // Recupera el dato
        // Usa el dato...
 */