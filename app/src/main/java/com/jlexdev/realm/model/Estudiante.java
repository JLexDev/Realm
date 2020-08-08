package com.jlexdev.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Estudiante extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String nombre;

    private Tutor tutor; // Ahora es un objeto
    private int edad;

    // Constructor with no arg
    public Estudiante() {
    }

    public Estudiante(String nombre, Tutor tutor, int edad) {
        this.nombre = nombre;
        this.tutor = tutor;
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}