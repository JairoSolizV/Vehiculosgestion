package com.tuempresa.gestionvehiculos;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // Obtener el ID del vehículo pasado desde MainActivity
        String vehiculoId = getIntent().getStringExtra("VEHICULO_ID");

        // Buscar el vehículo en el repositorio
        Vehiculo vehiculo = VehiculoRepository.getInstance().buscarPorId(vehiculoId);

        // Obtener referencias a las vistas
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvDetalle = findViewById(R.id.tvDetalle);
        Button btnVolver = findViewById(R.id.btnVolver);

        if (vehiculo != null) {
            // Mostrar información del vehículo
            tvTitulo.setText(vehiculo.getMarca() + " " + vehiculo.getModelo());

            // Mostrar todos los detalles
            String detalle = "ID: " + vehiculo.getId() + "\n\n" +
                    "Tipo: " + (vehiculo instanceof VehiculoNuevo ? "Nuevo" : "Usado") + "\n\n" +
                    "Marca: " + vehiculo.getMarca() + "\n\n" +
                    "Modelo: " + vehiculo.getModelo() + "\n\n" +
                    "Precio Base: $" + String.format("%.2f", vehiculo.getPrecioBase()) + "\n\n" +
                    "Cantidad: " + vehiculo.getNumber() + "\n\n";

            // Agregar campos específicos según el tipo
            if (vehiculo instanceof VehiculoNuevo) {
                VehiculoNuevo vn = (VehiculoNuevo) vehiculo;
                detalle += "Descuento: " + vn.getDescuento() + "%\n\n" +
                        "Precio Final: $" + String.format("%.2f", vn.calcularPrecio());
            } else if (vehiculo instanceof VehiculoUsado) {
                VehiculoUsado vu = (VehiculoUsado) vehiculo;
                detalle += "Años de Uso: " + vu.getAñosUso() + "\n\n" +
                        "Depreciación: " + vu.getDepreciacion() + "%\n\n" +
                        "Precio Final: $" + String.format("%.2f", vu.calcularPrecio());
            }

            tvDetalle.setText(detalle);
        } else {
            tvTitulo.setText("Vehículo no encontrado");
            tvDetalle.setText("No se encontró ningún vehículo con ID: " + vehiculoId);
        }

        // Configurar botón para volver
        btnVolver.setOnClickListener(v -> finish());
    }
}
