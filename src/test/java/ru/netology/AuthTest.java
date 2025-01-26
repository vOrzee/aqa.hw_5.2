package ru.netology;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.dto.RegistrationDto;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.utils.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.utils.DataGenerator.Registration.getUser;
import static ru.netology.utils.DataGenerator.generateLogin;
import static ru.netology.utils.DataGenerator.generatePassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    private void sendAuth(RegistrationDto user) {
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        val registeredUser = getRegisteredUser("active");
        sendAuth(registeredUser);
        $(".heading").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        sendAuth(notRegisteredUser);
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.matchText("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        sendAuth(blockedUser);
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.matchText("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        registeredUser.setLogin(generateLogin());
        sendAuth(registeredUser);
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.matchText("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        registeredUser.setPassword(generatePassword());
        sendAuth(registeredUser);
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.matchText("Неверно указан логин или пароль"));
    }
}