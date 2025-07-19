package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import java.time.ZonedDateTime;

public record CreateUserRequest(String name, String password, String firstName, String lastName,
                                ZonedDateTime birthdate, String email) { }
