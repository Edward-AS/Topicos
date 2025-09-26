package com.example.ejerciciodiez

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VentanaAlta : AppCompatActivity() {
    private lateinit var nombre: EditText
    private lateinit var usuarioNuevo: EditText
    private lateinit var contraNueva: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventana_alta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nombre = findViewById(R.id.nombre)
        usuarioNuevo = findViewById(R.id.usuarioNuevo)
        contraNueva = findViewById(R.id.contraNueva)
    }
    fun aceptar(view: View) {
        if (nombre.text.toString() != "" && usuarioNuevo.text.toString() != "" && contraNueva.text.toString() != "") {
            val resultIntent = Intent().apply {
                putExtra("nombre", nombre.text.toString())
                putExtra("usuario", usuarioNuevo.text.toString())
                putExtra("contra", contraNueva.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        } else {
            Toast.makeText(this, "Llene los campos antes de continuar", Toast.LENGTH_SHORT).show()
        }
    }

    fun salir(view: View) {
        finish()
    }
}