package com.tuempresa.gestionvehiculos;

public abstract class Vehiculo {
    protected String id;  // Nuevo campo
    protected String marca;
    protected String modelo;
    protected double precioBase;
    protected int number;

    public Vehiculo(String id, String marca, String modelo, double precioBase, int number) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precioBase = precioBase;
        this.number = number;
    }

    // Añade este getter
    public String getId() {
        return id;
    }

    // Metodo para mostrar todos los detalles
    public abstract String getDetalleCompleto();


    // Añadir getter para el nuevo atributo
    public int getNumber() {
        return number;
    }

    // Getters
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getPrecioBase() { return precioBase; }

    // Metodo abstracto
    public abstract double calcularPrecio();

    public String obtenerInfo() {
        return "Marca: " + marca + "\nModelo: " + modelo +
                "\nPrecio Base: $" + precioBase;
    }
}