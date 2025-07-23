# Testing Unitario en SmartOrder

_Proyecto realizado por: Josselyn Vega Devia_

## 1. Resumen del Proyecto

Este documento detalla la implementación de una batería de pruebas unitarias para la aplicación `SmartOrders`. El objetivo principal es formalizar el proceso de testing para reforzar la detección temprana de defectos, facilitar futuras modificaciones y garantizar la calidad y estabilidad del sistema.

La situación inicial de la empresa era una validación manual de pedidos, un proceso lento y propenso a errores que generaba un riesgo constante de regresiones. La adopción del **Testing Unitario con JUnit 5** se presenta como la solución estratégica para mitigar estos problemas, proporcionando una red de seguridad automatizada que valida la lógica de negocio clave (cálculos, descuentos, stock, etc.).

### Tecnologías Utilizadas
*   **Lenguaje:** Java (Compatible con JDK 11 en adelante, desarrollado en JDK 24)
*   **Gestor de Proyecto y Dependencias:** Apache Maven
*   **Framework de Pruebas:** JUnit 5

---

## 2. Instrucciones de Configuración y Ejecución

A continuación se detallan los pasos para clonar, compilar y probar el proyecto.

### Prerrequisitos
*   JDK 11 o superior instalado.
*   Apache Maven instalado y configurado en el PATH del sistema.
*   Git instalado.

### Pasos para la Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/jvegad/smartorder-testing-unitario
    cd smartorder-testing-unitario
    ```

2.  **Compilar y ejecutar las pruebas:**
    El siguiente comando limpiará compilaciones anteriores, compilará el código y ejecutará todas las pruebas unitarias definidas.
    ```bash
    mvn clean test
    ```
    Al finalizar la ejecución, la consola de Maven debería mostrar el mensaje `BUILD SUCCESS`, indicando que todas las pruebas pasaron correctamente.

---

## 3. Configuración de Dependencias (pom.xml)

Para habilitar las funcionalidades avanzadas de JUnit 5, fue necesario agregar dependencias específicas en el archivo `pom.xml`. Además de las dependencias base de JUnit (`junit-jupiter-api` y `junit-jupiter-engine`), se incluyeron dos artefactos clave:

*   **Tests Parametrizados:** Para poder crear pruebas que se ejecutan múltiples veces con diferentes argumentos (`@ParameterizedTest`), se requiere la siguiente dependencia. Esto es fundamental para probar diversos casos borde sin duplicar código.
    ```xml
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-params</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
    ```

*   **Suites de Pruebas:** Para poder organizar y agrupar múltiples clases de prueba en una sola ejecución mediante la anotación `@Suite`, es indispensable incluir el motor de suites de la plataforma JUnit.
    ```xml
    <dependency>
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-suite-engine</artifactId>
        <version>1.10.0</version>
        <scope>test</scope>
    </dependency>
    ```
---

## 4. Técnicas de Prueba Aplicadas

Para construir una batería de pruebas robusta, se aplicaron las siguientes técnicas de JUnit 5:

*   **Callbacks (`@BeforeEach`):** Se utilizó para inicializar un estado limpio antes de la ejecución de cada test (ej. crear un nuevo `CarritoCompra`). Esto garantiza la **independencia y consistencia de las pruebas**, evitando que el resultado de un test afecte al siguiente.

*   **Tests Parametrizados (`@ParameterizedTest`):** Esta técnica fue fundamental para validar la lógica de stock en `PedidoService`. En lugar de escribir múltiples tests casi idénticos para cada caso inválido (stock 0, stock -1), se creó un único test que itera sobre un conjunto de datos, reduciendo la duplicidad de código.

*   **Testeo de Excepciones (`assertThrows`):** Crucial para la robustez del sistema. Se usó para verificar que la aplicación maneja correctamente los casos de error esperados (ej. procesar un pedido vacío), asegurando que el programa falle de forma controlada y predecible.

*   **Supuestos (`assumeTrue`):** Se incluyó un ejemplo para demostrar cómo saltar pruebas condicionalmente. Esta técnica es valiosa en escenarios donde un test depende de un sistema externo que podría no estar disponible, evitando fallos innecesarios en el reporte.

*   **Suites de Pruebas (`@Suite`):** Para organizar y agrupar todas las clases de prueba (`CarritoCompraTest` y `PedidoServiceTest`). Permite ejecutar toda la batería de tests con un solo comando, ofreciendo una visión global e inmediata del estado de salud del código y simplificando los reportes.

---

## 5. Estructura del Código y Ejemplos

El código fuente se encuentra estructurado en el directorio `src/` siguiendo las convenciones de Maven.

*   **Clases centrales:** `src/main/java/com/smartorder/`
    *   `Producto.java`, `CarritoCompra.java`, `PedidoService.java`
*   **Pruebas unitarias:** `src/test/java/com/smartorder/`
    *   `CarritoCompraTest.java` (Aserciones básicas y `@BeforeEach`).
    *   `PedidoServiceTest.java` (Tests parametrizados, de excepción y supuestos).
    *   `SuiteDePruebas.java` (Organización en suite).

### Ejemplo de Test de Excepción
Este método verifica que se lanza `IllegalArgumentException` si se intenta procesar un pedido con un carrito vacío, lo cual es una regla de negocio crítica.

```java
@Test
@DisplayName("Debería lanzar IllegalArgumentException si el carrito está vacío")
void testProcesarPedido_LanzaExcepcionSiCarritoVacio() {
    CarritoCompra carritoVacio = new CarritoCompra();
    
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        pedidoService.procesarPedido(carritoVacio);
    });

    assertEquals("El carrito está vacío, no se puede procesar el pedido.", exception.getMessage());
}
```
---

## 6. Reflexión Final

### Agilización en la Localización de Bugs con VS Code

La ejecución automática de estas pruebas unitarias en un entorno como **Visual Studio Code** (con extensiones como "Test Runner for Java") transforma el flujo de trabajo.

Cuando un desarrollador realiza un cambio, puede **ejecutar la suite de pruebas con un solo clic**. El resultado es inmediato:
*   **Feedback Instantáneo:** En segundos, la consola muestra un resultado claro: verde (BUILD SUCCESS) o rojo (ERROR).
*   **Localización Precisa:** Si una prueba falla, la extensión no solo indica *cuál* falló, sino que permite navegar directamente a la línea de la aserción que no se cumplió, mostrando el valor esperado contra el valor recibido. Esto elimina la depuración manual y la revisión de logs extensos.
*   **Ciclo de Desarrollo Ágil:** El ciclo de `codificar -> probar -> corregir` se vuelve extremadamente rápido. Un bug en `PedidoService` sería detectado por `PedidoServiceTest` al instante, permitiendo una corrección inmediata.

En resumen, la automatización de pruebas en el IDE convierte el testing de una tarea manual y tediosa a una red de seguridad integrada y eficiente que acelera el desarrollo y mejora drásticamente la calidad del software.