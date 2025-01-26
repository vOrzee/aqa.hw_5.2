package ru.netology.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class RegistrationDto {
    String login;
    String password;
    String status;
}