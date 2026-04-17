package com.api.utils;

import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest — configuración global que se ejecuta una vez antes de toda la suite.
 *
 * Todos los test classes deben extender esta clase.
 */
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        // Parser por defecto para respuestas JSON
        RestAssured.defaultParser = Parser.JSON;

        // Log de responses en consola para debugging
        RestAssured.filters(new ResponseLoggingFilter());

        System.out.println("====================================");
        System.out.println("  GoRest API Test Suite - Iniciando");
        System.out.println("====================================");
    }
}
