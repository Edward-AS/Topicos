package com.example.examen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var tiempo: TextView
    lateinit var text1: TextView
    lateinit var text2: TextView
    lateinit var text3: TextView
    lateinit var text4: TextView
    lateinit var text5: TextView
    lateinit var text6: TextView
    lateinit var text7: TextView
    lateinit var text8: TextView
    lateinit var text9: TextView

    lateinit var btnX: Button
    lateinit var btnO: Button
    lateinit var btnLimpiar: Button

    lateinit var nombreUno: EditText
    lateinit var nombreDos: EditText
    private var tiempoIncio: Long = 0L
    private var marcaActual: String = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text1 = findViewById(R.id.textView1)
        text2 = findViewById(R.id.textView2)
        text3 = findViewById(R.id.textView3)
        text4 = findViewById(R.id.textView4)
        text5 = findViewById(R.id.textView5)
        text6 = findViewById(R.id.textView6)
        text7 = findViewById(R.id.textView7)
        text8 = findViewById(R.id.textView8)
        text9 = findViewById(R.id.textView9)

        btnX = findViewById(R.id.btnUno)
        btnO = findViewById(R.id.btnDos)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        nombreUno = findViewById(R.id.jugadorUno)
        nombreDos = findViewById(R.id.jugadorDos)

        tiempo = findViewById(R.id.tiempo)


        tiempoIncio = System.currentTimeMillis()

        tiempo.post(object : Runnable {
            override fun run() {
                val elapsedMillis = System.currentTimeMillis() - tiempoIncio
                val formattedTime = String.format(
                    "Tiempo: %02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                    TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedMillis))
                )
                tiempo.text = formattedTime

                tiempo.postDelayed(this, 1000)
            }
        })

        btnX?.setOnClickListener {
            marcaActual = "X"
            btnX?.isEnabled = false
            btnO?.isEnabled = true
        }

        btnO?.setOnClickListener {
            marcaActual = "O"
            btnX?.isEnabled = true
            btnO?.isEnabled = false
        }

        text1.setOnClickListener { if (text1.tag == null) { text1.setText(marcaActual); text1.tag = "ocupado" } }
        text2.setOnClickListener { if (text2.tag == null) { text2.setText(marcaActual); text2.tag = "ocupado" } }
        text3.setOnClickListener { if (text3.tag == null) { text3.setText(marcaActual); text3.tag = "ocupado" } }
        text4.setOnClickListener { if (text4.tag == null) { text4.setText(marcaActual); text4.tag = "ocupado" } }
        text5.setOnClickListener { if (text5.tag == null) { text5.setText(marcaActual); text5.tag = "ocupado" } }
        text6.setOnClickListener { if (text6.tag == null) { text6.setText(marcaActual); text6.tag = "ocupado" } }
        text7.setOnClickListener { if (text7.tag == null) { text7.setText(marcaActual); text7.tag = "ocupado" } }
        text8.setOnClickListener { if (text8.tag == null) { text8.setText(marcaActual); text8.tag = "ocupado" } }
        text9.setOnClickListener { if (text9.tag == null) { text9.setText(marcaActual); text9.tag = "ocupado" } }


        btnLimpiar.setOnClickListener {
            text1.setText("")
            text1.tag = null
            text2.setText("")
            text2.tag = null
            text3.setText("")
            text3.tag = null
            text4.setText("")
            text4.tag = null
            text5.setText("")
            text5.tag = null
            text6.setText("")
            text6.tag = null
            text7.setText("")
            text7.tag = null
            text8.setText("")
            text8.tag = null
            text9.setText("")
            text9.tag = null

            btnX.performClick()

            tiempoIncio = System.currentTimeMillis()
        }
    }
}