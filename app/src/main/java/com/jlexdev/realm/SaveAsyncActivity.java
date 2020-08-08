package com.jlexdev.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jlexdev.realm.model.Estudiante;
import com.jlexdev.realm.model.Tutor;

import java.util.UUID;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SaveAsyncActivity extends AppCompatActivity {

    // public static final String // TAG

    private EditText etNombre1;
    private EditText etEdad1;
    private Button btnGuardarDato1;
    private Button btnVerDato1;
    private Button btnQuery1;

    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_async);

        myRealm = Realm.getDefaultInstance();

        etNombre1 = findViewById(R.id.et_nombre_1);
        etEdad1 = findViewById(R.id.et_edad_1);
        btnGuardarDato1 = findViewById(R.id.btn_guardar_dato_1);
        btnVerDato1 = findViewById(R.id.btn_ver_dato_1);
        btnQuery1 = findViewById(R.id.btn_query_1);

        btnGuardarDato1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = etNombre1.getText().toString();
                final int edad = Integer.valueOf(etEdad1.getText().toString());

                final String id = UUID.randomUUID().toString();


                // Para automatizar y evitar usar los "transaction" manualmente
                // ASYNC

                realmAsyncTask = myRealm.executeTransactionAsync(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        Tutor miTutor = realm.createObject(Tutor.class); // También puedo "myRealm."
                                                        miTutor.setID("A000129");
                                                        miTutor.setNombre("José Luján");
                                                        miTutor.setEdad(40);

                                                        Estudiante miEstudiante = myRealm.createObject(Estudiante.class, id);
                                                        miEstudiante.setNombre(nombre);
                                                        miEstudiante.setTutor(miTutor); // Ahora es objeto (No cadena)
                                                        miEstudiante.setEdad(edad);
                                                    }

                                                    // Eventos
                                                }, new Realm.Transaction.OnSuccess() {
                                                    @Override
                                                    public void onSuccess() {
                                                        // Message
                                                        Toast.makeText(SaveAsyncActivity.this, "Se guardó correctamente",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Realm.Transaction.OnError() {
                                                    @Override
                                                    public void onError(Throwable error) {
                                                        // Message
                                                        Toast.makeText(SaveAsyncActivity.this, "¡No se guardó!",
                                                                Toast.LENGTH_SHORT).show();
                                                        Log.e("TAG", "Error: " + error);
                                                    }

                                                }

                );

            }
        });


        btnVerDato1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Estudiante> estudiantes = myRealm.where(Estudiante.class).findAll();

                StringBuilder builder = new StringBuilder();
                for (Estudiante estudiante: estudiantes) {
                    builder.append("\n id : " + estudiante.getId());
                    builder.append("\n nombre: " + estudiante.getNombre());
                    builder.append("\n edad: " + estudiante.getEdad());

                    Tutor tutor = estudiante.getTutor();
                    builder.append("\n nombre: " + tutor.getNombre());
                    builder.append("\n edad: " + tutor.getEdad());
                }


                Log.e("TAG", builder.toString());
            }
        });


        btnQuery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Query Method
                //justQuery();
                anotherQuery(); // Otro Query
            }
        });

    }


    // CONSULTA
    public void justQuery(){
        RealmQuery<Estudiante> realmQuery = myRealm.where(Estudiante.class);
        // Filtro
        realmQuery.greaterThan("edad", 20); // Atributo del modelo Estudiante "edad"
        realmQuery.contains("nombre", "Lobez"); // Que muestre si hay Nombre "Lobez"

        RealmResults<Estudiante> estudiantes = realmQuery.findAll();

        displayRealm(estudiantes);
    }


    // Opción más limpia
    public void anotherQuery() {
        RealmResults<Estudiante> estudiantes = myRealm.where(Estudiante.class)
                .greaterThan("edad", 20)
                .contains("nombre", "jlex", Case.INSENSITIVE) // Que no importe las MAYUS
                .findAll();

        displayRealm(estudiantes);
    }


    // Mostrar Resultados de Query
    private void displayRealm(RealmResults<Estudiante> estudiantes) {
        StringBuilder builder = new StringBuilder();
        for (Estudiante estudiante: estudiantes) {
            builder.append("\n id : " + estudiante.getId());
            builder.append("\n nombre: " + estudiante.getNombre());
            builder.append("\n edad: " + estudiante.getEdad());
        }

        Log.e("TAG", builder.toString());
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Evitar tener tareas pendientes
        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }

}
