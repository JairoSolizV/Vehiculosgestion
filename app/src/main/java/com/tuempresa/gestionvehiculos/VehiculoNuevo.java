package com.tuempresa.gestionvehiculos;

public class VehiculoNuevo extends Vehiculo {
    private double descuento;

    public VehiculoNuevo(String id, String marca, String modelo, double precioBase, double descuento, int number) {
        super(id, marca, modelo, precioBase, number);
        this.descuento = descuento;
    }

    @Override
    public String getDetalleCompleto() {
        return String.format(
                "Tipo: Vehículo Nuevo\nID: %s\nMarca: %s\nModelo: %s\nPrecio Base: $%.2f\n" +
                        "Descuento: %.2f%%\nPrecio Final: $%.2f\nNumber: %d",
                id, marca, modelo, precioBase, descuento, calcularPrecio(), number
        );
    }
    public double getDescuento() {
        return descuento;
    }


    @Override
    public double calcularPrecio() {
        return getPrecioBase() * (1 - descuento/100);
    }

    @Override
    public String obtenerInfo() {
        return super.obtenerInfo() +
                "\nTipo: Nuevo" +
                "\nDescuento: " + descuento + "%" +
                "\nPrecio Final: $" + calcularPrecio();
    }

    public void aplicarDescuentoAdicional(double extra) {
        descuento = Math.min(100, descuento + extra);
    }
}