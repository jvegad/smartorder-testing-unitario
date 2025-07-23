package com.smartorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas para CarritoCompra")
public class CarritoCompraTest {

    private CarritoCompra carrito;

    @BeforeEach
    void setUp() {
        carrito = new CarritoCompra();
    }

    @Test
    @DisplayName("Debería agregar un producto correctamente")
    void testAgregarProducto() {
        Producto producto = new Producto("Laptop", 1200.00, 10);
        carrito.agregarProducto(producto);
        assertFalse(carrito.estaVacio(), "El carrito no debería estar vacío después de agregar un producto.");
        assertEquals(1, carrito.getProductos().size(), "El carrito debería tener 1 producto.");
    }

    @Test
    @DisplayName("Debería calcular el total correctamente para múltiples productos")
    void testCalcularTotal() {
        carrito.agregarProducto(new Producto("Laptop", 1200.00, 10));
        carrito.agregarProducto(new Producto("Mouse", 25.00, 50));
        assertEquals(1225.00, carrito.calcularTotal(), 0.01, "El total calculado no es correcto.");
    }

    @Test
    @DisplayName("Debería estar vacío al ser creado")
    void testCarritoVacioInicialmente() {
        assertTrue(carrito.estaVacio(), "El carrito debería estar vacío inicialmente.");
        assertEquals(0, carrito.getProductos().size(), "El tamaño del carrito debería ser 0.");
    }
}
