package com.example.downloadermanager.Model;

public class Ficheros {

    private String id, name, descripcion,date,autor, url;

    public Ficheros() {
    }

    public Ficheros(String id, String name, String descripcion, String date, String autor, String url) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.date = date;
        this.autor = autor;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
