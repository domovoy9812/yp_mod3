package ru.yandex.practicum.bliushtein.mod3.exchange;

public class ExchangeException extends RuntimeException {
    public static final String INCORRECT_CURRENCY_ERROR_MESSAGE = "Incorrect currency value '%s'";
    public static final String CANT_SET_EXCHANGE_RATE_FOR_BASE_CURRENCY_ERROR_MESSAGE
            = "Can't set exchange rate for base currency";
    public static final String INCORRECT_EXCHANGE_RATE_ERROR_MESSAGE = "Incorrect exchange rate '%f' for currency '%s'";

    public ExchangeException(String message) {
        super(message);
    }

    public static ExchangeException incorrectCurrency(String currency) {
        return new ExchangeException(INCORRECT_CURRENCY_ERROR_MESSAGE.formatted(currency));
    }

    public static ExchangeException cantSetExchangeRateForBaseCurrency() {
        return new ExchangeException(CANT_SET_EXCHANGE_RATE_FOR_BASE_CURRENCY_ERROR_MESSAGE);
    }

    public static ExchangeException incorrectExchangeRate(Float exchangeRate, String currency) {
        return new ExchangeException(INCORRECT_EXCHANGE_RATE_ERROR_MESSAGE.formatted(exchangeRate, currency));
    }
}
