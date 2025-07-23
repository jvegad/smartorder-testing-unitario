package com.smartorder;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {

    private final List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}
