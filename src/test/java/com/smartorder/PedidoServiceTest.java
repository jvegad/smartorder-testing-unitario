package com.smartorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Pruebas para PedidoService")
class PedidoServiceTest {

    private final PedidoService pedidoService = new PedidoService();
    private Exception exception;

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si el carrito está vacío")
    void testProcesarPedido_LanzaExcepcionSiCarritoVacio() {
        CarritoCompra carritoVacio = new CarritoCompra();
        exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.procesarPedido(carritoVacio);
        });
        assertEquals("El carrito está vacío, no se puede procesar el pedido.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería procesar un pedido con stock correctamente")
    void testProcesarPedido_ConStock() {
        CarritoCompra carrito = new CarritoCompra();
        carrito.agregarProducto(new Producto("Teclado", 100, 5));
        double total = pedidoService.procesarPedido(carrito);
        assertEquals(100, total, "El total del pedido procesado no es correcto.");
    }

    @ParameterizedTest(name = "Debería lanzar IllegalStateException para producto con stock = {0}")
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("Prueba parametrizada para stock inválido")
    void testProcesarPedido_ConStockInvalido(int stockInvalido) {
        CarritoCompra carrito = new CarritoCompra();
        carrito.agregarProducto(new Producto("Monitor", 300, stockInvalido));
        exception = assertThrows(IllegalStateException.class, () -> {
            pedidoService.procesarPedido(carrito);
        });
    }

    @Test
    @DisplayName("Debería saltar la prueba si el servicio externo no está disponible")
    void testConSuposicion() {
        boolean servicioExternoDisponible = false;
        assumeTrue(servicioExternoDisponible, "La prueba se salta porque el servicio externo no está disponible.");
        System.out.println("Ejecutando lógica de prueba que depende del servicio externo...");
        assertTrue(true);
    }
}
