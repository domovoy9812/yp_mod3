package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @NonNull
    String currency;
    private int balance;
}
