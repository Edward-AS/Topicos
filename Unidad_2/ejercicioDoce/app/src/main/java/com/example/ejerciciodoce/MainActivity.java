package com.example.ejerciciodoce;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText usuario, contra;
    private final ActivityResultLauncher<Intent> launcherAlta = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {

                    Intent data = result.getData();
                    if (data != null) {
                        String username = data.getStringExtra("usuario");
                        usuario.setText(username);
                        Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                    }
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Toast.makeText(this, "ERROR: Proceso cancelado", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> launcherRecupera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Contraseña restablecida", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Toast.makeText(this, "ERROR: No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            });

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

        usuario = (EditText) findViewById(R.id.usuarioMain);
        contra = (EditText) findViewById(R.id.contraMain);
    }

    @Override
    protected void onResume(){
        super.onResume();
        contra.setText("");
    }

    public void entrar(View view) {
        if (validarCampos()) {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
            SQLiteDatabase bd = admin.getReadableDatabase();

            String user = usuario.getText().toString().trim();
            Cursor datos = bd.rawQuery("SELECT contra FROM usuario WHERE username = ?", new String[]{user});

            if (datos.moveToFirst()) {
                if (datos.getString(0).equals(contra.getText().toString())) {
                    datos.close();
                    bd.close();

                    Intent intentSaludo = new Intent(this, VentanaSaludo.class);
                    intentSaludo.putExtra("user", user);
                    startActivity(intentSaludo);
                } else {
                    Toast.makeText(this, "ERROR: Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El usuario no existe, itente de nuevo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void nuevoUsuario(View view) {
        Intent intentAlta = new Intent(this, VentanaAlta.class);
        launcherAlta.launch(intentAlta);
    }

    public void restablecerContra(View view) {
        Intent intentRecupera = new Intent(this, VentanaRecuperacion.class);
        launcherRecupera.launch(intentRecupera);
    }

    public void salir(View view) {
        finish();
    }

    private boolean validarCampos() {
        String campo1 = usuario.getText().toString().trim();
        String campo2 = contra.getText().toString().trim();

        if (campo1.isEmpty() || campo2.isEmpty()) {
            Toast.makeText(this, "Complete los campos para continuar", Toast.LENGTH_SHORT).show();
            return FALSE;
        }
        return TRUE;
    }
}