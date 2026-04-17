package com.api.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * RequestSpecFactory — centraliza la construcción de specs reutilizables.
 *
 * Ventajas:
 *  - No repetir headers/baseUri en cada test
 *  - Cambio global desde un solo lugar
 *  - Fácil de extender con specs específicas (admin, guest, etc.)
 */
public class RequestSpecFactory {

    private static final ConfigManager config = ConfigManager.getInstance();

    /**
     * Spec con Bearer Token válido — para tests autorizados.
     */
    public static RequestSpecification withValidToken() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + config.getValidToken())
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    /**
     * Spec con token inválido — para tests de autenticación negativa.
     */
    public static RequestSpecification withInvalidToken() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + config.getInvalidToken())
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    /**
     * Spec sin token — para tests de autenticación (401 esperado).
     */
    public static RequestSpecification withoutToken() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }
}
