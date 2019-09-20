package com.example.pacificoreader.Revista.Model;

public class Revista {

    String nombre;
    String descripcion;
    String publicado;
    String autor;
    String articulos;
    String imagen;
    String path;

    public Revista(String nombre, String descripcion, String publicado, String autor, String articulos, String imagen, String path) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.publicado = publicado;
        this.autor = autor;
        this.articulos = articulos;
        this.imagen = imagen;
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPublicado() {
        return publicado;
    }

    public void setPublicado(String publicado) {
        this.publicado = publicado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getArticulos() {
        return articulos;
    }

    public void setArticulos(String articulos) {
        this.articulos = articulos;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
