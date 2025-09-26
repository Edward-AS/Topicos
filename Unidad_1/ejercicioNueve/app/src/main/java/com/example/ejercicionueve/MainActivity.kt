package com.example.ejercicionueve

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var nombreText: EditText
    lateinit var carreraText: EditText
    lateinit var noControlText: EditText
    lateinit var btnAceptar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nombreText = findViewById(R.id.nombre)
        carreraText = findViewById(R.id.carrera)
        noControlText = findViewById(R.id.noControl)
        btnAceptar = findViewById(R.id.btnEnviar)

        btnAceptar.setOnClickListener {
            if (nombreText.text.toString() != "" && carreraText.text.toString() != "" && noControlText.text.toString() != "") {
                val datosAlumno: Array<String> = arrayOf(
                    nombreText.text.toString(),
                    carreraText.text.toString(),
                    noControlText.text.toString()
                )
                val intent = Intent(this, Ventana2::class.java)
                intent.putExtra("datosAlumno", datosAlumno)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Llena todos los datos para enviar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}