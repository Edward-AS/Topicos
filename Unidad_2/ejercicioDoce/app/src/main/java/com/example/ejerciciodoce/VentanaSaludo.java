package com.example.ejerciciodoce;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VentanaSaludo extends AppCompatActivity {

    private TextView saludo;
    AdminSQLiteOpenHelper admin;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_saludo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saludo = (TextView) findViewById(R.id.saludo);

        String usuario = getIntent().getStringExtra("user");

        admin = new AdminSQLiteOpenHelper(this, "Administración", null, 1);
        bd = admin.getReadableDatabase();

        Cursor datos = bd.rawQuery("SELECT noControl, nombre, telefono, correo FROM usuario WHERE username = ?", new String[]{usuario});

        datos.moveToFirst();
        String mensaje = "Hola "+ datos.getString(1) +", bienvenido" +
                "\nNúmero de control: "+ datos.getString(0) +
                "\nTeléfono: "+ datos.getString(2) +
                "\nCorreo: " + datos.getString(3);

        saludo.setText(mensaje);

        datos.close();
        bd.close();
    }

    public void salir(View view) {
        finish();
    }
}