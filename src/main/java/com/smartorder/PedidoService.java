package com.smartorder;

public class PedidoService {

    public double procesarPedido(CarritoCompra carrito) {
        if (carrito == null || carrito.estaVacio()) {
            throw new IllegalArgumentException("El carrito está vacío, no se puede procesar el pedido.");
        }
        for (Producto producto : carrito.getProductos()) {
            if (producto.getStock() <= 0) {
                throw new IllegalStateException("El producto " + producto.getNombre() + " no tiene stock disponible.");
            }
        }
        return carrito.calcularTotal();
    }
}
