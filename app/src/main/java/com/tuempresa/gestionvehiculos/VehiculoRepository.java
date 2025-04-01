package com.tuempresa.gestionvehiculos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehiculoRepository {
    private static VehiculoRepository instance;
    private List<Vehiculo> vehiculos = new ArrayList<>();

    private VehiculoRepository() {
        // Datos de ejemplo con IDs únicos
        vehiculos.add(new VehiculoNuevo("VN-001", "Toyota", "Corolla", 25000, 10, 30));
        vehiculos.add(new VehiculoUsado("VU-001", "Nissan", "Sentra", 15000, 5, 20, 70));
        vehiculos.add(new VehiculoNuevo("VN-002", "Honda", "Civic", 22000, 15, 500));
        vehiculos.add(new VehiculoUsado("VU-002", "Ford", "Fiesta", 12000, 3, 15, 250));
//        vehiculos.add(new VehiculoNuevo("VN-003", "Volkswagen", "Golf", 23000, 5, 500));
//        vehiculos.add(new VehiculoUsado("VU-003", "Chevrolet", "Spark", 10000, 7, 25, 250));
//        vehiculos.add(new VehiculoNuevo("VN-004", "Hyundai", "Tucson", 28000, 12, 500));
//        vehiculos.add(new VehiculoUsado("VU-004", "Kia", "Rio", 11000, 4, 18, 250));
//        vehiculos.add(new VehiculoNuevo("VN-005", "Mazda", "CX3", 26000, 8, 500));
//        vehiculos.add(new VehiculoUsado("VU-005", "Renault", "Sandero", 9000, 6, 22, 250));
    }

    // Nuevo metodo para obtener resumen por tipo
    public Map<String, Integer> getResumenCantidadPorTipo() {
        Map<String, Integer> resumen = new HashMap<>();
        int totalNuevos = 0;
        int totalUsados = 0;

        for (Vehiculo v : vehiculos) {
            if (v instanceof VehiculoNuevo) {
                totalNuevos += v.getNumber();
            } else if (v instanceof VehiculoUsado) {
                totalUsados += v.getNumber();
            }
        }

        resumen.put("Vehículos Nuevos", totalNuevos);
        resumen.put("Vehículos Usados", totalUsados);
        return resumen;
    }

    // Resto de métodos existentes...


    public static synchronized VehiculoRepository getInstance() {
        if (instance == null) {
            instance = new VehiculoRepository();
        }
        return instance;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public List<Vehiculo> obtenerTodos() {
        return new ArrayList<>(vehiculos);
    }

    public List<Vehiculo> buscarPorMarca(String marca) {
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
    public Vehiculo buscarPorId(String id) {
        for (Vehiculo v : vehiculos) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }
}