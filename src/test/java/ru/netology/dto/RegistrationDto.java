package ru.netology.dto;

import lombok.*;

@Value
public class RegistrationDto {
    String login;
    String password;
    String status;
}