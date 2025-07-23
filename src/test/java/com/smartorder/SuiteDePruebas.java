package com.smartorder;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

// @Suite: Indica que esta clase es una suite de pruebas.
// @SuiteDisplayName: Un nombre descriptivo para la suite.
// @SelectClasses: Aquí listamos todas las clases de prueba que queremos incluir.
@Suite
@SuiteDisplayName("Suite de Pruebas Completa para SmartOrder")
@SelectClasses({
    CarritoCompraTest.class,
    PedidoServiceTest.class
})
public class SuiteDePruebas {

}
