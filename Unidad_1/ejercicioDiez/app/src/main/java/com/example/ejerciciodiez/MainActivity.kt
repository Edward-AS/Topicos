package com.example.ejerciciodiez

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var usuario: EditText
    private lateinit var contra: EditText

    private var usuarioNuevo: String? = ""
    private var contraNueva: String? = ""
    private var nombre: String? = ""

    private var contador: Int = 0

    private val registerForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            usuarioNuevo = data?.getStringExtra("usuario")
            contraNueva = data?.getStringExtra("contra")
            nombre = data?.getStringExtra("nombre")

            usuario.setText(usuarioNuevo)
            contra.setText("")
            contador = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        usuario = findViewById(R.id.usuario)
        contra = findViewById(R.id.contra)
    }
    fun nuevoUsuario(view: View) {
        val intentNuevo = Intent(this, VentanaAlta::class.java)
        registerForResult.launch(intentNuevo)
    }

    fun salir(view: View) {
        finish()
    }

    fun entrar(view: View) {
        if (usuarioNuevo != "" && contraNueva != "") {
            if (contador <= 3) {
                if (usuario.text.toString() != "" && contra.text.toString() != "") {
                    if (usuario.text.toString() == usuarioNuevo && contra.text.toString() == contraNueva) {
                        val intentSaludo = Intent(this, VentanaSaludo::class.java)
                        intentSaludo.putExtra("nombre", nombre)
                        startActivity(intentSaludo)
                    } else {
                        Toast.makeText(
                            this, "Usuario o contraseña incorrecto, vuelva a intentarlo de nuevo",
                            Toast.LENGTH_SHORT
                        ).show()
                        contador += 1
                    }
                } else {
                    Toast.makeText(this, "Llene los campos antes de continuar", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Ha itentado muchas veces, vuelva a intentar más tarde",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "Crear un nuevo usuario antes de continuar", Toast.LENGTH_SHORT).show()
        }
    }
}