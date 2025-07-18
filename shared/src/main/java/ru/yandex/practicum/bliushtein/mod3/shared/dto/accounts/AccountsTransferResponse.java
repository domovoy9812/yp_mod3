package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;

@NoArgsConstructor
@Getter
public class AccountsTransferResponse extends GenericResponse {
    private Account from;
    private Account to;

    public AccountsTransferResponse(boolean successful, String errorMessage, Account from, Account to) {
        super(successful, errorMessage);
        this.from = from;
        this.to = to;
    }
    public static AccountsTransferResponse ok(Account from, Account to) {
        return new AccountsTransferResponse(true, null, from, to);
    }

    public static AccountsTransferResponse fail(String errorMessage) {
        return new AccountsTransferResponse(false, errorMessage, null, null);
    }
}
