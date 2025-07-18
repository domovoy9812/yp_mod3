package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
public class BankUserResponse extends GenericResponse {
    private BankUser bankUser;

    public BankUserResponse(boolean successful, String errorMessage, BankUser bankUser) {
        super(successful, errorMessage);
        this.bankUser = bankUser;
    }

    public static BankUserResponse ok(BankUser bankUser) {
        return new BankUserResponse(true, null, bankUser);
    }

    public static BankUserResponse fail(String errorMessage) {
        return new BankUserResponse(false, errorMessage, null);
    }
}
