package com.example.ejerciciodoce;

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

public class VentanaRecuperacion extends AppCompatActivity {

    private EditText user, palSecreta, contra, confirma_contra;
    AdminSQLiteOpenHelper admin;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_recuperacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = (EditText) findViewById(R.id.usuarioRecupera);
        palSecreta = (EditText) findViewById(R.id.pSecretaRecupera);
        contra = (EditText) findViewById(R.id.contraRecupera);
        confirma_contra = (EditText) findViewById(R.id.contraConfirma);
    }

    public void guardar(View view) {
        String usuario = user.getText().toString().trim();
        String pSecreta = palSecreta.getText().toString();
        String contrasena = contra.getText().toString();
        String confirmacion = confirma_contra.getText().toString();

        if (!usuario.isEmpty() && !pSecreta.trim().isEmpty() && !contrasena.trim().isEmpty() && !confirmacion.trim().isEmpty()) {

            admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
            bd = admin.getWritableDatabase();

            Cursor datos = bd.rawQuery("SELECT pSecreta FROM usuario WHERE username = ?", new String[]{usuario});
            if (datos.moveToFirst()) {
                if (contrasena.equals(confirmacion)) {

                    if (datos.getString(0).equals(pSecreta)) {
                        ContentValues registro = new ContentValues();
                        registro.put("contra", contrasena);

                        int fila = bd.update("usuario", registro, "username = ?", new String[]{usuario});

                        if (fila > 0) {
                            setResult(RESULT_OK);
                        } else {
                            setResult(RESULT_CANCELED);
                        }

                        bd.close();
                        datos.close();

                        finish();
                    } else {
                        Toast.makeText(this, "ERROR: Palabra secreta incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ERROR: Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "ERROR: El usuario no existe", Toast.LENGTH_SHORT).show();
            }

            bd.close();
            datos.close();

        } else {
            Toast.makeText(this, "Complete los campos para continuar", Toast.LENGTH_SHORT).show();
        }
    }

    public void salir(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}