package com.example.ejerciciodoce;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VentanaAlta extends AppCompatActivity {
    private EditText noCon, nom, user, contra, tel, correo, palSecreta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_alta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noCon = (EditText) findViewById(R.id.noControl);
        nom  = (EditText) findViewById(R.id.nombre);
        user = (EditText) findViewById(R.id.usuario);
        contra = (EditText) findViewById(R.id.contra);
        tel = (EditText) findViewById(R.id.telefono);
        correo = (EditText) findViewById(R.id.correo);
        palSecreta = (EditText) findViewById(R.id.pSecreta);
    }

    public void aceptarAlta(View view) {
        String noControl = noCon.getText().toString().trim();
        String nombre = nom.getText().toString().trim();
        String usuario = user.getText().toString().trim();
        String contrasena = contra.getText().toString().trim();
        String telefono = tel.getText().toString().trim();
        String email = correo.getText().toString().trim();
        String pSecreta = palSecreta.getText().toString().trim();

        if (!noControl.isEmpty() && !nombre.isEmpty() && !usuario.isEmpty() && !contrasena.isEmpty() && !telefono.isEmpty() && !email.isEmpty() && !pSecreta.isEmpty()) {

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("noControl", noControl);
            registro.put("nombre", nombre);
            registro.put("username", usuario);
            registro.put("contra", contrasena);
            registro.put("telefono", telefono);
            registro.put("correo", email);
            registro.put("pSecreta", pSecreta);

            bd.insert("usuario", null, registro);
            bd.close();

            noCon.setText("");
            nom.setText("");
            user.setText("");
            contra.setText("");
            tel.setText("");
            correo.setText("");
            palSecreta.setText("");
            //REMPLAZAR ESTO PARA MOSTRARLO EN LA PANTALLA DE INICIO
            //Toast.makeText(this, "Usuario creado de manera exitosa", Toast.LENGTH_SHORT).show();
            mostrarUsuariosEnBD();

            Intent intent = new Intent().putExtra("usuario", usuario);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Complete los campos para continuar", Toast.LENGTH_SHORT).show();
        }
    }

    public void salir(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    // Method para depurar y ver los usuarios guardados en la base de datos
    private void mostrarUsuariosEnBD() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        Cursor cursor = bd.rawQuery("SELECT username, contra FROM usuario", null);

        if (cursor.moveToFirst()) {
            do {
                String usuarioBD = cursor.getString(0);
                String contraBD = cursor.getString(1);
                Log.d("DB_DEBUG", "Usuario guardado: '" + usuarioBD + "', Contraseña: '" + contraBD + "'");
            } while (cursor.moveToNext());
        } else {
            Log.d("DB_DEBUG", "No hay usuarios en la base de datos todavía");
        }

        cursor.close();
        bd.close();
    }
}