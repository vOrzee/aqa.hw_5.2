package ru.netology.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class RegistrationDto {
    public final String login;
    public final String password;
    public final String status;
}
