package com.example.ejercicioseis

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    lateinit var radioGruop: RadioGroup
    lateinit var rbCirculo: RadioButton
    lateinit var rbTriangulo: RadioButton
    lateinit var rbRectangulo: RadioButton
    lateinit var rbTrapecio: RadioButton
    lateinit var btnCalcular: Button

    lateinit var datosCirculo: LinearLayout
    lateinit var datosRectTri: LinearLayout
    lateinit var datosTrapecio: LinearLayout

    lateinit var radio: EditText
    lateinit var baseFig: EditText
    lateinit var altura: EditText
    lateinit var baseMayor: EditText
    lateinit var baseMenor: EditText
    lateinit var alturaTrapecio: EditText

    lateinit var resultado: TextView

    var area: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGruop = findViewById(R.id.radioGroup)
        rbCirculo = findViewById(R.id.rbCirculo)
        rbTriangulo = findViewById(R.id.rbTriangulo)
        rbRectangulo = findViewById(R.id.rbRectangulo)
        rbTrapecio = findViewById(R.id.rbTrapecio)

        datosCirculo = findViewById(R.id.datosCirculo)
        datosRectTri = findViewById(R.id.datosRectTri)
        datosTrapecio = findViewById(R.id.datosTrapecio)

        radio = findViewById(R.id.radio)

        baseFig = findViewById(R.id.baseFig)
        altura = findViewById(R.id.altura)

        baseMayor = findViewById(R.id.baseMayor)
        baseMenor = findViewById(R.id.baseMenor)
        alturaTrapecio = findViewById(R.id.alturaTrapecio)

        btnCalcular = findViewById(R.id.calcular)
        resultado = findViewById(R.id.resultado)


        radioGruop.setOnCheckedChangeListener { _, checkedId ->
            datosCirculo.visibility = View.GONE
            datosRectTri.visibility = View.GONE
            datosTrapecio.visibility = View.GONE

            when (checkedId) {
                R.id.rbCirculo -> datosCirculo.visibility = View.VISIBLE
                R.id.rbTriangulo -> datosRectTri.visibility = View.VISIBLE
                R.id.rbRectangulo -> datosRectTri.visibility = View.VISIBLE
                R.id.rbTrapecio -> datosTrapecio.visibility = View.VISIBLE
            }
        }
    }

    fun opera(view: View) {
        btnCalcular.setOnClickListener {
            when {
                rbCirculo.isChecked -> circulo()
                rbTriangulo.isChecked -> rectangulo(triangulo = true)
                rbRectangulo.isChecked -> rectangulo()
                rbTrapecio.isChecked -> trapecio()
            }
            resultado.text = area.toString()
        }
    }

    fun circulo() {
        val valRadio = radio.text.toString().toDoubleOrNull()
        if (valRadio != null) {
            area = PI * valRadio.pow(2)
        }
        else {
            Toast.makeText(this, "El radio debe de ser un núemro válido", Toast.LENGTH_SHORT).show()
        }
    }

    fun rectangulo(triangulo: Boolean = false) {
        val valBaseFig = baseFig.text.toString().toDoubleOrNull()
        val valAltura = altura.text.toString().toDoubleOrNull()
        if (valBaseFig != null && valAltura != null) {
            area = valBaseFig * valAltura
            if (triangulo) {
                area /= 2
            }
        }
        else {
            Toast.makeText(this, "Los valores deben de ser un número válido", Toast.LENGTH_SHORT).show()
        }
    }

    fun trapecio() {
        val valBaseMay = baseMayor.text.toString().toDoubleOrNull()
        val valBaseMen = baseMenor.text.toString().toDoubleOrNull()
        val valAltura = alturaTrapecio.text.toString().toDoubleOrNull()
        if (valBaseMay != null && valBaseMen != null && valAltura != null){
            area = (valBaseMay + valBaseMen) * valAltura / 2
        }
        else {
            Toast.makeText(this, "Los valores deben de ser un número válido", Toast.LENGTH_SHORT).show()
        }
    }
}