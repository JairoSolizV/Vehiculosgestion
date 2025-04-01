package com.tuempresa.gestionvehiculos;

public class VehiculoUsado extends Vehiculo {
    private int añosUso;
    private double depreciacion;

    public VehiculoUsado(String id, String marca, String modelo, double precioBase, int añosUso, double depreciacion, int number) {
        super(id, marca, modelo, precioBase, number);
        this.añosUso = añosUso;
        this.depreciacion = depreciacion;
    }

    @Override
    public String getDetalleCompleto() {
        return String.format(
                "Tipo: Vehículo Usado\nID: %s\nMarca: %s\nModelo: %s\nPrecio Base: $%.2f\n" +
                        "Años de Uso: %d\nDepreciación: %.2f%%\nPrecio Final: $%.2f\nCantidad: %d",
                id, marca, modelo, precioBase, añosUso, depreciacion, calcularPrecio(), number
        );
    }
    public int getAñosUso() {
        return añosUso;
    }
    public double getDepreciacion() {
        return depreciacion;
    }


    @Override
    public double calcularPrecio() {
        return getPrecioBase() * (1 - depreciacion/100);
    }

    @Override
    public String obtenerInfo() {
        return super.obtenerInfo() +
                "\nTipo: Usado" +
                "\nAños de uso: " + añosUso +
                "\nDepreciación: " + depreciacion + "%" +
                "\nPrecio Final: $" + calcularPrecio();
    }
}