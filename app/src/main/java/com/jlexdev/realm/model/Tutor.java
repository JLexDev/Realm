package com.jlexdev.realm.model;

import io.realm.RealmObject;

public class Tutor extends RealmObject {

    private String ID;
    private String nombre;
    private int edad;

    // Constructor with no arg
    public Tutor() {
    }

    public Tutor(String ID, String nombre, int edad) {
        this.ID = ID;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
