package com.example.ejerciciosiete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnPagina2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnPagina2 = findViewById(R.id.btnPagSig)

        btnPagina2.setOnClickListener {
            val intent = Intent(this, ventana2::class.java)
            startActivity(intent)
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