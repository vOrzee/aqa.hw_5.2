package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.dto.RegistrationDto;
import ru.netology.utils.DataGenerator;

import static io.restassured.RestAssured.given;
import static ru.netology.utils.DataGenerator.Registration.requestSpec;

class RegistrationTest {

    private RegistrationDto user;

    @BeforeEach
    void setUser() {
        user = DataGenerator.Registration.getRegisteredUser("");
    }

    @Test
    void changePasswordHappyTest() {
        user.setPassword(DataGenerator.generatePassword());
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    void changeLoginHappyTest() {
        user.setLogin(DataGenerator.generateLogin());
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({
            "active, 200",
            "blocked, 200",
            "inactive, 500",
            "200, 500",
            ", 500"
    })
    void changeStatusValidationTest(String status, int statusCode) {
        user.setStatus(status);
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(statusCode);
    }

    @Test
    void checkNullableLogin() {
        user.setLogin(null);
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(500);
    }

    @Test
    void checkNullablePassword() {
        user.setPassword(null);
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(500);
    }
}
