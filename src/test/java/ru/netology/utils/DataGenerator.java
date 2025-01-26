package ru.netology.utils;

import com.github.javafaker.Faker;
import lombok.val;
import ru.netology.dto.RegistrationDto;

import java.util.Random;

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
        private Registration() {
        }

        public static RegistrationDto generateUser() {
            String login = generateLogin();
            String password = generatePassword();
            String status = generateStatus();
            return new RegistrationDto(login, password, status);
        }
    }
}