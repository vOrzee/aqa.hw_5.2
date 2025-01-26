package ru.netology.utils;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import ru.netology.dto.RegistrationDto;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateLogin() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static String generateStatus() {
        val random = new Random();
        return random.nextBoolean() ? "active" : "blocked";
    }

    public static class Registration {

        public static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            String login = generateLogin();
            String password = generatePassword();
            return new RegistrationDto(login, password, status.isBlank() ? generateStatus() : status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            val user = Registration.getUser(status);
            given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users");
            return user;
        }
    }
}