package ru.yandex.practicum.bliushtein.mod3.shared.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenericResponse {
    private boolean successful;
    private String errorMessage;

    public static GenericResponse ok() {
        return new GenericResponse(true, null);
    }

    public static GenericResponse fail(String errorMessage) {
        return new GenericResponse(false, errorMessage);
    }
}
