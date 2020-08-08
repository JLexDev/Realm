package com.jlexdev.realm.model;

import io.realm.RealmObject;

public class Calificacion extends RealmObject {

    private int ID;
    private String titulo;
    private int valor;

    // Constructor with no arg
    public Calificacion() {
    }

    public Calificacion(String titulo, int valor) {
        this.titulo = titulo;
        this.valor = valor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}