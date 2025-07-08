package ru.yandex.practicum.bliushtein.mod3.shared.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUser {
    private String username;
    private String password;
    private List<Account> accounts;
}
