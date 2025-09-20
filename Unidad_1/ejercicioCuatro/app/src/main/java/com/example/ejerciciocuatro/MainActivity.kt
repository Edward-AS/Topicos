package com.example.ejerciciocuatro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.AndroidViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var btnLlenar: Button
    lateinit var btnLimpiar: Button
    lateinit var cuadro: Array<Array<TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLlenar = findViewById(R.id.llenar)
        btnLimpiar = findViewById(R.id.limpiar)

        val id = arrayOf(
            arrayOf(R.id.celda00, R.id.celda01, R.id.celda02),
            arrayOf(R.id.celda10, R.id.celda11, R.id.celda12),
            arrayOf(R.id.celda20, R.id.celda21, R.id.celda22)
        )
        cuadro = Array(3) {fila ->
            Array(3) {col ->
                findViewById(id[fila][col])
            }
        }
/*
        btnLlenar.setOnClickListener{
            cuadro.forEach { fila ->
                fila.forEach { celda ->
                    celda.text = Random.nextInt(101).toString()
                }
            }
        }
        btnLimpiar.setOnClickListener {
            cuadro.forEach { fila ->
                fila.forEach { celda ->
                    celda.text = ""
                }
            }
        }
*/
        //La matriz se pasa a una "lista" con .flatten()
        cuadro.flatten().forEach { celda ->
            celda.setOnClickListener {
                if (celda.text != "") {
                    Toast.makeText(this, "Número: ${celda.text}", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Las celdas están vacías", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun llenar(view: View) {
        llenarUno()
    }

    fun limpiar(view: View) {
        limpiarUno()
    }

    fun llenarUno() {
        btnLlenar.setOnClickListener{
            cuadro.forEach { fila ->
                fila.forEach { celda ->
                    celda.text = Random.nextInt(101).toString()
                }
            }
        }
    }

    fun limpiarUno(){
        btnLimpiar.setOnClickListener {
            cuadro.forEach { fila ->
                fila.forEach { celda ->
                    celda.text = ""
                }
            }
        }
    }
}