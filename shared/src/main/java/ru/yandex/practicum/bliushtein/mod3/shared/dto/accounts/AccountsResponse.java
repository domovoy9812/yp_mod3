package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;

import java.util.List;

@NoArgsConstructor
@Getter
public class AccountsResponse extends GenericResponse {
    private List<Account> accounts;

    public AccountsResponse(boolean successful, String errorMessage, List<Account> accounts) {
        super(successful, errorMessage);
        this.accounts = accounts;
    }

    public static AccountsResponse ok(List<Account> accounts) {
        return new AccountsResponse(true, null, accounts);
    }

    public static AccountsResponse fail(String errorMessage) {
        return new AccountsResponse(false, errorMessage, null);
    }
}
