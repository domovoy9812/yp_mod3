package ru.yandex.practicum.bliushtein.mod3.shared.dto;

public record CreateUserRequest(String name, String password, String firstName, String lastName, String email) { }
