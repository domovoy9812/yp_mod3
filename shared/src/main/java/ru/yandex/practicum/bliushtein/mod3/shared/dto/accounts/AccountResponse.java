package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;

@NoArgsConstructor
@Getter
@Setter
public class AccountResponse extends GenericResponse {
    private Account account;

    public AccountResponse(boolean successful, String errorMessage, Account account) {
        super(successful, errorMessage);
        this.account = account;
    }

    public static AccountResponse ok(Account account) {
        return new AccountResponse(true, null, account);
    }

    public static AccountResponse fail(String errorMessage) {
        return new AccountResponse(false, errorMessage, null);
    }
}
