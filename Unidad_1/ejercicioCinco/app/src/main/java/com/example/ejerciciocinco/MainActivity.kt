package com.example.ejerciciocinco

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnSuma: Button
    lateinit var btnResta: Button
    lateinit var btnMult: Button
    lateinit var btnDivide: Button
    lateinit var btnOpera: Button
    lateinit var resultado: TextView
    lateinit var valorUno: EditText
    lateinit var valorDos: EditText

    //Varibles para almacenar los valores
    var val1: Double = 0.0
    var val2: Double = 0.0
    var resulta: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        valorUno = findViewById(R.id.varUno)
        valorDos = findViewById(R.id.varDos)

        btnSuma = findViewById(R.id.btnSuma)
        btnResta = findViewById(R.id.btnResta)
        btnMult = findViewById(R.id.btnMultip)
        btnDivide = findViewById(R.id.btnDivide)

        btnOpera = findViewById(R.id.btnOper)
        resultado = findViewById(R.id.resultado)

    }

    fun getNum(){
        val valUnoText = valorUno.text.toString()
        val valDosText = valorDos.text.toString()
        if (valUnoText.toDoubleOrNull() != null && valDosText.toDoubleOrNull() != null) {
            val1 = valUnoText.toDouble()
            val2 = valDosText.toDouble()
        }
        else {
            Toast.makeText(this, "Los valores deben de ser un número válido", Toast.LENGTH_SHORT).show()
        }
    }

    fun opera(view: View){
        btnOpera.setOnClickListener {
            resultado.text = resulta.toString()
        }
    }

    fun sumar(view: View){
        getNum()
        resulta = val1 + val2
    }

    fun restar(view: View){
        getNum()
        resulta = val1 - val2
    }

    fun mult(view: View){
        getNum()
        resulta = val1 * val2
    }

    fun divide(view: View){
        getNum()
        if (val2 != 0.0) {
            resulta = val1 / val2
        }
        else {
            Toast.makeText(this, "ERROR: No es posible dividir entre cero", Toast.LENGTH_SHORT).show()
        }
    }
}