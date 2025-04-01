package com.tuempresa.gestionvehiculos;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private VehiculoRepository repository;
    private ArrayAdapter<Vehiculo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = VehiculoRepository.getInstance();
        setupUI();
        cargarVehiculos();
    }

    private void setupUI() {
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(v -> registrarVehiculo());
        // En setupUI()
        Button btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(v -> {
            String id = ((EditText)findViewById(R.id.etBuscarId)).getText().toString().trim();
            if (!id.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("VEHICULO_ID", id);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<Vehiculo>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Vehiculo>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Vehiculo vehiculo = getItem(position);
                String tipo = vehiculo instanceof VehiculoNuevo ? "Nuevo" : "Usado";
                ((TextView) view).setText(
                        vehiculo.getMarca() + " " + vehiculo.getModelo() +
                                "\nTipo: " + tipo +
                                "\nPrecio: $" + vehiculo.calcularPrecio()
                );
                return view;
            }
        };

        ListView lvVehiculos = findViewById(R.id.lvVehiculos);
        lvVehiculos.setAdapter(adapter);

        // Controlador del Spinner
        Spinner spinnerTipo = findViewById(R.id.spinnerTipo);
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean esNuevo = position == 0;
                findViewById(R.id.etDescuento).setVisibility(esNuevo ? View.VISIBLE : View.GONE);
                findViewById(R.id.etAños).setVisibility(esNuevo ? View.GONE : View.VISIBLE);
                findViewById(R.id.etDepreciacion).setVisibility(esNuevo ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Reemplaza el metodo registrarVehiculo()
    private void registrarVehiculo() {
        Spinner spinner = findViewById(R.id.spinnerTipo);
        String tipo = spinner.getSelectedItem().toString();

        String marca = ((EditText)findViewById(R.id.etMarca)).getText().toString().trim();
        String modelo = ((EditText)findViewById(R.id.etModelo)).getText().toString().trim();
        String precioStr = ((EditText)findViewById(R.id.etPrecio)).getText().toString().trim();

        if (marca.isEmpty() || modelo.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos básicos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String id = java.util.UUID.randomUUID().toString();

            int cantidad = 1;
            try {
                String cantidadStr = ((EditText)findViewById(R.id.etCantidad)).getText().toString().trim();
                if (!cantidadStr.isEmpty()) {
                    cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad < 1) {
                        Toast.makeText(this, "La cantidad debe ser ≥ 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Cantidad inválida (usando 1)", Toast.LENGTH_SHORT).show();
            }

            double precio = Double.parseDouble(precioStr);
            Vehiculo vehiculo;

            if (tipo.equals("Vehículo Nuevo")) {
                String descuentoStr = ((EditText)findViewById(R.id.etDescuento)).getText().toString().trim();
                double descuento = descuentoStr.isEmpty() ? 0 : Double.parseDouble(descuentoStr);

                vehiculo = new VehiculoNuevo(id, marca, modelo, precio, descuento, cantidad);

            } else {
                String añosStr = ((EditText)findViewById(R.id.etAños)).getText().toString().trim();
                String depreciacionStr = ((EditText)findViewById(R.id.etDepreciacion)).getText().toString().trim();

                int años = añosStr.isEmpty() ? 0 : Integer.parseInt(añosStr);
                double depreciacion = depreciacionStr.isEmpty() ? 0 : Double.parseDouble(depreciacionStr);

                vehiculo = new VehiculoUsado(id, marca, modelo, precio, años, depreciacion, cantidad);
            }

            repository.agregarVehiculo(vehiculo);
            cargarVehiculos();
            limpiarCampos();
            Toast.makeText(this, "Vehículo registrado!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en valores numéricos", Toast.LENGTH_SHORT).show();
        }
    }


    private void limpiarCampos() {
        ((EditText)findViewById(R.id.etMarca)).setText("");
        ((EditText)findViewById(R.id.etModelo)).setText("");
        ((EditText)findViewById(R.id.etPrecio)).setText("");
        ((EditText)findViewById(R.id.etCantidad)).setText("");
        ((EditText)findViewById(R.id.etDescuento)).setText("");
        ((EditText)findViewById(R.id.etAños)).setText("");
        ((EditText)findViewById(R.id.etDepreciacion)).setText("");
    }

    private void cargarVehiculos() {
        adapter.clear();
        adapter.addAll(repository.obtenerTodos());
        mostrarResumen(); // Añade esta línea
    }
    private void mostrarResumen() {
        Map<String, Integer> resumen = repository.getResumenCantidadPorTipo();
        StringBuilder sb = new StringBuilder("Resumen de \"number\":\n");

        for (Map.Entry<String, Integer> entry : resumen.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }

        TextView tvResumen = findViewById(R.id.tvResumen);
        tvResumen.setText(sb.toString());
    }
}