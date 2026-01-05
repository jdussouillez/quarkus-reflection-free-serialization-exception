package com.github.jdussouillez.qrfse;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(EventResource.class)
class EventResourceTest {

    @BeforeAll
    public static void beforeAll() {
        RestAssuredConfig.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Test
    public void testGetOk() {
        RestAssured.given()
            .get("1")
            .then()
            .assertThat()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("id", Matchers.equalTo(1))
            .body("name", Matchers.equalTo("Devoxx France"));
    }

    @Test
    public void testGetNotFound() {
        RestAssured.given()
            .get("99")
            .then()
            .assertThat()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode())
            .body("message", Matchers.equalTo("Event not found"));
    }
}
