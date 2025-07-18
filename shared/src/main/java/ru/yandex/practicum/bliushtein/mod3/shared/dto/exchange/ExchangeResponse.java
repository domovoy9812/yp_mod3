package ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;

@NoArgsConstructor
@Getter
@Setter
public class ExchangeResponse extends GenericResponse {
    private Integer amount;

    public ExchangeResponse(boolean successful, String errorMessage, Integer amount) {
        super(successful, errorMessage);
        this.amount = amount;
    }

    public static ExchangeResponse ok(int amount) {
        return new ExchangeResponse(true, null, amount);
    }

    public static ExchangeResponse fail(String errorMessage) {
        return new ExchangeResponse(false, errorMessage, null);
    }
}
