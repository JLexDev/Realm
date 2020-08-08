package com.jlexdev.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jlexdev.realm.model.Estudiante;
import com.jlexdev.realm.model.Tutor;

import java.util.UUID;

import io.realm.Realm;

public class SaveActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etEdad;
    private Button btnGuardarDato;

    private Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        myRealm = Realm.getDefaultInstance();

        etNombre = findViewById(R.id.et_nombre);
        etEdad = findViewById(R.id.et_edad);
        btnGuardarDato = findViewById(R.id.btn_guardar_dato);

        btnGuardarDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = etNombre.getText().toString();
                final int edad = Integer.valueOf(etEdad.getText().toString());

                final String id = UUID.randomUUID().toString();

                /*
                // Prevenir Errores
                try {
                    // Transacción REALM
                    myRealm.beginTransaction();
                    //Codigo de Objetos Tutor-Estudiante
                    myRealm.commitTransaction();

                } catch (Exception e) {
                    // Cancelo transacción
                    myRealm.cancelTransaction();
                }
                */

                // Para automatizar y evitar usar los "transaction" manualmente
                myRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Tutor miTutor = myRealm.createObject(Tutor.class); // También puedo "realm."
                        miTutor.setID("A000129");
                        miTutor.setNombre("José Luján");
                        miTutor.setEdad(40);

                        Estudiante miEstudiante = myRealm.createObject(Estudiante.class, id);
                        miEstudiante.setNombre(nombre);
                        miEstudiante.setTutor(miTutor); // Ahora es objeto (No cadena)
                        miEstudiante.setEdad(edad);

                        // Message
                        Toast.makeText(SaveActivity.this, "Se guardó correctamente",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
