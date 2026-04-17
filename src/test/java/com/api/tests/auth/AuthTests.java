package com.api.tests.auth;

import com.api.config.RequestSpecFactory;
import com.api.utils.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * AuthTests — valida los 3 escenarios de autenticación Bearer.
 *
 * ✅ Token válido     → 200
 * ❌ Sin token        → 401
 * ❌ Token inválido   → 401
 */
@Epic("Autenticación")
@Feature("Bearer Token")
public class AuthTests extends BaseTest {

    private static final String USERS_ENDPOINT = "/users";

    // ─────────────────────────────────────────────
    // ✅ Token Válido
    // ─────────────────────────────────────────────

    @Test(description = "GET /users con token válido debe retornar 200")
    @Story("Token Válido")
    @Severity(SeverityLevel.BLOCKER)
    public void validToken_shouldReturn200() {
        given()
                .spec(RequestSpecFactory.withValidToken())
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", not(empty()));
    }

    // ─────────────────────────────────────────────
    // ❌ Sin Token
    // ─────────────────────────────────────────────

    @Test(description = "GET /users sin token debe retornar 401")
    @Story("Sin Token")
    @Severity(SeverityLevel.CRITICAL)
    public void noToken_shouldReturn401() {
        given()
                .spec(RequestSpecFactory.withoutToken())
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .statusCode(401)
                .body("message", equalTo("Authentication failed"));
    }

    // ─────────────────────────────────────────────
    // ❌ Token Inválido
    // ─────────────────────────────────────────────

    @Test(description = "GET /users con token inválido debe retornar 401")
    @Story("Token Inválido")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidToken_shouldReturn401() {
        given()
                .spec(RequestSpecFactory.withInvalidToken())
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .statusCode(401)
                .body("message", equalTo("Authentication failed"));
    }

    // ─────────────────────────────────────────────
    // ✅ POST con token válido debe retornar 401 si sin auth
    // ─────────────────────────────────────────────

    @Test(description = "POST /users sin token debe retornar 401")
    @Story("Sin Token - POST")
    @Severity(SeverityLevel.CRITICAL)
    public void postWithoutToken_shouldReturn401() {
        String body = """
                {
                    "name": "Test User",
                    "email": "test@example.com",
                    "gender": "male",
                    "status": "active"
                }
                """;

        given()
                .spec(RequestSpecFactory.withoutToken())
                .body(body)
        .when()
                .post(USERS_ENDPOINT)
        .then()
                .statusCode(401)
                .body("message", equalTo("Authentication failed"));
    }
}
