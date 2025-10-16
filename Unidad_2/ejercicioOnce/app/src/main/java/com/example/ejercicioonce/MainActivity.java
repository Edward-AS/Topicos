package com.example.ejercicioonce;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText ed1, ed2, ed3, ed4; // Solo se declaran aquellos que reciben datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
    }
    // Datos de alta los usuarios
    public void alta(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = ed1.getText().toString();
        String nombre = ed2.getText().toString();
        String carrera = ed3.getText().toString();
        String semestre = ed4.getText().toString();

        ContentValues registro = new ContentValues(); // Se crea un contenedor (pila)
        registro.put("clave", dni);
        registro.put("nombre", nombre);
        registro.put("carrera", carrera);
        registro.put("semestre", semestre);

        bd.insert("usuario", null, registro);
        bd.close();

        ed1.setText(""); ed2.setText(""); ed3.setText(""); ed4.setText("");

        Toast.makeText(this, "Datos del usuario cargados", Toast.LENGTH_SHORT).show();
    }

    public void consulta(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
//        String dni = ed1.getText().toString();
//        Cursor fila = bd.rawQuery("select nombre, ciudad, numero from usuario where clave = " + dni, null);
        Cursor fila = bd.rawQuery("select * from usuario", null);

        if (fila.moveToFirst()) {
            ed2.setText(fila.getString(1));
            ed3.setText(fila.getString(2));
            ed4.setText(fila.getString(3));
        } else {
            Toast.makeText(this, "No existe ningún usuario con ese dni", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void baja(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = ed1.getText().toString();
        // Se borra borra la base de datos del usuario por el dni
        int cant = bd.delete("usuario", "clave = " + dni, null);
        bd.close();
        ed1.setText(""); ed2.setText(""); ed3.setText(""); ed4.setText("");
        if (cant == 1) {
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = ed1.getText().toString();
        String nombre = ed2.getText().toString();
        String carrera = ed3.getText().toString();
        String semestre = ed4.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("carrera", carrera);
        registro.put("semestre", semestre);

        int cant = bd.update("usuario", registro, "clave = " + dni, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Datos modificados con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    public void salir(View view) {
        finish();
    }
}